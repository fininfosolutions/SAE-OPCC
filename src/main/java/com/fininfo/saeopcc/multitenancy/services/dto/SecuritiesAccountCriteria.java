package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.multitenancy.services.dto.SecuritiesAccountCriteria.AccountTypeFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecuritiesAccountCriteria implements Serializable, Criteria {

  public static class AccountTypeFilter extends Filter<AccountType> {

    public AccountTypeFilter() {}

    public AccountTypeFilter(AccountTypeFilter filter) {
      super(filter);
    }

    @Override
    public AccountTypeFilter copy() {
      return new AccountTypeFilter(this);
    }
  }

  private LongFilter id;
  private StringFilter description;
  private StringFilter accountNumber;
  private LocalDateFilter openingDate;
  private StringFilter assetIsin;
  private StringFilter shareholderDescription;
  private StringFilter intermediaryDescription;
  private AccountTypeFilter accountType;

  public SecuritiesAccountCriteria(SecuritiesAccountCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
    this.openingDate = other.openingDate == null ? null : other.openingDate.copy();
    this.assetIsin = other.assetIsin == null ? null : other.assetIsin.copy();
    this.shareholderDescription =
        other.shareholderDescription == null ? null : other.shareholderDescription.copy();
    this.intermediaryDescription =
        other.intermediaryDescription == null ? null : other.intermediaryDescription.copy();
    this.accountType = other.accountType == null ? null : other.accountType.copy();
  }

  public SecuritiesAccountCriteria(
      LongFilter id,
      StringFilter description,
      StringFilter accountNumber,
      LocalDateFilter openingDate,
      StringFilter assetIsin,
      StringFilter shareholderDescription,
      StringFilter intermediaryDescription,
      AccountTypeFilter accountType) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.description = description;
    this.openingDate = openingDate;
    this.assetIsin = assetIsin;
    this.shareholderDescription = shareholderDescription;
    this.intermediaryDescription = intermediaryDescription;
    this.accountType = accountType;
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
