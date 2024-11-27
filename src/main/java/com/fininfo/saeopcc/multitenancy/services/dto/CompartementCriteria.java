package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompartementCriteria implements Serializable, Criteria {

  private StringFilter fundCode;

  public CompartementCriteria(CompartementCriteria other) {
    this.fundCode = other.fundCode == null ? null : other.fundCode.copy();
  }

  public CompartementCriteria(StringFilter fundCode) {
    this.fundCode = fundCode;
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
