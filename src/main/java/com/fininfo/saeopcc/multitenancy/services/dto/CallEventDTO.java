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
public class CallEventDTO extends EventDTO {
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

  private Long issueId;
  private String issueDescription;
  private LocalDate issueClosingDate;
  private LocalDate issueOpeningDate;
  private BigDecimal issueQuantity;
  private BigDecimal issueAmount;
  private BigDecimal issuePrice;
  private Long deviseId;
  private String deviseCodeAlpha;

}
