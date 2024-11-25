package com.fininfo.saeopcc.config.multitenant;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Configures an {@link LocalContainerEntityManagerFactoryBean EntityManagerFactoryBean} for a
 * schema-based multi-tenant data source.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = {"${multitenancy.tenant.repository.packages}"},
    entityManagerFactoryRef = "tenantEntityManagerFactory",
    transactionManagerRef = "tenantTransactionManager")
public class MultiTenantSchemaHibernateConfiguration {

  private final ConfigurableListableBeanFactory beanFactory;
  private final JpaProperties jpaProperties;
  private final String entityPackages;

  @Autowired
  public MultiTenantSchemaHibernateConfiguration(
      ConfigurableListableBeanFactory beanFactory,
      JpaProperties jpaProperties,
      @Value("${multitenancy.tenant.entityManager.packages}") String entityPackages) {
    this.beanFactory = beanFactory;
    this.jpaProperties = jpaProperties;
    this.entityPackages = entityPackages;
  }

  @Primary
  @Bean
  LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
      DataSource dataSource,
      MultiTenantConnectionProvider multiTenantConnectionProvider,
      CurrentTenantIdentifierResolver tenantIdentifierResolver) {

    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setPersistenceUnitName("tenant-persistence-unit");
    em.setPackagesToScan(entityPackages);
    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
    jpaPropertiesMap.put(
        Environment.PHYSICAL_NAMING_STRATEGY,
        "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
    jpaPropertiesMap.put(
        Environment.IMPLICIT_NAMING_STRATEGY,
        "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
    jpaPropertiesMap.put(Environment.BEAN_CONTAINER, new SpringBeanContainer(this.beanFactory));
    jpaPropertiesMap.remove(Environment.DEFAULT_SCHEMA);
    jpaPropertiesMap.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
    jpaPropertiesMap.put(
        Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
    jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
    em.setJpaPropertyMap(jpaPropertiesMap);

    return em;
  }

  @Primary
  @Bean
  public JpaTransactionManager tenantTransactionManager(
      @Qualifier("tenantEntityManagerFactory") EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }
}
