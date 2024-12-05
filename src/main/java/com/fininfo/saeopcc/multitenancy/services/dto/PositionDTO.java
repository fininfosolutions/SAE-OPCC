package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.InvestmentType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.PositionType;
import com.fininfo.saeopcc.shared.domains.enumeration.PositionNature;
import com.fininfo.saeopcc.shared.services.dto.AbstractAuditingEntityDTO;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PositionDTO extends AbstractAuditingEntityDTO {

  private Long id;

  private PositionType positionType;
  private String reference;

  private Instant positionDate;
  private PositionNature positionNature;

  private String nostroSecurityAccount;
  private String custodian;
  private String clientDescription;

  private LocalDate valueDate;
  private InvestmentType investmentType;

  private LocalDate endDate;
  private String accountAccountNumber;
  private Long accountId;
}
