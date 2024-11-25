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
public class AccountResponsibleCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter name;

  private StringFilter description;

  private LongFilter individualId;

  public AccountResponsibleCriteria(AccountResponsibleCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.name = other.name == null ? null : other.name.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.individualId = other.individualId == null ? null : other.individualId.copy();
  }

  @Override
  public AccountResponsibleCriteria copy() {
    return new AccountResponsibleCriteria(this);
  }
}
