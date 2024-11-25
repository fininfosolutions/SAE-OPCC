package com.fininfo.saeopcc.config;

import com.fininfo.saeopcc.aop.logging.LoggingAspect;
import com.fininfo.saeopcc.configuration.ReferentielConstants;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

  @Bean
  @Profile(ReferentielConstants.SPRING_PROFILE_DEVELOPMENT)
  public LoggingAspect loggingAspect(Environment env) {
    return new LoggingAspect(env);
  }
}
