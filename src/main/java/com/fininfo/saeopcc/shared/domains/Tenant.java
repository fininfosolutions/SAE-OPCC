package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "tenant", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
public class Tenant {

  @Id
  @Column(name = "schema", updatable = false, nullable = false, unique = true)
  private String schema;

  @NotNull
  @Column(name = "issuer", nullable = false)
  private String issuer;

  public String getJwkSetUrl() {
    return this.issuer + "/protocol/openid-connect/certs";
  }
}
