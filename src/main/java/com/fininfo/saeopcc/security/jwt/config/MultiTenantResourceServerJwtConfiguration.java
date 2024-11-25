package com.fininfo.saeopcc.security.jwt.config;

import com.fininfo.saeopcc.shared.services.TenantService;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTClaimsSetAwareJWSKeySelector;
import com.nimbusds.jwt.proc.JWTProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class MultiTenantResourceServerJwtConfiguration {

  @Bean
  @ConditionalOnMissingBean(JWTClaimsSetAwareJWSKeySelector.class)
  JWTClaimsSetAwareJWSKeySelector<SecurityContext> multiTenantJWSKeySelector(
      TenantService tenantService) {
    return new MultiTenantJWSKeySelector(tenantService);
  }

  @Bean
  @ConditionalOnMissingBean(JWTProcessor.class)
  JWTProcessor<SecurityContext> multiTenantJwtProcessor(
      JWTClaimsSetAwareJWSKeySelector<SecurityContext> multiTenantJWSKeySelector) {
    ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
    jwtProcessor.setJWTClaimsSetAwareJWSKeySelector(multiTenantJWSKeySelector);
    return jwtProcessor;
  }

  @Bean
  @ConditionalOnMissingBean(OAuth2TokenValidator.class)
  OAuth2TokenValidator<Jwt> multiTenantJwtIssuerValidator(TenantService tenantService) {
    return new MultiTenantJwtIssuerValidator(tenantService);
  }

  @Bean
  @ConditionalOnMissingBean(JwtDecoder.class)
  JwtDecoder multiTenantJwtDecoder(
      JWTProcessor<SecurityContext> multiTenantJwtProcessor,
      OAuth2TokenValidator<Jwt> multiTenantJwtIssuerValidator) {
    NimbusJwtDecoder decoder = new NimbusJwtDecoder(multiTenantJwtProcessor);
    OAuth2TokenValidator<Jwt> validator =
        new DelegatingOAuth2TokenValidator<>(
            JwtValidators.createDefault(), multiTenantJwtIssuerValidator);
    decoder.setJwtValidator(validator);
    return decoder;
  }
}
