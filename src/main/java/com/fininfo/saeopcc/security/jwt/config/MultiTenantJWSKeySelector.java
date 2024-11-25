package com.fininfo.saeopcc.security.jwt.config;

import com.fininfo.saeopcc.shared.domains.Tenant;
import com.fininfo.saeopcc.shared.services.TenantService;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.JWTClaimsSetAwareJWSKeySelector;
import java.net.URL;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantJWSKeySelector implements JWTClaimsSetAwareJWSKeySelector<SecurityContext> {
  private final TenantService tenantService;
  private final Map<String, JWSKeySelector<SecurityContext>> selectors = new ConcurrentHashMap<>();

  public MultiTenantJWSKeySelector(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @Override
  public List<? extends Key> selectKeys(
      JWSHeader jwsHeader, JWTClaimsSet jwtClaimsSet, SecurityContext securityContext)
      throws KeySourceException {
    return selectors
        .computeIfAbsent(toTenant(jwtClaimsSet), this::fromTenant)
        .selectJWSKeys(jwsHeader, securityContext);
  }

  private String toTenant(JWTClaimsSet claimSet) {
    return claimSet.getIssuer();
  }

  private JWSKeySelector<SecurityContext> fromTenant(String issuer) {
    return tenantService
        .getByIssuer(issuer)
        .map(Tenant::getJwkSetUrl)
        .map(this::fromUri)
        .orElseThrow(() -> new IllegalArgumentException("Unknown tenant"));
  }

  private JWSKeySelector<SecurityContext> fromUri(String uri) {
    try {
      return JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL(new URL(uri));
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
