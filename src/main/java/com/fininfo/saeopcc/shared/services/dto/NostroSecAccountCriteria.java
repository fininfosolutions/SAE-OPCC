package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.MarketNature;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NostroSecAccountCriteria extends AccountCriteria {

  public static class MarketFilter extends Filter<MarketNature> {

    public MarketFilter() {}

    public MarketFilter(NostroSecAccountCriteria.MarketFilter filter) {
      super(filter);
    }

    @Override
    public NostroSecAccountCriteria.MarketFilter copy() {
      return new NostroSecAccountCriteria.MarketFilter(this);
    }
  }

  private LongFilter id;
  private NostroSecAccountCriteria.MarketFilter market;
  private LongFilter clearingHouseId;
  private StringFilter clearingHouseDescription;
  private LongFilter clearingMemberId;
  private StringFilter clearingMemberDescription;
  private LongFilter custodianId;
  private StringFilter custodianDescription;

  public NostroSecAccountCriteria(NostroSecAccountCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.market = other.market == null ? null : other.market.copy();
    this.clearingHouseId = other.clearingHouseId == null ? null : other.clearingHouseId.copy();
    this.clearingHouseDescription =
        other.clearingHouseDescription == null ? null : other.clearingHouseDescription.copy();
    this.clearingMemberId = other.clearingMemberId == null ? null : other.clearingMemberId.copy();
    this.clearingMemberDescription =
        other.clearingMemberDescription == null ? null : other.clearingMemberDescription.copy();
    this.custodianId = other.custodianId == null ? null : other.custodianId.copy();
    this.custodianDescription =
        other.custodianDescription == null ? null : other.custodianDescription.copy();
  }

  public NostroSecAccountCriteria(
      LongFilter id,
      NostroSecAccountCriteria.MarketFilter market,
      LongFilter clearingHouseId,
      StringFilter clearingHouseDescription,
      LongFilter clearingMemberId,
      StringFilter clearingMemberDescription,
      LongFilter custodianId,
      StringFilter custodianDescription) {
    this.id = id;
    this.market = market;
    this.clearingHouseId = clearingHouseId;
    this.clearingHouseDescription = clearingHouseDescription;
    this.clearingMemberId = clearingMemberId;
    this.clearingMemberDescription = clearingMemberDescription;
    this.custodianId = custodianId;
    this.custodianDescription = custodianDescription;
  }

  @Override
  public NostroSecAccountCriteria copy() {
    return new NostroSecAccountCriteria(this);
  }
}
