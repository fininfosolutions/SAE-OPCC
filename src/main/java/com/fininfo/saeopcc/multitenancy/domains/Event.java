package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.Devise;
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
@Table(name = "event")
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Event extends AbstractAuditingEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  private LocalDate closingDate;

  private LocalDate appealDate;
  private BigDecimal globalSousInitialAmount;
  private BigDecimal globalSousInitialQuantity;

  private BigDecimal globalSousAmount;
  private BigDecimal globalSousQuantity;
  private BigDecimal globalAppealAmount;
  private BigDecimal globalAppealQuantity;

  private BigDecimal percentage;

  @Enumerated(EnumType.STRING)
  private EventStatus eventStatus;

  private BigDecimal globalUnfundedAmount;
  private BigDecimal globalUnfundedQuantity;
  private String dinvestmentPeriod;

  private String investmentPeriod;

  @ManyToOne @EqualsAndHashCode.Exclude private Issue issue;
  @ManyToOne private Devise devise;
}
