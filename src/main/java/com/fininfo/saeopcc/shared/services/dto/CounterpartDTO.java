package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.FiscalStatus;
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
public class CounterpartDTO extends RoleDTO {

  private Long id;

  private LocalDate affilationDate;

  private String economicAgentCategoryCode;

  private String socioProfessionalCategoryCode;
  private LocalDate affiliationDate;
  private FiscalStatus fiscalStatus;
}
