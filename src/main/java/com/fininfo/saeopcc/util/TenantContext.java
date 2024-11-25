package com.fininfo.saeopcc.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TenantContext {

  private TenantContext() {}

  private static final InheritableThreadLocal<String> currentTenant =
      new InheritableThreadLocal<>();
  private static final InheritableThreadLocal<String> currentUser = new InheritableThreadLocal<>();

  public static void setTenantId(String tenantId) {
    log.debug("Setting tenantId to " + tenantId);
    currentTenant.set(tenantId);
  }

  public static String getTenantId() {
    return currentTenant.get();
  }

  public static void setUser(String user) {
    currentUser.set(user);
  }

  public static String getUser() {
    return currentUser.get();
  }

  public static void clear() {
    currentTenant.remove();
    currentUser.remove();
  }
}
