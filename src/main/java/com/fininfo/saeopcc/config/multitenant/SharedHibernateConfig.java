package com.fininfo.saeopcc.config.multitenant;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.schema.Action;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(
    basePackages = {"${multitenancy.shared.repository.packages}"},
    entityManagerFactoryRef = "sharedEntityManagerFactory",
    transactionManagerRef = "sharedTransactionManager")
public class SharedHibernateConfig {

  private final ConfigurableListableBeanFactory beanFactory;
  private final JpaProperties jpaProperties;

  @Value("${multitenancy.shared.entityManager.packages}")
  private String entityPackages;

  @Bean
  public LocalContainerEntityManagerFactoryBean sharedEntityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

    em.setPersistenceUnitName("shared-persistence-unit");
    em.setPackagesToScan(entityPackages);
    em.setDataSource(dataSource);

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    Map<String, Object> properties = new HashMap<>(this.jpaProperties.getProperties());
    properties.put(
        Environment.PHYSICAL_NAMING_STRATEGY,
        "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
    properties.put(
        Environment.IMPLICIT_NAMING_STRATEGY,
        "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
    properties.put(Environment.BEAN_CONTAINER, new SpringBeanContainer(this.beanFactory));
    properties.put(Environment.HBM2DDL_AUTO, Action.UPDATE);

    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  public JpaTransactionManager sharedTransactionManager(
      @Qualifier("sharedEntityManagerFactory") EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }
}
