package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.Origin;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.Custodian;
import com.fininfo.saeopcc.shared.domains.enumeration.TransactionType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "subscription")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Subscription extends AbstractAuditingEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "reference")
  private String reference;

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type")
  private TransactionType transactionType;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "maturity_date")
  private LocalDate maturityDate;

  @Column(name = "rate")
  private BigDecimal rate;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "settlement_date")
  private LocalDate settlementDate;

  @Column(name = "message")
  private String message;

  @Column(name = "ecart")
  private Boolean ecart;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private SubscriptionStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "origin")
  private Origin origin;

  @ManyToOne private Custodian custodian;

  @ManyToOne private Shareholder shareholder;
}
