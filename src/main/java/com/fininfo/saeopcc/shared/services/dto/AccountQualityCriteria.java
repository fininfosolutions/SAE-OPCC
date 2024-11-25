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
public class AccountQualityCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter code;

  private StringFilter description;

  public AccountQualityCriteria(AccountQualityCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.code = other.code == null ? null : other.code.copy();
    this.description = other.description == null ? null : other.description.copy();
  }

  @Override
  public AccountQualityCriteria copy() {
    return new AccountQualityCriteria(this);
  }
}
