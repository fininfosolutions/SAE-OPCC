package com.fininfo.saeopcc.shared.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** A DTO for the {@link com.backupswift.referentiel.domain.Issuer} entity. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class IssuerDTO extends RoleDTO {

  private Long id;

  private String economicAgentCategoryCode;

  private String socioProfessionalCategoryCode;

  private String clientSegment;

  private String techAccount;
}
