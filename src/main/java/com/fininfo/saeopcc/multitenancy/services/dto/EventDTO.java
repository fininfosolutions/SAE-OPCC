package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class EventDTO {
  private Long id;
  private String description;
  private LocalDate closingDate;
  private LocalDate appealDate;

  private BigDecimal globalSousAmount;
  private BigDecimal globalSousQuantity;
  private BigDecimal globalAppealAmount;
  private BigDecimal globalAppealQuantity;
  private BigDecimal globalSousInitialAmount;
  private BigDecimal globalSousInitialQuantity;

  private BigDecimal percentage;
  private EventStatus eventStatus;
  private BigDecimal globalUnfundedAmount;
  private BigDecimal globalUnfundedQuantity;
  private String dinvestmentPeriod;

  private String investmentPeriod;

  private Long issueId;
  private Long deviseId;
}
