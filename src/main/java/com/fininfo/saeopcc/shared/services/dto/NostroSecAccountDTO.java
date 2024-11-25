package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.MarketNature;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NostroSecAccountDTO extends AccountDTO implements Serializable {

  private Long id;
  private MarketNature market;
  private Long clearingHouseId;
  private String clearingHouseDescription;
  private Long clearingMemberId;
  private String clearingMemberDescription;
  private Long custodianId;
  private String custodianDescription;
}
