package com.fininfo.saeopcc.shared.services.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class BankDTO extends RoleDTO {

  private Long id;

  private LocalDate affiliationDate;

  private String economicAgentCategoryCode;

  private String socioProfessionalCategoryCode;

  private Long branch;

  private String codeBic;

  private List<CashAccountDTO> cashAccounts;
}
