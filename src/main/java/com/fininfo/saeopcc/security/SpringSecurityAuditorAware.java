package com.fininfo.saeopcc.security;

import com.fininfo.saeopcc.config.Constants;
import com.fininfo.saeopcc.util.TenantContext;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/** Implementation of {@link AuditorAware} based on Spring Security. */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    if (TenantContext.getUser() != null) return Optional.of(TenantContext.getUser());
    return Optional.of(Constants.SYSTEM_ACCOUNT);
  }
}
