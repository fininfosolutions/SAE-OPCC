package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "saeconfig", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@NoArgsConstructor
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class SAEConfig extends AbstractAuditingEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "value")
  private String value;
}
