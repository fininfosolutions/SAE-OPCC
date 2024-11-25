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
public class FundOrganismDTO implements Serializable {

  private Long id;

  private String identifier;

  private String description;

  private String longName;

  private Long fundCustodianId;

  private Long fundManagerId;
}
