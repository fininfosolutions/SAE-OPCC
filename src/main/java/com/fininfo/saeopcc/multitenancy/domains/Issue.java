package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.enumeration.IssueStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "issue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Issue extends AbstractAuditingEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "current_step")
  private Integer currentStep;

  @Enumerated(EnumType.STRING)
  @Column(name = "issue_status")
  private IssueStatus issueStatus;

  @Column(name = "description")
  private String description;

  @Column(name = "opening_date")
  private LocalDate openingDate;

  @Column(name = "closing_date")
  private LocalDate closingDate;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "price")
  private BigDecimal price;

  @OneToOne private IssueAccount issueAccount;

  private BigDecimal initialClosingAmount;
  private BigDecimal maximumLimitAmount;
  private BigDecimal nextClosingAmount;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "issue")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Subscription> subscriptions = new HashSet<>();
}
