package com.fininfo.saeopcc.liquibase;

import com.fininfo.saeopcc.shared.domains.Tenant;
import com.fininfo.saeopcc.shared.repositories.TenantRepository;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import javax.sql.DataSource;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class DynamicSchemaBasedMultiTenantSpringLiquibase
    implements InitializingBean, ResourceLoaderAware {

  private final TenantRepository masterTenantRepository;

  private final DataSource dataSource;

  @Qualifier("tenantLiquibaseProperties")
  private final LiquibaseProperties liquibaseProperties;

  private ResourceLoader resourceLoader;

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("Schema based multitenancy enabled");
    this.runOnAllSchemas(dataSource, masterTenantRepository.findAll());
  }

  protected void runOnAllSchemas(DataSource dataSource, Iterable<Tenant> tenants)
      throws SQLException, LiquibaseException {
    for (Tenant tenant : tenants) {
      log.info("Initializing Liquibase for tenant " + tenant.getSchema());

      createSchemaIfNotExists(dataSource, tenant.getSchema());

      SpringLiquibase liquibase = this.getSpringLiquibase(dataSource, tenant.getSchema());
      liquibase.afterPropertiesSet();
      log.info("Liquibase ran for tenant " + tenant.getSchema());
    }
  }

  protected void createSchemaIfNotExists(DataSource dataSource, String schema) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      DatabaseMetaData metaData = connection.getMetaData();
      try (ResultSet schemas = metaData.getSchemas(null, schema)) {
        if (!schemas.next()) {
          String query = "CREATE SCHEMA " + schema;
          try (PreparedStatement statement = connection.prepareStatement(query)) {
            log.info("Creating schema : {}", schema);
            boolean result = statement.execute();
            log.info("Result Creating schema : {}", result);
            connection.commit();
            log.info("Transaction committed.");
          }
        }
      }
    }
  }

  protected SpringLiquibase getSpringLiquibase(DataSource dataSource, String schema) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setResourceLoader(getResourceLoader());
    liquibase.setDataSource(dataSource);
    liquibase.setDefaultSchema(schema);
    if (liquibaseProperties.getParameters() != null) {
      liquibaseProperties.getParameters().put("schema", schema);
      liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
    } else {
      liquibase.setChangeLogParameters(Collections.singletonMap("schema", schema));
    }
    liquibase.setChangeLog(liquibaseProperties.getChangeLog());
    liquibase.setContexts(liquibaseProperties.getContexts());
    liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
    liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
    liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
    liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
    liquibase.setDropFirst(liquibaseProperties.isDropFirst());
    liquibase.setShouldRun(liquibaseProperties.isEnabled());
    liquibase.setLabels(liquibaseProperties.getLabels());
    liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
    liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
    log.info("liquibase properties content log: {}", liquibase.getChangeLog());
    return liquibase;
  }
}
