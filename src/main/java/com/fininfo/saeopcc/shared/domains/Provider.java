package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "provider", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@EqualsAndHashCode(callSuper = false)
public class Provider extends Role {

  @Id private Long id;

  @Column(name = "name")
  private String name;
}
