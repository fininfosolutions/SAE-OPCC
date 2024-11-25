package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountClassification;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountNature;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  public static class AccountClassificationFilter extends Filter<AccountClassification> {

    public AccountClassificationFilter() {}

    public AccountClassificationFilter(AccountClassificationFilter filter) {
      super(filter);
    }

    @Override
    public AccountClassificationFilter copy() {
      return new AccountClassificationFilter(this);
    }
  }

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

  public static class AccountNatureFilter extends Filter<AccountNature> {

    public AccountNatureFilter() {}

    public AccountNatureFilter(AccountNatureFilter filter) {
      super(filter);
    }

    @Override
    public AccountNatureFilter copy() {
      return new AccountNatureFilter(this);
    }
  }

  private LongFilter id;
  private StringFilter description;
  private StringFilter accountNumber;
  private AccountClassificationFilter accountClassification;
  private AccountTypeFilter accountType;
  private StringFilter root;
  private StringFilter code1;
  private StringFilter code2;
  private BooleanFilter isActive;
  private AccountNatureFilter accountNature;

  public AccountCriteria(AccountCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.accountNature = other.accountNature == null ? null : other.accountNature.copy();

    this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
    this.accountClassification =
        other.accountClassification == null ? null : other.accountClassification.copy();
    this.accountType = other.accountType == null ? null : other.accountType.copy();
    this.root = other.root == null ? null : other.root.copy();
    this.code1 = other.code1 == null ? null : other.code1.copy();
    this.code2 = other.code2 == null ? null : other.code2.copy();
    this.isActive = other.isActive == null ? null : other.isActive.copy();
  }

  public AccountCriteria(
      LongFilter id,
      StringFilter description,
      StringFilter accountNumber,
      AccountClassificationFilter accountClassification,
      AccountTypeFilter accountType,
      StringFilter root,
      StringFilter code1,
      StringFilter code2,
      AccountNatureFilter accountNature,
      BooleanFilter isActive) {
    this.id = id;
    this.description = description;
    this.accountNumber = accountNumber;
    this.accountClassification = accountClassification;
    this.accountNature = accountNature;
    this.accountType = accountType;
    this.root = root;
    this.code1 = code1;
    this.code2 = code2;
    this.isActive = isActive;
  }

  @Override
  public AccountCriteria copy() {
    return new AccountCriteria(this);
  }
}
