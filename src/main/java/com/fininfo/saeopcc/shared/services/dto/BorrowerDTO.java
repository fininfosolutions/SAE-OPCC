package com.fininfo.saeopcc.shared.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class BorrowerDTO extends RoleDTO {

  private Long id;

  public BorrowerDTO() {
    super();
  }
}
