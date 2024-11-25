package com.fininfo.saeopcc.security.jwt.config;

import com.fininfo.saeopcc.shared.domains.Tenant;
import com.fininfo.saeopcc.shared.services.TenantService;
import com.nimbusds.jwt.JWTParser;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantAuthenticationManagerResolver
    implements AuthenticationManagerResolver<HttpServletRequest> {
  private final TenantService tenantService;
  private final JwtDecoder jwtDecoder;
  private final BearerTokenResolver resolver = new DefaultBearerTokenResolver();
  private final Map<String, AuthenticationManager> authenticationManagers =
      new ConcurrentHashMap<>();

  public MultiTenantAuthenticationManagerResolver(
      TenantService tenantService, JwtDecoder jwtDecoder) {
    this.tenantService = tenantService;
    this.jwtDecoder = jwtDecoder;
  }

  @Override
  public AuthenticationManager resolve(HttpServletRequest request) {
    return authenticationManagers.computeIfAbsent(toTenant(request), this::fromTenant);
  }

  private String toTenant(HttpServletRequest request) {
    try {
      String token = resolver.resolve(request);
      return JWTParser.parse(token).getJWTClaimsSet().getStringClaim("tenantid");
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  private AuthenticationManager fromTenant(String tenant) {
    return tenantService
            .getBySchema(tenant)
            .map(Tenant::getSchema)
            .map(i -> new JwtAuthenticationProvider(jwtDecoder))
            .orElseThrow(() -> new InvalidBearerTokenException("Unknown tenant: " + tenant))
        ::authenticate;
  }
}
