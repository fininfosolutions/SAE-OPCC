package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.Direction;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.InvestmentType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class MovementDTO {
  private Long id;
  private String reference;
  private String instructionReference;

  private Long instructionId;

  private TransactionType transactionType;
  private MovementStatus status;
  private LocalDate movementDate;

  private LocalDate impactedDate;
  private Boolean impacted;
  private Boolean htm;

  private Boolean reversed;
  private BigDecimal amount;

  private String assetAssetType;

  private Long assetId;

  private String assetIsin;
  private MovementType type;
  private String nostroSecurityAccount;
  private String custodian;
  private Direction sens;
  private Integer direction;
  private InvestmentType investmentType;

  private String assetDescription;
  private String assetCode;

  private BigDecimal quantity;

  private Long accountId;
  private String clientDescription;

  private String accountAccountNumber;
  private AccountType accountAccountType;
}
