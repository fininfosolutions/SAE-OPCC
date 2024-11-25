package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.PriceStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class PriceDTO implements Serializable {

  private Long id;

  private LocalDate pricingDate;

  private BigDecimal closingPrice;

  private Boolean active;

  private PriceStatus status;

  private BigDecimal rate;

  private BigDecimal openingPrice;

  private BigDecimal averagePrice;

  private BigDecimal maxPrice;

  private BigDecimal minPrice;

  private BigDecimal offerPrice;

  private BigDecimal bidPrice;

  private BigDecimal issuePrice;

  private BigDecimal nominalValue;

  private BigDecimal currentNominalValue;

  private BigDecimal coupon;

  private BigDecimal netAssetValue;

  private BigDecimal expertValue;

  private Long assetId;
  private String assetIsin;
  private String assetShortCode;
  private Long providerId;
  private String providerReference;
  private String providerDescription;
}
