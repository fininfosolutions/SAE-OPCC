package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.IntegerFilter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import com.fininfo.saeopcc.shared.domains.enumeration.BusinessRisk;
import com.fininfo.saeopcc.shared.domains.enumeration.LegalStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.MarketType;
import com.fininfo.saeopcc.shared.domains.enumeration.QuantityType;
import com.fininfo.saeopcc.shared.domains.enumeration.QuotationPriceMode;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetCriteria implements Serializable, Criteria {

  /** Class for filtering AssetType */
  public static class AssetTypeFilter extends Filter<AssetType> {

    public AssetTypeFilter() {}

    public AssetTypeFilter(AssetTypeFilter filter) {
      super(filter);
    }

    @Override
    public AssetTypeFilter copy() {
      return new AssetTypeFilter(this);
    }
  }

  /** Class for filtering MarketType */
  public static class MarketTypeFilter extends Filter<MarketType> {

    public MarketTypeFilter() {}

    public MarketTypeFilter(MarketTypeFilter filter) {
      super(filter);
    }

    @Override
    public MarketTypeFilter copy() {
      return new MarketTypeFilter(this);
    }
  }

  /** Class for filtering BusinessRisk */
  public static class BusinessRiskFilter extends Filter<BusinessRisk> {

    public BusinessRiskFilter() {}

    public BusinessRiskFilter(BusinessRiskFilter filter) {
      super(filter);
    }

    @Override
    public BusinessRiskFilter copy() {
      return new BusinessRiskFilter(this);
    }
  }

  /** Class for filtering BusinessRisk */
  public static class QuotationPriceModeFilter extends Filter<QuotationPriceMode> {

    public QuotationPriceModeFilter() {}

    public QuotationPriceModeFilter(QuotationPriceModeFilter filter) {
      super(filter);
    }

    @Override
    public QuotationPriceModeFilter copy() {
      return new QuotationPriceModeFilter(this);
    }
  }

  /** Class for filtering BusinessRisk */
  public static class QuantityTypeFilter extends Filter<QuantityType> {

    public QuantityTypeFilter() {}

    public QuantityTypeFilter(QuantityTypeFilter filter) {
      super(filter);
    }

    @Override
    public QuantityTypeFilter copy() {
      return new QuantityTypeFilter(this);
    }
  }

  public static class LegalStatusFilter extends Filter<LegalStatus> {

    public LegalStatusFilter() {}

    public LegalStatusFilter(LegalStatusFilter filter) {
      super(filter);
    }

    @Override
    public LegalStatusFilter copy() {
      return new LegalStatusFilter(this);
    }
  }

  public static class SecurityFormFilter extends Filter<SecurityForm> {

    public SecurityFormFilter() {}

    public SecurityFormFilter(SecurityFormFilter filter) {
      super(filter);
    }

    @Override
    public SecurityFormFilter copy() {
      return new SecurityFormFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter isin;

  private StringFilter reference;

  private StringFilter description;

  private StringFilter shortCode;

  private StringFilter aditionalDescription;

  private BooleanFilter active;

  private BooleanFilter inscriMaroclear;

  private StringFilter fundmanager;

  private LocalDateFilter deactivationDate;

  private IntegerFilter securitiesInCirculationNumber;

  private LocalDateFilter premiumDate;

  private BooleanFilter dematerialized;

  private BooleanFilter taxExemption;

  private BooleanFilter listed;

  private BooleanFilter canceled;

  private LocalDateFilter cancellationDate;

  private LocalDateFilter quotationFirstDate;

  private AssetTypeFilter assetType;

  private StringFilter code;

  private BigDecimalFilter nominalValue;

  private BigDecimalFilter currentNominalValue;

  private BigDecimalFilter issuePremium;

  private BigDecimalFilter amountPremium;

  private BigDecimalFilter amountOutstandingPremium;

  private LongFilter quotationGroupId;

  private MarketTypeFilter marketType;

  private BusinessRiskFilter businessRisk;

  private LongFilter fiscalNatureId;

  private SecurityFormFilter securityForm;
  private Boolean isinNotNull;

  private QuantityTypeFilter quantityType;

  private LongFilter securityTypeId;

  private QuotationPriceModeFilter quotationPriceMode;

  private LongFilter settlementTypeId;

  private LongFilter securitySectorId;

  private LongFilter custodianId;

  private LongFilter centraliserId;

  private LongFilter registrarId;

  private LegalStatusFilter legalStatus;

  private LongFilter issuerId;

  private LongFilter marketId;

  private LongFilter csdId;

  public AssetCriteria(AssetCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.isin = other.isin == null ? null : other.isin.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.fundmanager = other.fundmanager == null ? null : other.fundmanager.copy();
    this.shortCode = other.shortCode == null ? null : other.shortCode.copy();
    this.aditionalDescription =
        other.aditionalDescription == null ? null : other.aditionalDescription.copy();
    this.active = other.active == null ? null : other.active.copy();
    this.deactivationDate = other.deactivationDate == null ? null : other.deactivationDate.copy();
    this.securitiesInCirculationNumber =
        other.securitiesInCirculationNumber == null
            ? null
            : other.securitiesInCirculationNumber.copy();
    this.premiumDate = other.premiumDate == null ? null : other.premiumDate.copy();
    this.dematerialized = other.dematerialized == null ? null : other.dematerialized.copy();
    this.inscriMaroclear = other.inscriMaroclear == null ? null : other.inscriMaroclear.copy();
    this.taxExemption = other.taxExemption == null ? null : other.taxExemption.copy();
    this.listed = other.listed == null ? null : other.listed.copy();
    this.canceled = other.canceled == null ? null : other.canceled.copy();
    this.cancellationDate = other.cancellationDate == null ? null : other.cancellationDate.copy();
    this.quotationFirstDate =
        other.quotationFirstDate == null ? null : other.quotationFirstDate.copy();
    this.assetType = other.assetType == null ? null : other.assetType.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.nominalValue = other.nominalValue == null ? null : other.nominalValue.copy();
    this.currentNominalValue =
        other.currentNominalValue == null ? null : other.currentNominalValue.copy();
    this.issuePremium = other.issuePremium == null ? null : other.issuePremium.copy();
    this.amountPremium = other.amountPremium == null ? null : other.amountPremium.copy();
    this.amountOutstandingPremium =
        other.amountOutstandingPremium == null ? null : other.amountOutstandingPremium.copy();
    this.quotationGroupId = other.quotationGroupId == null ? null : other.quotationGroupId.copy();
    this.marketType = other.marketType == null ? null : other.marketType.copy();
    this.businessRisk = other.businessRisk == null ? null : other.businessRisk.copy();
    this.fiscalNatureId = other.fiscalNatureId == null ? null : other.fiscalNatureId.copy();
    this.securityForm = other.securityForm == null ? null : other.securityForm.copy();
    this.quantityType = other.quantityType == null ? null : other.quantityType.copy();
    this.securityTypeId = other.securityTypeId == null ? null : other.securityTypeId.copy();
    this.quotationPriceMode =
        other.quotationPriceMode == null ? null : other.quotationPriceMode.copy();
    this.settlementTypeId = other.settlementTypeId == null ? null : other.settlementTypeId.copy();
    this.securitySectorId = other.securitySectorId == null ? null : other.securitySectorId.copy();
    this.custodianId = other.custodianId == null ? null : other.custodianId.copy();
    this.centraliserId = other.centraliserId == null ? null : other.centraliserId.copy();
    this.registrarId = other.registrarId == null ? null : other.registrarId.copy();
    this.legalStatus = other.legalStatus == null ? null : other.legalStatus.copy();
    this.issuerId = other.issuerId == null ? null : other.issuerId.copy();
    this.marketId = other.marketId == null ? null : other.marketId.copy();
    this.csdId = other.csdId == null ? null : other.csdId.copy();
  }

  @Override
  public AssetCriteria copy() {
    return new AssetCriteria(this);
  }

  // @Override
  // public boolean equals(Object o) {
  //   if (this == o) {
  //     return true;
  //   }
  //   if (o == null || getClass() != o.getClass()) {
  //     return false;
  //   }
  //   final AssetCriteria that = (AssetCriteria) o;
  //   return Objects.equals(id, that.id)
  //       && Objects.equals(isin, that.isin)
  //       && Objects.equals(description, that.description)
  //       && Objects.equals(shortCode, that.shortCode)
  //       && Objects.equals(aditionalDescription, that.aditionalDescription)
  //       && Objects.equals(active, that.active)
  //       && Objects.equals(deactivationDate, that.deactivationDate)
  //       && Objects.equals(securitiesInCirculationNumber, that.securitiesInCirculationNumber)
  //       && Objects.equals(premiumDate, that.premiumDate)
  //       && Objects.equals(dematerialized, that.dematerialized)
  //       && Objects.equals(taxExemption, that.taxExemption)
  //       && Objects.equals(listed, that.listed)
  //       && Objects.equals(canceled, that.canceled)
  //       && Objects.equals(cancellationDate, that.cancellationDate)
  //       && Objects.equals(quotationFirstDate, that.quotationFirstDate)
  //       && Objects.equals(assetType, that.assetType)
  //       && Objects.equals(code, that.code)
  //       && Objects.equals(nominalValue, that.nominalValue)
  //       && Objects.equals(currentNominalValue, that.currentNominalValue)
  //       && Objects.equals(issuePremium, that.issuePremium)
  //       && Objects.equals(amountPremium, that.amountPremium)
  //       && Objects.equals(amountOutstandingPremium, that.amountOutstandingPremium)
  //       && Objects.equals(quotationGroupId, that.quotationGroupId)
  //       && Objects.equals(marketType, that.marketType)
  //       && Objects.equals(businessRisk, that.businessRisk)
  //       && Objects.equals(fiscalNatureId, that.fiscalNatureId)
  //       && Objects.equals(securityForm, that.securityForm)
  //       && Objects.equals(quantityType, that.quantityType)
  //       && Objects.equals(securityTypeId, that.securityTypeId)
  //       && Objects.equals(quotationPriceMode, that.quotationPriceMode)
  //       && Objects.equals(settlementTypeId, that.settlementTypeId)
  //       && Objects.equals(securitySectorId, that.securitySectorId)
  //       && Objects.equals(custodianId, that.custodianId)
  //       && Objects.equals(centraliserId, that.centraliserId)
  //       && Objects.equals(registrarId, that.registrarId)
  //       && Objects.equals(legalStatus, that.legalStatus)
  //       && Objects.equals(issuerId, that.issuerId)
  //       && Objects.equals(marketId, that.marketId)
  //       && Objects.equals(csdId, that.csdId);
  // }

  // @Override
  // public int hashCode() {
  //   return Objects.hash(
  //       id,
  //       isin,
  //       description,
  //       shortCode,
  //       aditionalDescription,
  //       active,
  //       deactivationDate,
  //       securitiesInCirculationNumber,
  //       premiumDate,
  //       dematerialized,
  //       taxExemption,
  //       listed,
  //       canceled,
  //       cancellationDate,
  //       quotationFirstDate,
  //       assetType,
  //       code,
  //       nominalValue,
  //       currentNominalValue,
  //       issuePremium,
  //       amountPremium,
  //       amountOutstandingPremium,
  //       quotationGroupId,
  //       marketType,
  //       businessRisk,
  //       fiscalNatureId,
  //       securityForm,
  //       quantityType,
  //       securityTypeId,
  //       quotationPriceMode,
  //       settlementTypeId,
  //       securitySectorId,
  //       custodianId,
  //       centraliserId,
  //       registrarId,
  //       legalStatus,
  //       issuerId,
  //       marketId,
  //       csdId);
  // }

  // // prettier-ignore
  // @Override
  // public String toString() {
  //   return "AssetCriteria{"
  //       + (id != null ? "id=" + id + ", " : "")
  //       + (isin != null ? "isin=" + isin + ", " : "")
  //       + (description != null ? "description=" + description + ", " : "")
  //       + (shortCode != null ? "shortCode=" + shortCode + ", " : "")
  //       + (aditionalDescription != null
  //           ? "aditionalDescription=" + aditionalDescription + ", "
  //           : "")
  //       + (active != null ? "active=" + active + ", " : "")
  //       + (deactivationDate != null ? "deactivationDate=" + deactivationDate + ", " : "")
  //       + (securitiesInCirculationNumber != null
  //           ? "securitiesInCirculationNumber=" + securitiesInCirculationNumber + ", "
  //           : "")
  //       + (premiumDate != null ? "premiumDate=" + premiumDate + ", " : "")
  //       + (dematerialized != null ? "dematerialized=" + dematerialized + ", " : "")
  //       + (taxExemption != null ? "taxExemption=" + taxExemption + ", " : "")
  //       + (listed != null ? "listed=" + listed + ", " : "")
  //       + (canceled != null ? "canceled=" + canceled + ", " : "")
  //       + (cancellationDate != null ? "cancellationDate=" + cancellationDate + ", " : "")
  //       + (quotationFirstDate != null ? "quotationFirstDate=" + quotationFirstDate + ", " : "")
  //       + (assetType != null ? "assetType=" + assetType + ", " : "")
  //       + (code != null ? "code=" + code + ", " : "")
  //       + (nominalValue != null ? "nominalValue=" + nominalValue + ", " : "")
  //       + (currentNominalValue != null ? "currentNominalValue=" + currentNominalValue + ", " :
  // "")
  //       + (issuePremium != null ? "issuePremium=" + issuePremium + ", " : "")
  //       + (amountPremium != null ? "amountPremium=" + amountPremium + ", " : "")
  //       + (amountOutstandingPremium != null
  //           ? "amountOutstandingPremium=" + amountOutstandingPremium + ", "
  //           : "")
  //       + (quotationGroupId != null ? "quotationGroupId=" + quotationGroupId + ", " : "")
  //       + (marketType != null ? "marketType=" + marketType + ", " : "")
  //       + (businessRisk != null ? "businessRisk=" + businessRisk + ", " : "")
  //       + (fiscalNatureId != null ? "fiscalNatureId=" + fiscalNatureId + ", " : "")
  //       + (securityForm != null ? "securityForm=" + securityForm + ", " : "")
  //       + (quantityType != null ? "quantityType=" + quantityType + ", " : "")
  //       + (securityTypeId != null ? "securityTypeId=" + securityTypeId + ", " : "")
  //       + (quotationPriceMode != null ? "quotationPriceMode=" + quotationPriceMode + ", " : "")
  //       + (settlementTypeId != null ? "settlementTypeId=" + settlementTypeId + ", " : "")
  //       + (securitySectorId != null ? "securitySectorId=" + securitySectorId + ", " : "")
  //       + (custodianId != null ? "custodianId=" + custodianId + ", " : "")
  //       + (centraliserId != null ? "centraliserId=" + centraliserId + ", " : "")
  //       + (registrarId != null ? "registrarId=" + registrarId + ", " : "")
  //       + (legalStatus != null ? "legalStatus=" + legalStatus + ", " : "")
  //       + (issuerId != null ? "issuerId=" + issuerId + ", " : "")
  //       + (marketId != null ? "marketId=" + marketId + ", " : "")
  //       + (csdId != null ? "csdId=" + csdId + ", " : "")
  //       + "}";
}
