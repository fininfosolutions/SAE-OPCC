package com.fininfo.saeopcc.shared.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Countries extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "short_name")
  private String shortName;

  @Column(name = "long_name")
  private String longName;

  @Column(name = "iso_3")
  private String iso3;

  @Column(name = "iso_2")
  private String iso2;

  @Column(name = "num_code")
  private Integer numCode;

  @ManyToOne
  @JsonIgnoreProperties(value = "countries", allowSetters = true)
  private FiscalAuthority fiscalAuthority;

  @ManyToOne
  @JsonIgnoreProperties(value = "countries", allowSetters = true)
  private Correspondent correspondent;

  @ManyToOne
  @JsonIgnoreProperties(value = "countries", allowSetters = true)
  private BusinessEntity businessEntity;
}
