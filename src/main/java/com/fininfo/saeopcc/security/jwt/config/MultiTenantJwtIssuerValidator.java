package com.fininfo.saeopcc.security.jwt.config;

import com.fininfo.saeopcc.shared.domains.Tenant;
import com.fininfo.saeopcc.shared.services.TenantService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantJwtIssuerValidator implements OAuth2TokenValidator<Jwt> {
  private final TenantService tenantService;
  private final Map<String, JwtIssuerValidator> validators = new ConcurrentHashMap<>();

  public MultiTenantJwtIssuerValidator(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @Override
  public OAuth2TokenValidatorResult validate(Jwt token) {
    return validators.computeIfAbsent(toTenant(token), this::fromTenant).validate(token);
  }

  private String toTenant(Jwt jwt) {
    return jwt.getIssuer().toString();
  }

  private JwtIssuerValidator fromTenant(String issuer) {
    return tenantService
        .getByIssuer(issuer)
        .map(Tenant::getIssuer)
        .map(JwtIssuerValidator::new)
        .orElseThrow(() -> new IllegalArgumentException("Unknown tenant"));
  }
}
