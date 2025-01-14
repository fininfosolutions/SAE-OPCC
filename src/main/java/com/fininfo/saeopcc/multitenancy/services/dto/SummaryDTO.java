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
public class SummaryDTO {
  private BigDecimal calledAmount;
  private BigDecimal calledQuantity;
  private BigDecimal remainingCapital;
  private BigDecimal notCalledPercentage;
  private BigDecimal remainingQuantity;
  private BigDecimal totalSubscribed;
  private BigDecimal quantitySubscribed;
  private BigDecimal quantityUnsubscribed;
  private BigDecimal totalUnsubscribed;
  private BigDecimal notCalledAmount;
  private BigDecimal notCalledQuantity;
  private BigDecimal releasedAmount;
  private BigDecimal releasedQuantity;
  private BigDecimal notReleasedAmount;
  private BigDecimal notReleasedQuantity;
}
