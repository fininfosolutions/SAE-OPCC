/*
 * Copyright 2016-2020 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fininfo.saeopcc.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to JHipster.
 *
 * <p>Properties are configured in the application.yml file.
 *
 * <p>This class also load properties in the Spring Environment from the git.properties and
 * META-INF/build-info.properties files if they are found in the classpath.
 */
@Component
public class ReferentielProperties {

  private final Async async = new Async();

  private final Http http = new Http();

  private final Cache cache = new Cache();

  private final Mail mail = new Mail();

  private final Security security = new Security();

  private final Swagger swagger = new Swagger();

  private final Metrics metrics = new Metrics();

  private final Logging logging = new Logging();

  private final CorsConfiguration cors = new CorsConfiguration();

  private final Social social = new Social();

  private final Gateway gateway = new Gateway();

  private final Registry registry = new Registry();

  private final ClientApp clientApp = new ClientApp();

  private final AuditEvents auditEvents = new AuditEvents();

  /**
   * Getter for the field <code>async</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Async} object.
   */
  public Async getAsync() {
    return async;
  }

  /**
   * Getter for the field <code>http</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Http} object.
   */
  public Http getHttp() {
    return http;
  }

  /**
   * Getter for the field <code>cache</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Cache} object.
   */
  public Cache getCache() {
    return cache;
  }

  /**
   * Getter for the field <code>mail</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Mail} object.
   */
  public Mail getMail() {
    return mail;
  }

  /**
   * Getter for the field <code>registry</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Registry} object.
   */
  public Registry getRegistry() {
    return registry;
  }

  /**
   * Getter for the field <code>security</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Security} object.
   */
  public Security getSecurity() {
    return security;
  }

  /**
   * Getter for the field <code>swagger</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Swagger} object.
   */
  public Swagger getSwagger() {
    return swagger;
  }

  /**
   * Getter for the field <code>metrics</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Metrics} object.
   */
  public Metrics getMetrics() {
    return metrics;
  }

  /**
   * Getter for the field <code>logging</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Logging} object.
   */
  public Logging getLogging() {
    return logging;
  }

  /**
   * Getter for the field <code>cors</code>.
   *
   * @return a {@link org.springframework.web.cors.CorsConfiguration} object.
   */
  public CorsConfiguration getCors() {
    return cors;
  }

  /**
   * Getter for the field <code>social</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Social} object.
   */
  public Social getSocial() {
    return social;
  }

  /**
   * Getter for the field <code>gateway</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.Gateway} object.
   */
  public Gateway getGateway() {
    return gateway;
  }

  /**
   * Getter for the field <code>clientApp</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.ClientApp} object.
   */
  public ClientApp getClientApp() {
    return clientApp;
  }

  /**
   * Getter for the field <code>auditEvents</code>.
   *
   * @return a {@link io.github.ReferentielProperties.config.BrokerProperties.AuditEvents} object.
   */
  public AuditEvents getAuditEvents() {
    return auditEvents;
  }

  @Component
  public static class Async {

    private int corePoolSize = ReferentielDefaults.Async.corePoolSize;

    private int maxPoolSize = ReferentielDefaults.Async.maxPoolSize;

    private int queueCapacity = ReferentielDefaults.Async.queueCapacity;

    public int getCorePoolSize() {
      return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
      this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
      return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
      this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
      return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
      this.queueCapacity = queueCapacity;
    }
  }

  @Component
  public static class Http {

    private final Cache cache = new Cache();

    public Cache getCache() {
      return cache;
    }

    @Component
    public static class Cache {

      private int timeToLiveInDays = ReferentielDefaults.Http.Cache.timeToLiveInDays;

      public int getTimeToLiveInDays() {
        return timeToLiveInDays;
      }

      public void setTimeToLiveInDays(int timeToLiveInDays) {
        this.timeToLiveInDays = timeToLiveInDays;
      }
    }
  }

  @Component
  public static class Cache {

    private final Hazelcast hazelcast = new Hazelcast();

    private final Caffeine caffeine = new Caffeine();

    private final Ehcache ehcache = new Ehcache();

    private final Infinispan infinispan = new Infinispan();

    private final Memcached memcached = new Memcached();

    public Hazelcast getHazelcast() {
      return hazelcast;
    }

    public Caffeine getCaffeine() {
      return caffeine;
    }

    public Ehcache getEhcache() {
      return ehcache;
    }

    public Infinispan getInfinispan() {
      return infinispan;
    }

    public Memcached getMemcached() {
      return memcached;
    }

    @Component
    public static class Hazelcast {

      private int timeToLiveSeconds = ReferentielDefaults.Cache.Hazelcast.timeToLiveSeconds;

      private int backupCount = ReferentielDefaults.Cache.Hazelcast.backupCount;

      private final ManagementCenter managementCenter = new ManagementCenter();

      public ManagementCenter getManagementCenter() {
        return managementCenter;
      }

      @Component
      public static class ManagementCenter {

        private boolean enabled = ReferentielDefaults.Cache.Hazelcast.ManagementCenter.enabled;

        private int updateInterval =
            ReferentielDefaults.Cache.Hazelcast.ManagementCenter.updateInterval;

        private String url = ReferentielDefaults.Cache.Hazelcast.ManagementCenter.url;

        public boolean isEnabled() {
          return enabled;
        }

        public void setEnabled(boolean enabled) {
          this.enabled = enabled;
        }

        public int getUpdateInterval() {
          return updateInterval;
        }

        public void setUpdateInterval(int updateInterval) {
          this.updateInterval = updateInterval;
        }

        public String getUrl() {
          return url;
        }

        public void setUrl(String url) {
          this.url = url;
        }
      }

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public int getBackupCount() {
        return backupCount;
      }

      public void setBackupCount(int backupCount) {
        this.backupCount = backupCount;
      }
    }

    @Component
    public static class Caffeine {

      private int timeToLiveSeconds = ReferentielDefaults.Cache.Caffeine.timeToLiveSeconds;

      private long maxEntries = ReferentielDefaults.Cache.Caffeine.maxEntries;

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public long getMaxEntries() {
        return maxEntries;
      }

      public void setMaxEntries(long maxEntries) {
        this.maxEntries = maxEntries;
      }
    }

    @Component
    public static class Ehcache {

      private int timeToLiveSeconds = ReferentielDefaults.Cache.Ehcache.timeToLiveSeconds;

      private long maxEntries = ReferentielDefaults.Cache.Ehcache.maxEntries;

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public long getMaxEntries() {
        return maxEntries;
      }

      public void setMaxEntries(long maxEntries) {
        this.maxEntries = maxEntries;
      }
    }

    @Component
    public static class Infinispan {

      private String configFile = ReferentielDefaults.Cache.Infinispan.configFile;

      private boolean statsEnabled = ReferentielDefaults.Cache.Infinispan.statsEnabled;

      private final Local local = new Local();

      private final Distributed distributed = new Distributed();

      private final Replicated replicated = new Replicated();

      public String getConfigFile() {
        return configFile;
      }

      public void setConfigFile(String configFile) {
        this.configFile = configFile;
      }

      public boolean isStatsEnabled() {
        return statsEnabled;
      }

      public void setStatsEnabled(boolean statsEnabled) {
        this.statsEnabled = statsEnabled;
      }

      public Local getLocal() {
        return local;
      }

      public Distributed getDistributed() {
        return distributed;
      }

      public Replicated getReplicated() {
        return replicated;
      }

      @Component
      public static class Local {

        private long timeToLiveSeconds =
            ReferentielDefaults.Cache.Infinispan.Local.timeToLiveSeconds;

        private long maxEntries = ReferentielDefaults.Cache.Infinispan.Local.maxEntries;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }
      }

      @Component
      public static class Distributed {

        private long timeToLiveSeconds =
            ReferentielDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;

        private long maxEntries = ReferentielDefaults.Cache.Infinispan.Distributed.maxEntries;

        private int instanceCount = ReferentielDefaults.Cache.Infinispan.Distributed.instanceCount;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }

        public int getInstanceCount() {
          return instanceCount;
        }

        public void setInstanceCount(int instanceCount) {
          this.instanceCount = instanceCount;
        }
      }

      @Component
      public static class Replicated {

        private long timeToLiveSeconds =
            ReferentielDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;

        private long maxEntries = ReferentielDefaults.Cache.Infinispan.Replicated.maxEntries;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }
      }
    }

    @Component
    public static class Memcached {

      private boolean enabled = ReferentielDefaults.Cache.Memcached.enabled;

      /** Comma or whitespace separated list of servers' addresses. */
      private String servers = ReferentielDefaults.Cache.Memcached.servers;

      private int expiration = ReferentielDefaults.Cache.Memcached.expiration;

      private boolean useBinaryProtocol = ReferentielDefaults.Cache.Memcached.useBinaryProtocol;

      private Authentication authentication = new Authentication();

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public String getServers() {
        return servers;
      }

      public void setServers(String servers) {
        this.servers = servers;
      }

      public int getExpiration() {
        return expiration;
      }

      public void setExpiration(int expiration) {
        this.expiration = expiration;
      }

      public boolean isUseBinaryProtocol() {
        return useBinaryProtocol;
      }

      public void setUseBinaryProtocol(boolean useBinaryProtocol) {
        this.useBinaryProtocol = useBinaryProtocol;
      }

      public Authentication getAuthentication() {
        return authentication;
      }

      @Component
      public static class Authentication {

        private boolean enabled = ReferentielDefaults.Cache.Memcached.Authentication.enabled;
        private String username;
        private String password;

        public boolean isEnabled() {
          return enabled;
        }

        public Authentication setEnabled(boolean enabled) {
          this.enabled = enabled;
          return this;
        }

        public String getUsername() {
          return username;
        }

        public Authentication setUsername(String username) {
          this.username = username;
          return this;
        }

        public String getPassword() {
          return password;
        }

        public Authentication setPassword(String password) {
          this.password = password;
          return this;
        }
      }
    }
  }

  @Component
  public static class Mail {

    private boolean enabled = ReferentielDefaults.Mail.enabled;

    private String from = ReferentielDefaults.Mail.from;

    private String baseUrl = ReferentielDefaults.Mail.baseUrl;

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public String getBaseUrl() {
      return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
    }
  }

  @Component
  public static class Security {

    private final ClientAuthorization clientAuthorization = new ClientAuthorization();

    private final Authentication authentication = new Authentication();

    private final RememberMe rememberMe = new RememberMe();

    private final OAuth2 oauth2 = new OAuth2();

    public ClientAuthorization getClientAuthorization() {
      return clientAuthorization;
    }

    public Authentication getAuthentication() {
      return authentication;
    }

    public RememberMe getRememberMe() {
      return rememberMe;
    }

    public OAuth2 getOauth2() {
      return oauth2;
    }

    @Component
    public static class ClientAuthorization {

      private String accessTokenUri =
          ReferentielDefaults.Security.ClientAuthorization.accessTokenUri;

      private String tokenServiceId =
          ReferentielDefaults.Security.ClientAuthorization.tokenServiceId;

      private String clientId = ReferentielDefaults.Security.ClientAuthorization.clientId;

      private String clientSecret = ReferentielDefaults.Security.ClientAuthorization.clientSecret;

      public String getAccessTokenUri() {
        return accessTokenUri;
      }

      public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
      }

      public String getTokenServiceId() {
        return tokenServiceId;
      }

      public void setTokenServiceId(String tokenServiceId) {
        this.tokenServiceId = tokenServiceId;
      }

      public String getClientId() {
        return clientId;
      }

      public void setClientId(String clientId) {
        this.clientId = clientId;
      }

      public String getClientSecret() {
        return clientSecret;
      }

      public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
      }
    }

    @Component
    public static class Authentication {

      private final Jwt jwt = new Jwt();

      public Jwt getJwt() {
        return jwt;
      }

      public static class Jwt {

        private String secret = ReferentielDefaults.Security.Authentication.Jwt.secret;

        private String base64Secret = ReferentielDefaults.Security.Authentication.Jwt.base64Secret;

        private long tokenValidityInSeconds =
            ReferentielDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;

        private long tokenValidityInSecondsForRememberMe =
            ReferentielDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;

        public String getSecret() {
          return secret;
        }

        public void setSecret(String secret) {
          this.secret = secret;
        }

        public String getBase64Secret() {
          return base64Secret;
        }

        public void setBase64Secret(String base64Secret) {
          this.base64Secret = base64Secret;
        }

        public long getTokenValidityInSeconds() {
          return tokenValidityInSeconds;
        }

        public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
          this.tokenValidityInSeconds = tokenValidityInSeconds;
        }

        public long getTokenValidityInSecondsForRememberMe() {
          return tokenValidityInSecondsForRememberMe;
        }

        public void setTokenValidityInSecondsForRememberMe(
            long tokenValidityInSecondsForRememberMe) {
          this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
        }
      }
    }

    @Component
    public static class RememberMe {

      @NotNull private String key = ReferentielDefaults.Security.RememberMe.key;

      public String getKey() {
        return key;
      }

      public void setKey(String key) {
        this.key = key;
      }
    }

    @Component
    public static class OAuth2 {
      private List<String> audience = new ArrayList<>();

      public List<String> getAudience() {
        return Collections.unmodifiableList(audience);
      }

      public void setAudience(@NotNull List<String> audience) {
        this.audience.addAll(audience);
      }
    }
  }

  @Component
  public static class Swagger {

    private String title = ReferentielDefaults.Swagger.title;

    private String description = ReferentielDefaults.Swagger.description;

    private String version = ReferentielDefaults.Swagger.version;

    private String termsOfServiceUrl = ReferentielDefaults.Swagger.termsOfServiceUrl;

    private String contactName = ReferentielDefaults.Swagger.contactName;

    private String contactUrl = ReferentielDefaults.Swagger.contactUrl;

    private String contactEmail = ReferentielDefaults.Swagger.contactEmail;

    private String license = ReferentielDefaults.Swagger.license;

    private String licenseUrl = ReferentielDefaults.Swagger.licenseUrl;

    private String defaultIncludePattern = ReferentielDefaults.Swagger.defaultIncludePattern;

    private String host = ReferentielDefaults.Swagger.host;

    private String[] protocols = ReferentielDefaults.Swagger.protocols;

    private boolean useDefaultResponseMessages =
        ReferentielDefaults.Swagger.useDefaultResponseMessages;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    public String getTermsOfServiceUrl() {
      return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
      this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getContactName() {
      return contactName;
    }

    public void setContactName(String contactName) {
      this.contactName = contactName;
    }

    public String getContactUrl() {
      return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
      this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
      return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
      this.contactEmail = contactEmail;
    }

    public String getLicense() {
      return license;
    }

    public void setLicense(String license) {
      this.license = license;
    }

    public String getLicenseUrl() {
      return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
      this.licenseUrl = licenseUrl;
    }

    public String getDefaultIncludePattern() {
      return defaultIncludePattern;
    }

    public void setDefaultIncludePattern(String defaultIncludePattern) {
      this.defaultIncludePattern = defaultIncludePattern;
    }

    public String getHost() {
      return host;
    }

    public void setHost(final String host) {
      this.host = host;
    }

    public String[] getProtocols() {
      return protocols;
    }

    public void setProtocols(final String[] protocols) {
      this.protocols = protocols;
    }

    public boolean isUseDefaultResponseMessages() {
      return useDefaultResponseMessages;
    }

    public void setUseDefaultResponseMessages(final boolean useDefaultResponseMessages) {
      this.useDefaultResponseMessages = useDefaultResponseMessages;
    }
  }

  @Component
  public static class Metrics {

    private final Logs logs = new Logs();

    public Logs getLogs() {
      return logs;
    }

    @Component
    public static class Logs {

      private boolean enabled = ReferentielDefaults.Metrics.Logs.enabled;

      private long reportFrequency = ReferentielDefaults.Metrics.Logs.reportFrequency;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public long getReportFrequency() {
        return reportFrequency;
      }

      public void setReportFrequency(long reportFrequency) {
        this.reportFrequency = reportFrequency;
      }
    }
  }

  @Component
  public static class Logging {

    private boolean useJsonFormat = ReferentielDefaults.Logging.useJsonFormat;

    private final Logstash logstash = new Logstash();

    public boolean isUseJsonFormat() {
      return useJsonFormat;
    }

    public void setUseJsonFormat(boolean useJsonFormat) {
      this.useJsonFormat = useJsonFormat;
    }

    public Logstash getLogstash() {
      return logstash;
    }

    @Component
    public static class Logstash {

      private boolean enabled = ReferentielDefaults.Logging.Logstash.enabled;

      private String host = ReferentielDefaults.Logging.Logstash.host;

      private int port = ReferentielDefaults.Logging.Logstash.port;

      private int queueSize = ReferentielDefaults.Logging.Logstash.queueSize;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public String getHost() {
        return host;
      }

      public void setHost(String host) {
        this.host = host;
      }

      public int getPort() {
        return port;
      }

      public void setPort(int port) {
        this.port = port;
      }

      public int getQueueSize() {
        return queueSize;
      }

      public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
      }
    }
  }

  @Component
  public static class Social {

    private String redirectAfterSignIn = ReferentielDefaults.Social.redirectAfterSignIn;

    public String getRedirectAfterSignIn() {
      return redirectAfterSignIn;
    }

    public void setRedirectAfterSignIn(String redirectAfterSignIn) {
      this.redirectAfterSignIn = redirectAfterSignIn;
    }
  }

  @Component
  public static class Gateway {

    private final RateLimiting rateLimiting = new RateLimiting();

    public RateLimiting getRateLimiting() {
      return rateLimiting;
    }

    private Map<String, List<String>> authorizedMicroservicesEndpoints =
        ReferentielDefaults.Gateway.authorizedMicroservicesEndpoints;

    public Map<String, List<String>> getAuthorizedMicroservicesEndpoints() {
      return authorizedMicroservicesEndpoints;
    }

    public void setAuthorizedMicroservicesEndpoints(
        Map<String, List<String>> authorizedMicroservicesEndpoints) {
      this.authorizedMicroservicesEndpoints = authorizedMicroservicesEndpoints;
    }

    public static class RateLimiting {

      private boolean enabled = ReferentielDefaults.Gateway.RateLimiting.enabled;

      private long limit = ReferentielDefaults.Gateway.RateLimiting.limit;

      private int durationInSeconds = ReferentielDefaults.Gateway.RateLimiting.durationInSeconds;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public long getLimit() {
        return this.limit;
      }

      public void setLimit(long limit) {
        this.limit = limit;
      }

      public int getDurationInSeconds() {
        return durationInSeconds;
      }

      public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
      }
    }
  }

  @Component
  public static class Registry {

    private String password = ReferentielDefaults.Registry.password;

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  @Component
  public static class ClientApp {

    private String name = ReferentielDefaults.ClientApp.name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  @Component
  public static class AuditEvents {
    private int retentionPeriod = ReferentielDefaults.AuditEvents.retentionPeriod;

    public int getRetentionPeriod() {
      return retentionPeriod;
    }

    public void setRetentionPeriod(int retentionPeriod) {
      this.retentionPeriod = retentionPeriod;
    }
  }
}
