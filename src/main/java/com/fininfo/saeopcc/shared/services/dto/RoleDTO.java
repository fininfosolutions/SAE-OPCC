package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** A DTO for the {@link com.backupswift.referentiel.domain.Role} entity. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class RoleDTO implements Serializable {

  private Long id;

  private String description;

  private ActingAs actingAs;

  private String affiliedCode;

  private String definition;

  private String reference;

  private String externalReference;

  private Boolean active;

  private Long economicAgentCategoryId;

  private Long socioProfessionalCategoryId;

  private List<AddressDTO> addresses;
}
