package com.fininfo.saeopcc.shared.services.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class SecuritySegregationDTO implements Serializable {

  private Long id;

  private String code;

  private String description;
}
