package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.BigDecimalFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.Direction;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.InvestmentType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovementCriteria implements Serializable, Criteria {

  public static class DirectionFilter extends Filter<Direction> {
    public DirectionFilter() {}

    public DirectionFilter(DirectionFilter filter) {
      super(filter);
    }

    @Override
    public DirectionFilter copy() {
      return new DirectionFilter(this);
    }
  }

  public static class MovementTypeFilter extends Filter<MovementType> {
    public MovementTypeFilter() {}

    public MovementTypeFilter(MovementTypeFilter filter) {
      super(filter);
    }

    @Override
    public MovementTypeFilter copy() {
      return new MovementTypeFilter(this);
    }
  }

  public static class InvestmentTypeFilter extends Filter<InvestmentType> {
    public InvestmentTypeFilter() {}

    public InvestmentTypeFilter(InvestmentTypeFilter filter) {
      super(filter);
    }

    @Override
    public InvestmentTypeFilter copy() {
      return new InvestmentTypeFilter(this);
    }
  }

  public static class MovementStatusFilter extends Filter<MovementStatus> {
    public MovementStatusFilter() {}

    public MovementStatusFilter(MovementStatusFilter filter) {
      super(filter);
    }

    @Override
    public MovementStatusFilter copy() {
      return new MovementStatusFilter(this);
    }
  }

  public static class TransactionTypeFilter extends Filter<TransactionType> {
    public TransactionTypeFilter() {}

    public TransactionTypeFilter(TransactionTypeFilter filter) {
      super(filter);
    }

    @Override
    public TransactionTypeFilter copy() {
      return new TransactionTypeFilter(this);
    }
  }

  // Matching criteria fields for MovementDTO
  private LongFilter id;
  private StringFilter reference;
  private StringFilter instructionReference;
  private LongFilter instructionId;
  private TransactionTypeFilter transactionType;
  private MovementStatusFilter status;
  private LocalDateFilter movementDate;
  private LocalDateFilter impactedDate;
  private BigDecimalFilter amount;
  private StringFilter assetAssetType;
  private LongFilter assetId;
  private StringFilter isin;
  private MovementTypeFilter type;
  private StringFilter nostroSecurityAccount;
  private StringFilter custodian;
  private DirectionFilter sens;
  private InvestmentTypeFilter investmentType;
  private StringFilter assetDescription;
  private StringFilter assetCode;
  private BigDecimalFilter quantity;
  private LongFilter accountId;
  private StringFilter accountNumber;
  private StringFilter shareholderDescription;

  // Copy constructor to clone another instance of MovementCriteria
  public MovementCriteria(MovementCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.reference = other.reference == null ? null : other.reference.copy();
    this.instructionReference =
        other.instructionReference == null ? null : other.instructionReference.copy();
    this.instructionId = other.instructionId == null ? null : other.instructionId.copy();
    this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
    this.status = other.status == null ? null : other.status.copy();
    this.movementDate = other.movementDate == null ? null : other.movementDate.copy();
    this.impactedDate = other.impactedDate == null ? null : other.impactedDate.copy();
    this.amount = other.amount == null ? null : other.amount.copy();
    this.assetAssetType = other.assetAssetType == null ? null : other.assetAssetType.copy();
    this.assetId = other.assetId == null ? null : other.assetId.copy();
    this.isin = other.isin == null ? null : other.isin.copy();
    this.type = other.type == null ? null : other.type.copy();
    this.nostroSecurityAccount =
        other.nostroSecurityAccount == null ? null : other.nostroSecurityAccount.copy();
    this.custodian = other.custodian == null ? null : other.custodian.copy();
    this.sens = other.sens == null ? null : other.sens.copy();
    this.investmentType = other.investmentType == null ? null : other.investmentType.copy();
    this.assetDescription = other.assetDescription == null ? null : other.assetDescription.copy();
    this.assetCode = other.assetCode == null ? null : other.assetCode.copy();
    this.quantity = other.quantity == null ? null : other.quantity.copy();
    this.accountId = other.accountId == null ? null : other.accountId.copy();
    this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
    this.shareholderDescription =
        other.shareholderDescription == null ? null : other.shareholderDescription.copy();
  }

  // Constructor for initializing all fields
  public MovementCriteria(
      LongFilter id,
      StringFilter reference,
      StringFilter instructionReference,
      LongFilter instructionId,
      TransactionTypeFilter transactionType,
      MovementStatusFilter status,
      LocalDateFilter movementDate,
      LocalDateFilter impactedDate,
      BigDecimalFilter amount,
      StringFilter assetAssetType,
      LongFilter assetId,
      StringFilter isin,
      MovementTypeFilter type,
      StringFilter nostroSecurityAccount,
      StringFilter custodian,
      DirectionFilter sens,
      InvestmentTypeFilter investmentType,
      StringFilter assetDescription,
      StringFilter assetCode,
      BigDecimalFilter quantity,
      LongFilter accountId,
      StringFilter accountNumber,
      StringFilter shareholderDescription) {
    this.id = id;
    this.reference = reference;
    this.instructionReference = instructionReference;
    this.instructionId = instructionId;
    this.transactionType = transactionType;
    this.status = status;
    this.movementDate = movementDate;
    this.impactedDate = impactedDate;
    this.amount = amount;
    this.assetAssetType = assetAssetType;
    this.assetId = assetId;
    this.isin = isin;
    this.type = type;
    this.nostroSecurityAccount = nostroSecurityAccount;
    this.custodian = custodian;
    this.sens = sens;
    this.investmentType = investmentType;
    this.assetDescription = assetDescription;
    this.assetCode = assetCode;
    this.quantity = quantity;
    this.accountId = accountId;
    this.accountNumber = accountNumber;
    this.shareholderDescription = shareholderDescription;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public Criteria copy() {
    throw new UnsupportedOperationException("Unimplemented method 'copy'");
  }
}
