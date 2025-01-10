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
public class CallEventDTO {
  private Long id;
  private BigDecimal percentage;
  private String description;

  private String reference;

  private LocalDate closingDate;
  private LocalDate callDate;
  private BigDecimal calledAmount;
  private EventStatus eventStatus;
  private BigDecimal calledQuantity;
  private BigDecimal remainingQuantity;
  private BigDecimal remainingAmount;

  private String message;

  private Long issueId;
  private Long deviseId;
  private String deviseCodeAlpha;
}
