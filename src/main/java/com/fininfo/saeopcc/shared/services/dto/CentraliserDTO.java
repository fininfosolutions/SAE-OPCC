package com.fininfo.saeopcc.shared.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class CentraliserDTO extends RoleDTO {

  private Long id;
  private String economicAgentCategoryCode;

  private String socioProfessionalCategoryCode;
}
