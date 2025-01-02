package com.fininfo.saeopcc.multitenancy.domains;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fininfo.saeopcc.shared.domains.Devise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call_event")
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class CallEvent extends Event  {

  @Id
  private Long id;
  private LocalDate closingDate;
  private LocalDate appealDate;
  private BigDecimal globalSousInitialAmount;
  private BigDecimal globalSousInitialQuantity;
  private BigDecimal globalSousAmount;
  private BigDecimal globalSousQuantity;
  private BigDecimal globalAppealAmount;
  private BigDecimal globalAppealQuantity;
  private BigDecimal globalUnfundedAmount;
  private BigDecimal globalUnfundedQuantity;
  private String dinvestmentPeriod;
  private String investmentPeriod;

  @ManyToOne @EqualsAndHashCode.Exclude private Issue issue;
  @ManyToOne private Devise devise;
}
