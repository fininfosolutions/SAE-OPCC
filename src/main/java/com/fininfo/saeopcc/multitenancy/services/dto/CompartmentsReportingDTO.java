package com.fininfo.saeopcc.multitenancy.services.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class CompartmentsReportingDTO {

  private String fundName;
  private String compartementName;

  private BigDecimal totalSubscribedAmount;
  private BigDecimal totalCalledAmount;
  private BigDecimal totalCalledQuantity;
  private BigDecimal totalReleasedAmount;
  private BigDecimal totalReleasedQuantity;

  private BigDecimal notCalledAmount;
  private BigDecimal notCalledQuantity;

  private BigDecimal notReleasedAmount;
  private BigDecimal notReleasedQuantity;

  private String releaseStatus;
  private String investmentPeriod;
  private String disinvestmentPeriod;
}
