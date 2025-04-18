package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call")
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Call extends AbstractAuditingEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private String reference;

  private LocalDate callDate;

  @Column(precision = 21, scale = 4)
  private BigDecimal calledAmount;

  @Column(precision = 21, scale = 4)
  private BigDecimal percentage;

  @Column(precision = 21, scale = 4)
  private BigDecimal remainingAmount;

  @Column(precision = 21, scale = 4)
  private BigDecimal remainingQuantity;

  @Column(precision = 21, scale = 4)
  private BigDecimal calledQuantity;

  @Enumerated(EnumType.STRING)
  private CallStatus status;

  private String message;

  @ManyToOne @EqualsAndHashCode.Exclude private Subscription subscription;
  @ManyToOne @EqualsAndHashCode.Exclude private CallEvent callEvent;
  @ManyToOne @EqualsAndHashCode.Exclude private SecuritiesAccount securitiesAccount;
}
