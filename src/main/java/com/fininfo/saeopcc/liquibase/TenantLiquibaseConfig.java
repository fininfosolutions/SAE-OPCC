package com.fininfo.saeopcc.liquibase;

import com.fininfo.saeopcc.shared.repositories.TenantRepository;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy(false)
@Configuration
@ConditionalOnProperty(
    name = "multitenancy.tenant.liquibase.enabled",
    havingValue = "true",
    matchIfMissing = true)
public class TenantLiquibaseConfig {

  @Bean
  @ConfigurationProperties("multitenancy.tenant.liquibase")
  public LiquibaseProperties tenantLiquibaseProperties() {
    return new LiquibaseProperties();
  }

  @Bean
  public DynamicSchemaBasedMultiTenantSpringLiquibase tenantLiquibase(
      TenantRepository masterTenantRepository,
      DataSource dataSource,
      @Qualifier("tenantLiquibaseProperties") LiquibaseProperties liquibaseProperties) {
    return new DynamicSchemaBasedMultiTenantSpringLiquibase(
        masterTenantRepository, dataSource, liquibaseProperties);
  }
}
