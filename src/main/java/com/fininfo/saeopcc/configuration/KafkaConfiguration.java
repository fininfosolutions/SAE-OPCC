package com.fininfo.saeopcc.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfiguration {

  private String bootStrapServers = "localhost:9092";
  private static final String BOOTSTRAP_SERVERS = "bootstrap.servers";

  private int retryMaxNum = 3;

  private int backOffPeriod = 3000;

  private int sessionTimeOut = 30000;

  public int getSessionTimeOut() {

    return sessionTimeOut;
  }

  public void setSessionTimeOut(int sessionTimeOut) {

    this.sessionTimeOut = sessionTimeOut;
  }

  private Map<String, String> consumer = new HashMap<>();

  private Map<String, String> producer = new HashMap<>();

  public String getBootStrapServers() {

    return bootStrapServers;
  }

  public void setBootStrapServers(String bootStrapServers) {

    this.bootStrapServers = bootStrapServers;
  }

  public int getRetryMaxNum() {

    return retryMaxNum;
  }

  public void setRetryMaxNum(int retryMaxNum) {

    this.retryMaxNum = retryMaxNum;
  }

  public int getBackOffPeriod() {

    return backOffPeriod;
  }

  public void setBackOffPeriod(int backOffPeriod) {

    this.backOffPeriod = backOffPeriod;
  }

  public Map<String, Object> getConsumerProps() {

    Map<String, Object> properties = new HashMap<>(this.consumer);

    if (!properties.containsKey(BOOTSTRAP_SERVERS)) {

      properties.put(BOOTSTRAP_SERVERS, this.bootStrapServers);

      properties.put("session.timeout.ms", this.sessionTimeOut);
    }

    return properties;
  }

  public void setConsumer(Map<String, String> consumer) {

    this.consumer = consumer;
  }

  public Map<String, Object> getProducerProps() {

    Map<String, Object> properties = new HashMap<>(this.producer);

    if (!properties.containsKey(BOOTSTRAP_SERVERS)) {

      properties.put(BOOTSTRAP_SERVERS, this.bootStrapServers);

      properties.put("max.request.size", 27000000);
    }

    return properties;
  }

  public void setProducer(Map<String, String> producer) {

    this.producer = producer;
  }

  /** Fininfo code */
  @Bean
  public ProducerFactory<String, String> producerFactory() {

    return new DefaultKafkaProducerFactory<>(getProducerProps());
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {

    return new KafkaTemplate<>(producerFactory());
  }

  /** Fininfo code */
  @Bean
  public ConsumerFactory<String, String> consumerFactory() {

    return new DefaultKafkaConsumerFactory<>(getConsumerProps());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kakfaListenerContainerFactory() {

    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(consumerFactory());

    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kakfaRetryListenerContainerFactory(
      KafkaTemplate<String, String> kafkaTemplate) {

    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(consumerFactory());

    factory.setRetryTemplate(retryTemplate());
    factory.setRecoveryCallback(
        context -> {
          ConsumerRecord<?, ?> consumerRecord =
              (ConsumerRecord<?, ?>)
                  context.getAttribute(RetryingMessageListenerAdapter.CONTEXT_RECORD);
          String errorTopic = consumerRecord.topic().replace("_RETRY", "_ERROR");
          kafkaTemplate.send(errorTopic, consumerRecord.value().toString());
          return Optional.empty();
        });

    return factory;
  }

  @Bean
  public RetryTemplate retryTemplate() {

    RetryTemplate retryTemplate = new RetryTemplate();

    FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();

    fixedBackOffPolicy.setBackOffPeriod(getBackOffPeriod());

    retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

    Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();

    exceptionMap.put(TimeoutException.class, true);

    exceptionMap.put(NullPointerException.class, true);

    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(getRetryMaxNum(), exceptionMap, true);

    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }
}
