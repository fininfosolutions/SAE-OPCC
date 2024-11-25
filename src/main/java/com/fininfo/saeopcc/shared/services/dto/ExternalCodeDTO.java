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
public class ExternalCodeDTO implements Serializable {

  private Long id;

  private String code;

  private String description;

  private Long codificationSourceId;
  private String codificationSourceCode;

  private Long roleId;

  private Long businessEntityId;

  private Long cashAccountId;

  private Long securitiesAccountId;

  private Long assetId;
}
