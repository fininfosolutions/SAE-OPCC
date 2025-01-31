package com.fininfo.saeopcc.multitenancy.services.dto;

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
public class SubscriberDetailsBlocDTO {

  private String subscriberName;
  private BigDecimal totalSubscribedQuantity;
  private BigDecimal totalSubscribedAmount;
  private BigDecimal totalCalledAmount;
  private BigDecimal totalCalledQuantity;
  private BigDecimal totalReleasedAmount;
  private BigDecimal totalReleasedQuantity;
  private LocalDate date;
  private BigDecimal notReleasedAmount;
  private BigDecimal notReleasedQuantity;
}
