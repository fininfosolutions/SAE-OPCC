package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.shared.services.dto.RoleDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class ShareholderDTO extends RoleDTO {

  private Long id;
}
