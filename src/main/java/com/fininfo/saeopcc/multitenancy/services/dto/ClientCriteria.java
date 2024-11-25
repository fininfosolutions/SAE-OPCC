package com.fininfo.saeopcc.multitenancy.services.dto;

import java.io.Serializable;

import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.IntegerFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.ClientType;
import com.fininfo.saeopcc.shared.domains.enumeration.FiscalStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCriteria implements Serializable, Criteria {
  /** Class for filtering ClientType */
  public static class ClientTypeFilter extends Filter<ClientType> {

    public ClientTypeFilter() {}

    public ClientTypeFilter(ClientTypeFilter filter) {
      super(filter);
    }

    @Override
    public ClientTypeFilter copy() {
      return new ClientTypeFilter(this);
    }
  }

  /** Class for filtering FiscalStatus */
  public static class FiscalStatusFilter extends Filter<FiscalStatus> {

    public FiscalStatusFilter() {}

    public FiscalStatusFilter(FiscalStatusFilter filter) {
      super(filter);
    }

    @Override
    public FiscalStatusFilter copy() {
      return new FiscalStatusFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;
  private StringFilter description;

  private StringFilter mandateManager;

  private IntegerFilter mandateManagerFax;

  private IntegerFilter mandateManagerTel;

  private StringFilter mandateManagerMail;

  private BooleanFilter ebanking;

  private StringFilter externalReference;

  private StringFilter bankDomiciliation;

  private BooleanFilter subjectTax;

  private ClientTypeFilter clientType;

  private IntegerFilter customerAnalyticsSegment;

  private FiscalStatusFilter fiscalStatus;

  private LongFilter legalCustomerCategoryId;

  private LongFilter serviceLevelId;

  private LongFilter tribunalCodeId;

  private LongFilter reportingLanguageId;

  private LongFilter subActivityId;

  private LongFilter taxCategoryId;

  private LongFilter residenceCountryId;

  private LongFilter subManagementActivityId;

  private LongFilter groupId;

  private LongFilter activityId;

  private LongFilter residenceStatusId;

  private LongFilter clientCategoryId;

  private LongFilter reportingProfileId;

  private LongFilter nationalityId;

  private BooleanFilter isFund;

  public ClientCriteria(ClientCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.description = other.description == null ? null : other.description.copy();

    this.mandateManager = other.mandateManager == null ? null : other.mandateManager.copy();
    this.mandateManagerFax =
        other.mandateManagerFax == null ? null : other.mandateManagerFax.copy();
    this.mandateManagerTel =
        other.mandateManagerTel == null ? null : other.mandateManagerTel.copy();
    this.mandateManagerMail =
        other.mandateManagerMail == null ? null : other.mandateManagerMail.copy();
    this.ebanking = other.ebanking == null ? null : other.ebanking.copy();
    this.externalReference =
        other.externalReference == null ? null : other.externalReference.copy();
    this.bankDomiciliation =
        other.bankDomiciliation == null ? null : other.bankDomiciliation.copy();
    this.subjectTax = other.subjectTax == null ? null : other.subjectTax.copy();
    this.clientType = other.clientType == null ? null : other.clientType.copy();
    this.customerAnalyticsSegment =
        other.customerAnalyticsSegment == null ? null : other.customerAnalyticsSegment.copy();
    this.fiscalStatus = other.fiscalStatus == null ? null : other.fiscalStatus.copy();
    this.legalCustomerCategoryId =
        other.legalCustomerCategoryId == null ? null : other.legalCustomerCategoryId.copy();
    this.serviceLevelId = other.serviceLevelId == null ? null : other.serviceLevelId.copy();
    this.tribunalCodeId = other.tribunalCodeId == null ? null : other.tribunalCodeId.copy();
    this.reportingLanguageId =
        other.reportingLanguageId == null ? null : other.reportingLanguageId.copy();
    this.subActivityId = other.subActivityId == null ? null : other.subActivityId.copy();
    this.taxCategoryId = other.taxCategoryId == null ? null : other.taxCategoryId.copy();
    this.residenceCountryId =
        other.residenceCountryId == null ? null : other.residenceCountryId.copy();
    this.subManagementActivityId =
        other.subManagementActivityId == null ? null : other.subManagementActivityId.copy();
    this.groupId = other.groupId == null ? null : other.groupId.copy();
    this.activityId = other.activityId == null ? null : other.activityId.copy();
    this.residenceStatusId =
        other.residenceStatusId == null ? null : other.residenceStatusId.copy();
    this.clientCategoryId = other.clientCategoryId == null ? null : other.clientCategoryId.copy();
    this.reportingProfileId =
        other.reportingProfileId == null ? null : other.reportingProfileId.copy();
    this.nationalityId = other.nationalityId == null ? null : other.nationalityId.copy();
    this.isFund = other.isFund == null ? null : other.isFund.copy();
  }

  @Override
  public ClientCriteria copy() {
    return new ClientCriteria(this);
  }
}
