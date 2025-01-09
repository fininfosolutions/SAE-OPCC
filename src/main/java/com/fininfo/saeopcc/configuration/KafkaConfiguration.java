package com.fininfo.saeopcc.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class KafkaConfiguration {

  private String bootStrapServers = "localhost:9092";

  private int retryMaxNum = 3;

  private int backOffPeriod = 3000;

  private int sessionTimeOut = 180000;

  private String securityProtocol = "";

  private String saslMechanism = "";

  private String saslJaasConfig = "";

  private String sslTruststoreLocation = "";

  private String sslTruststorePassword = "";

  private String sslEndpointIdentificationAlgorithm = "";

  private int maxPollRecords = 1;

  private int maxPollIntervalms = 300000;

  private int requestTimeOut = 185000;

  private Map<String, String> consumer = new HashMap<>();

  private Map<String, String> producer = new HashMap<>();

  public Map<String, Object> getConsumerProps() {
    Map<String, Object> properties = new HashMap<>(this.consumer);
    if (!properties.containsKey("bootstrap.servers")) {
      properties.put("bootstrap.servers", this.bootStrapServers);
    }
    properties.put("security.protocol", this.securityProtocol);
    properties.put("sasl.mechanism", this.saslMechanism);
    properties.put("sasl.jaas.config", this.saslJaasConfig);
    properties.put("ssl.truststore.location", this.sslTruststoreLocation);
    properties.put("ssl.truststore.password", this.sslTruststorePassword);
    properties.put(
        "ssl.endpoint.identification.algorithm", this.sslEndpointIdentificationAlgorithm);
    properties.put("max.poll.records", this.maxPollRecords);
    properties.put("session.timeout.ms", this.sessionTimeOut);
    properties.put("request.timeout.ms", this.requestTimeOut);
    properties.put("max.poll.interval.ms", this.maxPollIntervalms);
    return properties;
  }

  public void setConsumer(Map<String, String> consumer) {
    this.consumer = consumer;
  }

  public Map<String, Object> getProducerProps() {
    Map<String, Object> properties = new HashMap<>(this.producer);
    if (!properties.containsKey("bootstrap.servers")) {
      properties.put("bootstrap.servers", this.bootStrapServers);
    }
    properties.put("security.protocol", this.securityProtocol);
    properties.put("sasl.mechanism", this.saslMechanism);
    properties.put("sasl.jaas.config", this.saslJaasConfig);
    properties.put("ssl.truststore.location", this.sslTruststoreLocation);
    properties.put("ssl.truststore.password", this.sslTruststorePassword);
    properties.put(
        "ssl.endpoint.identification.algorithm", this.sslEndpointIdentificationAlgorithm);
    // properties.put("compression.type", "snappy");
    // properties.put("linger.ms", 5);
    properties.put("max.request.size", 10485880);
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
          ConsumerRecord record =
              (ConsumerRecord) context.getAttribute(RetryingMessageListenerAdapter.CONTEXT_RECORD);
          String errorTopic = record.topic().replace("_RETRY", "_ERROR");
          kafkaTemplate.send(errorTopic, record.value().toString());
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
    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(getRetryMaxNum(), exceptionMap, true);
    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }
}
