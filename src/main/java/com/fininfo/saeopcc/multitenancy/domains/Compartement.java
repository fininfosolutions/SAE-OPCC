package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.shared.domains.Devise;
import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.IssueAccount;
import com.fininfo.saeopcc.shared.domains.enumeration.ShareClass;
import com.fininfo.saeopcc.shared.domains.enumeration.ShareForm;
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
import javax.persistence.OneToOne;
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
@Table(name = "compartement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Compartement {

  @Id private Long id;

  private String fonds;

  private String reference;

  @Column(name = "extension_decision")
  private String extensionDecision;

  @Column(name = "investment_period")
  private String investmentPeriod;

  @Column(name = "dinvestment_period")
  private String dinvestmentPeriod;

  @Column(name = "social_exercise")
  private BigDecimal socialExercise;

  @Column(name = "investment_orientation")
  private String investmentOrientation;

  @Column(name = "geographic_intervention")
  private String geographicIntervention;

  @Column(name = "international_strategy")
  private String internationalStrategy;

  @Column(name = "investment_size")
  private BigDecimal investmentSize;

  @Column(name = "investment_policy")
  private String investmentPolicy;

  @Column(name = "debt_policy")
  private String debtPolicy;

  @Column(name = "business_practices")
  private String businessPractices;

  @Column(name = "environmental_protection")
  private String environmentalProtection;

  @Column(name = "reinvestment_policy")
  private String reinvestmentPolicy;

  @Enumerated(EnumType.STRING)
  @Column(name = "share_form")
  private ShareForm shareForm;

  private String period;

  @Column(name = "approval_date")
  private LocalDate approvalDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "target_size")
  private BigDecimal targetSize;

  @Column(name = "sector_limit")
  private Double sectorLimit;

  @Enumerated(EnumType.STRING)
  @Column(name = "share_class")
  private ShareClass shareClass;

  @ManyToOne private Devise devise;

  @ManyToOne private Client client;

  @OneToOne private Fund fund;
  @OneToOne private IssueAccount issueAccount;
}
