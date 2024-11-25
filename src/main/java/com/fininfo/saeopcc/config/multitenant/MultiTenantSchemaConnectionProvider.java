package com.fininfo.saeopcc.config.multitenant;

import com.fininfo.saeopcc.shared.domains.Tenant;
import com.fininfo.saeopcc.shared.repositories.TenantRepository;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Provider that provides tenant-specific connection handling in a multi-tenant application. The
 * tenant distinction is realized by using separate schemas, i.e. each tenant uses its own schema in
 * a shared (common) database.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class MultiTenantSchemaConnectionProvider implements MultiTenantConnectionProvider {

  private final DataSource dataSource;
  private final TenantRepository tenantRepository;

  @Value("${multitenancy.schema-cache.maximumSize:1000}")
  private Long maximumSize;

  @Value("${multitenancy.schema-cache.expireAfterAccess:10}")
  private Integer expireAfterAccess;

  private LoadingCache<String, String> tenantSchemas;

  @PostConstruct
  private void createCache() {
    Caffeine<Object, Object> tenantsCacheBuilder = Caffeine.newBuilder();
    if (maximumSize != null) {
      tenantsCacheBuilder.maximumSize(maximumSize);
    }
    if (expireAfterAccess != null) {
      tenantsCacheBuilder.expireAfterAccess(expireAfterAccess, TimeUnit.MINUTES);
    }
    tenantSchemas =
        tenantsCacheBuilder.build(
            tenantId -> {
              Tenant tenant =
                  tenantRepository
                      .findBySchema(tenantId)
                      .orElseThrow(() -> new RuntimeException("No such tenant: " + tenantId));
              return tenant.getSchema();
            });
  }

  @Override
  public Connection getAnyConnection() throws SQLException {
    return dataSource.getConnection();
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    connection.close();
  }

  @Override
  public Connection getConnection(String tenantIdentifier) throws SQLException {
    log.trace("Get connection for tenant '{}'", tenantIdentifier);
    String tenantSchema = tenantSchemas.get(tenantIdentifier);
    final Connection connection = getAnyConnection();
    connection.setSchema(tenantSchema);
    return connection;
  }

  @Override
  public void releaseConnection(String tenantIdentifier, Connection connection)
      throws SQLException {
    log.trace("Release connection for tenant '{}'", tenantIdentifier);
    connection.setSchema(CurrentTenantResolver.DEFAULT_SCHEMA);
    releaseAnyConnection(connection);
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }

  @Override
  public boolean isUnwrappableAs(Class unwrapType) {
    return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
  }

  @Override
  public <T> T unwrap(Class<T> unwrapType) {
    if (MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType)) {
      return (T) this;
    } else {
      throw new UnknownUnwrapTypeException(unwrapType);
    }
  }
}
