package com.fininfo.saeopcc.config.multitenant;

import com.fininfo.saeopcc.util.TenantContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class TenantResolverInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String tenantIdentifier = resolveTenantIdentifierFromAuthentication();
    if (tenantIdentifier != null) {
      TenantContext.setTenantId(tenantIdentifier);
      return true;
    } else {
      log.error("Tenant identifier not found in the authentication token.");
      // i can stop propagation here and elevate an exception / redirect error page
      return false;
    }
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    TenantContext.clear();
  }

  private String resolveTenantIdentifierFromAuthentication() {
    AbstractAuthenticationToken authentication =
        (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {
      if (authentication instanceof JwtAuthenticationToken) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        return extractTenantFromJwtToken(jwtToken);
      } else {
        log.warn("Unsupported authentication token type: {}", authentication.getClass().getName());
      }
    }

    return null;
  }

  private String extractTenantFromJwtToken(JwtAuthenticationToken jwtToken) {
    Jwt jwt = jwtToken.getToken();
    TenantContext.setUser(jwt.getClaim("preferred_username"));
    return jwt.getClaim("tenantid");
  }
}
