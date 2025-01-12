package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
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
public class LiberationDTO {
  private Long id;

  private String description;
  private String reference;
  private String message;
  private BigDecimal percentage;

  private BigDecimal releasedAmount;

  private BigDecimal releasedQuantity;

  private BigDecimal remainingAmount;

  private LocalDate liberationDate;

  private LiberationStatus status;

  private BigDecimal remainingQuantity;
  private Long globalLiberationId;
  private Long callId;
  private Long securitiesAccountId;

  private BigDecimal callCalledAmount;
  private BigDecimal callCalledQuantity;
  private Long securitiesAccountAssetId;
  private String securitiesAccountAccountNumber;
  private String securitiesAccountShareholderDescription;
  private String callEventDeviseCodeAlpha;
}
