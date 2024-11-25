package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.CashAccountType;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class CashAccountDTO implements Serializable {

  private Long id;

  private String name;

  private String number;

  private Long clientId;

  private Long borrowerId;

  private Long lenderId;

  private Long proxyId;

  private Long correspondentId;

  private Long fundCustodianId;

  private Long accountsTypeId;

  private Long linkedCashAccountId;

  private CashAccountType cashAccountType;

  private String ibanNumber;

  private Long issuerId;

  private String accShortName;

  private String accLongName;
}
