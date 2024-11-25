package com.fininfo.saeopcc.shared.services.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SAEConfigDTO implements Serializable {
  private Long id;
  private String code;
  private String value;
}
