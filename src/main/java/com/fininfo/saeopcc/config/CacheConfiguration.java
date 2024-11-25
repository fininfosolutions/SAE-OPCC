package com.fininfo.saeopcc.config;

import com.fininfo.saeopcc.configuration.ReferentielProperties;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.util.Arrays;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

@Configuration
@EnableCaching
public class CacheConfiguration {

  private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

  private final Environment env;

  private final ServerProperties serverProperties;

  private final DiscoveryClient discoveryClient;

  private Registration registration;

  @Value("${k8s.service.dns.name:null}")
  private String serviceDnsName;

  @Value("${spring.application.name}")
  private String applicationName;

  public CacheConfiguration(
      Environment env, ServerProperties serverProperties, DiscoveryClient discoveryClient) {
    this.env = env;
    this.serverProperties = serverProperties;
    this.discoveryClient = discoveryClient;
  }

  @Autowired(required = false)
  public void setRegistration(Registration registration) {
    this.registration = registration;
  }

  @PreDestroy
  public void destroy() {
    log.info("Closing Cache Manager");
    Hazelcast.shutdownAll();
  }

  @Bean
  public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
    log.debug("Starting HazelcastCacheManager");
    return new com.hazelcast.spring.cache.HazelcastCacheManager(hazelcastInstance);
  }

  @Bean
  public HazelcastInstance hazelcastInstance(ReferentielProperties jHipsterProperties) {
    log.debug("Configuring Hazelcast");
    HazelcastInstance hazelCastInstance = Hazelcast.getHazelcastInstanceByName(applicationName);
    if (hazelCastInstance != null) {
      log.debug("Hazelcast already initialized");
      return hazelCastInstance;
    }
    Config config = new Config();
    config.setInstanceName(applicationName);
    config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
    if (this.registration == null) {

      log.debug("Configuring Hazelcast clustering for serviceName: {}", serviceDnsName);
      if (env.acceptsProfiles(Profiles.of("prod"))) { // Production

        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false); // 5701
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config
            .getNetworkConfig()
            .getJoin()
            .getKubernetesConfig()
            .setEnabled(true)
            .setProperty("service-dns", serviceDnsName);
      }
    } else {

      String serviceId = registration.getServiceId();
      log.debug("Configuring Hazelcast clustering for instanceId: {}", serviceId);
      if (env.acceptsProfiles(Profiles.of("dev"))) {
        log.debug(
            "Application is running with the \"dev\" profile, Hazelcast "
                + "cluster will only work with localhost instances");

        System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
        config.getNetworkConfig().setPort(serverProperties.getPort() + 5701);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
          String clusterMember = "127.0.0.1:" + (instance.getPort() + 5701);
          log.debug("Adding Hazelcast (dev) cluster member {}", clusterMember);
          config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
        }
      } else if (env.acceptsProfiles(Profiles.of("prod"))) {

        config.getNetworkConfig().setPort(5701);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
          String clusterMember = instance.getHost() + ":5701";
          log.debug("Adding Hazelcast (prod) cluster member {}", clusterMember);
          config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
        }
      }
    }
    config.getMapConfigs().put("default", initializeDefaultMapConfig(jHipsterProperties));
    for (String name :
        Arrays.asList(
            "assets",
            "roles",
            "securitiesAccount",
            "externalCodes",
            "businessEntities",
            "funds",
            "clients",
            "likendCashAccounts",
            "custodians",
            "rolesOpcvm")) {
      config.getMapConfigs().put(name, initializeDomainMapConfig(jHipsterProperties));
    }
    return Hazelcast.newHazelcastInstance(config);
  }

  private MapConfig initializeDefaultMapConfig(ReferentielProperties jHipsterProperties) {
    MapConfig mapConfig = new MapConfig();

    mapConfig.setBackupCount(jHipsterProperties.getCache().getHazelcast().getBackupCount());

    mapConfig.getEvictionConfig().setEvictionPolicy(EvictionPolicy.LRU);

    mapConfig.getEvictionConfig().setMaxSizePolicy(MaxSizePolicy.USED_HEAP_SIZE);

    return mapConfig;
  }

  private MapConfig initializeDomainMapConfig(ReferentielProperties jHipsterProperties) {
    MapConfig mapConfig = new MapConfig();
    mapConfig.setTimeToLiveSeconds(
        jHipsterProperties.getCache().getHazelcast().getTimeToLiveSeconds());
    return mapConfig;
  }
}
