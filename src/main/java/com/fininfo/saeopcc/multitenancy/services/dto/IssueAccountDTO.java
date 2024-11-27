package com.fininfo.saeopcc.multitenancy.services.dto;

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
public class IssueAccountDTO implements Serializable {
  private Long id;
  private BigDecimal issueaccountNumber;
  private LocalDate openingaccountDate;
  private BigDecimal securitiesquantity;
  private Boolean actif;
  private String reference;
  private String description;
  private String valueDescription;

  private BigDecimal amount;
  private BigDecimal price;
  private LocalDate closingAccountDate;
  private Long compartementId;
  private String compartementFundCode;
  private String compartementFundIsin;
}
