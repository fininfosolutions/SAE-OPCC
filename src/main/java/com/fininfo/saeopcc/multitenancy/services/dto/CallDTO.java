package com.fininfo.saeopcc.multitenancy.services.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallOrigin;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class CallDTO {
  private Long id;
  private String description;
  private LocalDate appealDate;
  private LocalDate closingDate;
  private BigDecimal appealAmount;
  private BigDecimal percentage;
  private BigDecimal unfundedAmount;
  private BigDecimal appealQuantity;
  private BigDecimal sousAmount;
  private BigDecimal sousQuantity;
  private BigDecimal unfundedQuantity;
  private Long deviseId;
  private CallStatus callStatus;
  private CallOrigin origin;
  private String dinvestmentPeriod;
  private String investmentPeriod;
 
}
