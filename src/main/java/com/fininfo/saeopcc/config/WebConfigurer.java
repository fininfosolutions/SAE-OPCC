package com.fininfo.saeopcc.config;

import com.fininfo.saeopcc.config.multitenant.TenantResolverInterceptor;
import javax.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Configuration of web application with Servlet 3.0 APIs. */
@Configuration
@Slf4j
public class WebConfigurer implements ServletContextInitializer, WebMvcConfigurer {

  private final Environment env;

  public WebConfigurer(Environment env) {
    this.env = env;
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    if (env.getActiveProfiles().length != 0) {
      log.info(
          "Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
    }

    log.info("Web application fully configured");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new TenantResolverInterceptor());
    log.debug("Added interceptors !");
  }
}
