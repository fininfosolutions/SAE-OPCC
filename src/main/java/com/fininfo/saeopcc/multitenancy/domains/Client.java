package com.fininfo.saeopcc.multitenancy.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.shared.domains.ClientCategory;
import com.fininfo.saeopcc.shared.domains.Countries;
import com.fininfo.saeopcc.shared.domains.ResidenceStatus;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.SecuritySector;
import com.fininfo.saeopcc.shared.domains.enumeration.ClientType;
import com.fininfo.saeopcc.shared.domains.enumeration.FiscalStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.JuridicalNature;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Client extends Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "mandate_manager")
  private String mandateManager;

  @Column(name = "mandate_manager_fax")
  private Integer mandateManagerFax;

  @Column(name = "mandate_manager_tel")
  private Integer mandateManagerTel;

  @Column(name = "mandate_manager_mail")
  private String mandateManagerMail;

  @Column(name = "ebanking")
  private Boolean ebanking;

  @Column(name = "isfund")
  private Boolean isfund;

  @Column(name = "ishouse")
  private Boolean isHouse;

  @Column(name = "bank_domiciliation")
  private String bankDomiciliation;

  @Column(name = "subject_tax")
  private Boolean subjectTax;

  @Column(name = "cash_depositor")
  private Boolean cashDepositor;

  @Enumerated(EnumType.STRING)
  @Column(name = "client_type")
  private ClientType clientType;

  @Column(name = "customer_analytics_segment")
  private Integer customerAnalyticsSegment;

  @Enumerated(EnumType.STRING)
  @Column(name = "fiscal_status")
  private FiscalStatus fiscalStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "juridical_nature")
  private JuridicalNature juridicalNature;

  @Enumerated(EnumType.STRING)
  @Column(name = "security_form")
  private SecurityForm securityForm;

  @Column(name = "is_resident")
  private Boolean isResident;

  @ManyToOne
  @JsonIgnoreProperties(value = "clients", allowSetters = true)
  private Countries residenceCountry;

  @ManyToOne
  @JsonIgnoreProperties(value = "clients", allowSetters = true)
  private ResidenceStatus residenceStatus;

  @ManyToOne
  @JsonIgnoreProperties(value = "clients", allowSetters = true)
  private ClientCategory clientCategory;

  @ManyToOne
  @JsonIgnoreProperties(value = "clients", allowSetters = true)
  private Countries nationality;

  @ManyToOne private SecuritySector securitySector;
}
