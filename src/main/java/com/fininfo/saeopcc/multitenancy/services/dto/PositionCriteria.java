package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.configuration.ZonedDateTimeFilter;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionCriteria implements Serializable, Criteria {

  private LongFilter id;
  private StringFilter reference;
  private StringFilter clientSecurityAccount;
  private StringFilter isin;
  private StringFilter clientDescription;
  private BigDecimalFilter quantity;
  private LocalDateFilter positionDate;
  private LocalDateFilter createdDate;
  private StringFilter createdBy;
  private LocalDateFilter lastModifiedDate;
  private StringFilter lastModifiedBy;

  private StringFilter client;
  private StringFilter assetType;
  private StringFilter assetDescription;
  private ZonedDateTimeFilter valueDate;
  private ZonedDateTimeFilter endDate;
  private LongFilter assetId;
  private LongFilter clientId;
  private Boolean reversed;

  public PositionCriteria(PositionCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.reference = other.reference == null ? null : other.reference.copy();
    this.clientSecurityAccount =
        other.clientSecurityAccount == null ? null : other.clientSecurityAccount.copy();
    this.isin = other.isin == null ? null : other.isin.copy();
    this.clientDescription =
        other.clientDescription == null ? null : other.clientDescription.copy();
    this.quantity = other.quantity == null ? null : other.quantity.copy();
    this.positionDate = other.positionDate == null ? null : other.positionDate;
    this.createdDate = other.createdDate == null ? null : other.createdDate;
    this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
    this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate;
    this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();

    this.client = other.client == null ? null : other.client.copy();
    this.assetType = other.assetType == null ? null : other.assetType.copy();
    this.assetDescription = other.assetDescription == null ? null : other.assetDescription.copy();
    this.valueDate = other.valueDate == null ? null : other.valueDate.copy();
    this.endDate = other.endDate == null ? null : other.endDate.copy();
    this.assetId = other.assetId == null ? null : other.assetId.copy();
    this.clientId = other.clientId == null ? null : other.clientId.copy();
  }

  public PositionCriteria(
      LongFilter id,
      StringFilter reference,
      StringFilter clientSecurityAccount,
      StringFilter intermediaryDescription,
      StringFilter isin,
      StringFilter clientDescription,
      BigDecimalFilter quantity,
      LocalDateFilter positionDate,
      LocalDateFilter createdDate,
      StringFilter createdBy,
      LocalDateFilter lastModifiedDate,
      StringFilter lastModifiedBy,
      StringFilter client,
      StringFilter assetType,
      StringFilter assetDescription,
      ZonedDateTimeFilter valueDate,
      ZonedDateTimeFilter endDate,
      LongFilter assetId,
      LongFilter clientId) {
    this.id = id;
    this.reference = reference;
    this.clientSecurityAccount = clientSecurityAccount;
    this.isin = isin;
    this.clientDescription = clientDescription;
    this.quantity = quantity;
    this.positionDate = positionDate;
    this.createdDate = createdDate;
    this.createdBy = createdBy;
    this.lastModifiedDate = lastModifiedDate;
    this.lastModifiedBy = lastModifiedBy;

    this.client = client;
    this.assetType = assetType;
    this.assetDescription = assetDescription;
    this.valueDate = valueDate;
    this.endDate = endDate;
    this.assetId = assetId;
    this.clientId = clientId;
  }

  @Override
  public Criteria copy() {
    return new PositionCriteria(this);
  }
}
