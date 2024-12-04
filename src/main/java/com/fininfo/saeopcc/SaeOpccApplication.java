package com.fininfo.saeopcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
public class SaeopccApplication {

  public static void main(String[] args) {
    SpringApplication.run(SaeopccApplication.class, args);
  }
}
