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
public class ProviderCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter name;

  public ProviderCriteria(ProviderCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.name = other.name == null ? null : other.name.copy();
  }

  @Override
  public ProviderCriteria copy() {
    return new ProviderCriteria(this);
  }
}
