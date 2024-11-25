package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.AddressTypes;
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
public class AddressDTO implements Serializable {

  private Long id;

  private String name;

  private AddressTypes type;

  private Long roleId;
}
