package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class GlobalLiberationDTO {
  private Long id;
  private String description;

  private String reference;

  private BigDecimal percentage;

  private EventStatus eventStatus;
  private Long callEventId;
}
