package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsTypeCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter code;

  private StringFilter description;

  private LongFilter securitiesAccountId;

  private LongFilter cashAccountId;

  public AccountsTypeCriteria(AccountsTypeCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.securitiesAccountId =
        other.securitiesAccountId == null ? null : other.securitiesAccountId.copy();
    this.cashAccountId = other.cashAccountId == null ? null : other.cashAccountId.copy();
  }

  @Override
  public AccountsTypeCriteria copy() {
    return new AccountsTypeCriteria(this);
  }
}
