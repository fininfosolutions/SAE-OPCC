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
public class ClientCategoryDTO implements Serializable {

  private Long id;

  private String type;

  private String description;

  private String nature;
}
