package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustodianCriteria implements Serializable, Criteria {

  private LongFilter id;
  private StringFilter description;

  public CustodianCriteria(CustodianCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.description = other.description == null ? null : other.description.copy();
  }

  public CustodianCriteria(LongFilter id, StringFilter description) {
    this.id = id;
    this.description = description;
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
