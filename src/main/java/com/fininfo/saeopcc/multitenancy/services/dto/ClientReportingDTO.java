package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.ReportingLiberationStatus;
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
public class ClientReportingDTO {
  private Long clientId;
  private String fundName;
  private BigDecimal totalSubscribedAmount;
  private BigDecimal totalCalledAmount;
  private BigDecimal totalCalledQuantity;
  private BigDecimal totalReleasedAmount;
  private BigDecimal totalReleasedQuantity;

  private BigDecimal notCalledAmount;
  private BigDecimal notCalledQuantity;

  private BigDecimal notReleasedAmount;
  private BigDecimal notReleasedQuantity;

  private ReportingLiberationStatus releaseStatus;
  private String investmentPeriod;
  private String disinvestmentPeriod;
}
