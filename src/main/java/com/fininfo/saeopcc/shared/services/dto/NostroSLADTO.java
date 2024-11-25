package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.AccountNature;
import com.fininfo.saeopcc.shared.domains.enumeration.OperationType;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import com.fininfo.saeopcc.shared.domains.enumeration.TransactionType;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class NostroSLADTO implements Serializable {

  private Long id;

  private OperationType operationType;

  private TransactionType transactionType;

  private AccountNature accountNature;
  private SecurityForm securityForm;

  private Long accountCategoryId;

  private Long custodianId;

  private Long nostroSecAccountId;
  private Long nostroSecAccountAccountNumber;
}
