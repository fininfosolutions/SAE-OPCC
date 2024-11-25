package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.AccountClassification;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountNature;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountDTO implements Serializable {

  private Long id;
  private String description;
  private String accountNumber;
  private AccountClassification accountClassification;
  private AccountType accountType;
  private String root;
  private String code1;
  private AccountNature accountNature;
  private String code2;
  private Boolean isActive;
}
