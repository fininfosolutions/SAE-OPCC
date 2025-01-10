package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
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
public class CallDTO {
  private Long id;
  private String description;
  private String reference;

  private LocalDate callDate;
  private BigDecimal calledAmount;
  private BigDecimal percentage;
  private BigDecimal remainingAmount;
  private BigDecimal remainingQuantiy;
  private BigDecimal calledQuantiy;
  private CallStatus status;
  private String message;
  private Long subscriptionId;
  private Long callEventId;
  private Long securitiesAccountId;
  private Long securitiesAccountAssetId;
}
