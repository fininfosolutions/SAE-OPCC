package com.fininfo.saeopcc.shared.services.dto;

import java.io.Serializable;
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
public class AmortizationTableEntryDTO implements Serializable {
  private Long id;
  private Integer annuityNumber;
  private LocalDate paymentDate;
  private BigDecimal principal;
  private BigDecimal couponPreTax;
  private BigDecimal TVA10;
  private BigDecimal annuity;
  private BigDecimal outstandingamount;
  private BigDecimal rate;
  private LocalDate effectDate;
  private Long bondId;
}
