package com.fininfo.saeopcc.shared.services.dto;

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
/** A DTO for the {@link com.backupswift.referentiel.domain.IssuerRiskCategory} entity. */
public class IssuerRiskCategoryDTO implements Serializable {

  private Long id;

  private String code;

  private String description;
}
