package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.EntityType;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class BusinessEntityDTO implements Serializable {

  private Long id;

  private String identifier;

  private EntityType entityType;

  private String entityDescription;

  private LocalDate incroporationDate;

  private Boolean mainEntity;

  private Long nationalityId;

  private Long residenceId;

  // private Set<RoleDTO> roles;
}
