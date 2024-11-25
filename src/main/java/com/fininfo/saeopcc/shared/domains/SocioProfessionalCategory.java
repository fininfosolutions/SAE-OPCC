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
@Table(name = "socio_professional_category", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class SocioProfessionalCategory extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "description")
  private String description;

  @Column(name = "short_description")
  private String shortDescription;

  @Column(name = "central_bank_code")
  private String centralBankCode;

  @ManyToOne
  @JsonIgnoreProperties(value = "socioProfessionalCategories", allowSetters = true)
  private Role role;

  public SocioProfessionalCategory code(String code) {
    this.code = code;
    return this;
  }

  public SocioProfessionalCategory description(String description) {
    this.description = description;
    return this;
  }

  public SocioProfessionalCategory shortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
    return this;
  }

  public SocioProfessionalCategory centralBankCode(String centralBankCode) {
    this.centralBankCode = centralBankCode;
    return this;
  }

  public SocioProfessionalCategory role(Role role) {
    this.role = role;
    return this;
  }
}
