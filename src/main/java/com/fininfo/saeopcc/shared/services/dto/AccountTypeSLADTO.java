package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.Direction;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class AccountTypeSLADTO implements Serializable {
  private Long id;
  private TransactionType transactionType;
  private AccountType accountType;
  private String prefix;
  private Direction sens;
  private Direction sensCash;
  private Integer transDirection;
  private Integer transDirectionCash;
}
