package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.AppealOrigin;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.AppealStatus;
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
public class AppealDTO {
  private Long id;
  private String description;
  private LocalDate appealDate;
  private LocalDate closingDate;
  private BigDecimal appealAmount;
  private BigDecimal percentage;
  private BigDecimal unfundedAmount;
  private BigDecimal appealQuantity;
  private BigDecimal sousAmount;
  private BigDecimal subscriptionAmount;
  private BigDecimal sousQuantity;
  private BigDecimal unfundedQuantity;
  private Long subscriptionId;
  private Long deviseId;
  private AppealStatus appealStatus;
  private AppealOrigin origin;
  private String dinvestmentPeriod;
  private String investmentPeriod;
}
