package com.fininfo.saeopcc.shared.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class FiscalAuthorityDTO extends RoleDTO {

  private Long id;

  private String economicAgentCategoryCode;

  private String socioProfessionalCategoryCode;
}
