package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.Origin;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionDirection;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.TransactionType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class SubscriptionDTO implements Serializable {

  private Long id;

  private String reference;

  private TransactionType transactionType;

  private String message;
  private Boolean ecart;

  private BigDecimal quantity;

  private LocalDate maturityDate;

  private BigDecimal rate;

  private BigDecimal amount;

  private LocalDate settlementDate;

  private SubscriptionStatus status;

  private String counterpartReference;
  private String counterpartDescription;
  private String counterpartExternalReference;
  private SubscriptionDirection subscriptionDirection;

  private Long custodianId;

  private String custodianReference;
  private String custodianDescription;
  private String custodianExternalReference;

  private Long counterpartId;

  private Long shareholderId;
  private Long issueId;
  private String shareholderReference;
  private String shareholderDescription;
  private Origin origin;
}
