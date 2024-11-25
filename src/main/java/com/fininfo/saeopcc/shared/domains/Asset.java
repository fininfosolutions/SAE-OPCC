package com.fininfo.saeopcc.shared.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import com.fininfo.saeopcc.shared.domains.enumeration.BusinessRisk;
import com.fininfo.saeopcc.shared.domains.enumeration.DetentionForm;
import com.fininfo.saeopcc.shared.domains.enumeration.LegalStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.MarketType;
import com.fininfo.saeopcc.shared.domains.enumeration.QuantityType;
import com.fininfo.saeopcc.shared.domains.enumeration.QuotationPriceMode;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import com.fininfo.saeopcc.shared.domains.enumeration.SettlementPlace;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asset", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Asset extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id private Long id;

  @Column(name = "reference")
  private String reference;

  @Column(name = "isin")
  private String isin;

  @Column(name = "description")
  private String description;

  @Column(name = "fundmanager")
  private String fundmanager;

  @Column(name = "short_code")
  private String shortCode;

  @Column(name = "aditional_description")
  private String aditionalDescription;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "deactivation_date")
  private LocalDate deactivationDate;

  @Column(name = "inscri_maroclear")
  private Boolean inscriMaroclear;

  @Column(name = "securities_in_circulation_number")
  private Integer securitiesInCirculationNumber;

  @Column(name = "premium_date")
  private LocalDate premiumDate;

  @Column(name = "dematerialized")
  private Boolean dematerialized;

  @Column(name = "tax_exemption")
  private Boolean taxExemption;

  @Column(name = "listed")
  private Boolean listed;

  @Column(name = "canceled")
  private Boolean canceled;

  @Column(name = "cancellation_date")
  private LocalDate cancellationDate;

  @Column(name = "quotation_first_date")
  private LocalDate quotationFirstDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "asset_type")
  private AssetType assetType;

  @Enumerated(EnumType.STRING)
  @Column(name = "detention_form")
  private DetentionForm detentionForm;

  @Enumerated(EnumType.STRING)
  @Column(name = "market_type")
  private MarketType marketType;

  @Enumerated(EnumType.STRING)
  @Column(name = "business_risk")
  private BusinessRisk businessRisk;

  @Enumerated(EnumType.STRING)
  @Column(name = "quotation_price_mode")
  private QuotationPriceMode quotationPriceMode;

  @Enumerated(EnumType.STRING)
  @Column(name = "quantity_type")
  private QuantityType quantityType;

  @Enumerated(EnumType.STRING)
  @Column(name = "settlement_place")
  private SettlementPlace settlementPlace;

  @Enumerated(EnumType.STRING)
  @Column(name = "legal_status")
  private LegalStatus legalStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "security_form")
  private SecurityForm securityForm;

  @Column(name = "code")
  private String code;

  @Column(name = "nominal_value", precision = 21, scale = 2)
  private BigDecimal nominalValue;

  @Column(name = "current_nominal_value", precision = 21, scale = 2)
  private BigDecimal currentNominalValue;

  @Column(name = "issue_premium", precision = 21, scale = 2)
  private BigDecimal issuePremium;

  @Column(name = "amount_premium", precision = 21, scale = 2)
  private BigDecimal amountPremium;

  @Column(name = "amount_outstanding_premium", precision = 21, scale = 2)
  private BigDecimal amountOutstandingPremium;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private QuotationGroup quotationGroup;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private SecurityClass securityClass;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private SecurityClassification securityClassification;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private FiscalNature fiscalNature;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private SecurityType securityType;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private SettlementType settlementType;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private SecuritySector securitySector;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private Devise devise;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private Custodian custodian;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private Centraliser centraliser;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private Issuer issuer;

  @ManyToOne
  @JsonIgnoreProperties(value = "assets", allowSetters = true)
  private CSD csd;

  @ManyToOne
  @JsonIgnoreProperties(value = "funds", allowSetters = true)
  private SDG sdg;
}
