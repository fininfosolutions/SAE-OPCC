package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "liberation")
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Liberation extends AbstractAuditingEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private String reference;
  private String message;

  private BigDecimal percentage;

  private LocalDate releasedAmount;

  private BigDecimal releasedQuantity;
  private BigDecimal remainingAmount;

  private LocalDate liberationDate;

  @Enumerated(EnumType.STRING)
  private LiberationStatus status;

  private BigDecimal remainingQuantity;

  @ManyToOne @EqualsAndHashCode.Exclude private GlobalLiberation liberationEvent;
  @ManyToOne @EqualsAndHashCode.Exclude private Call call;
  @ManyToOne @EqualsAndHashCode.Exclude private SecuritiesAccount securitiesAccount;
}
