package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@Entity
@Table(name = "issuer", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Issuer extends Role {

  @Id private Long id;

  @Column(name = "techAccount")
  private String techAccount;

  @Column(name = "client_segment")
  private String clientSegment;
}
