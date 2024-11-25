package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShareholderCriteria implements Serializable, Criteria {

  private LongFilter id;
  private StringFilter description;
  private StringFilter reference;

  public ShareholderCriteria(ShareholderCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.reference = other.reference == null ? null : other.reference.copy();
  }

  public ShareholderCriteria(LongFilter id, StringFilter description, StringFilter reference) {
    this.id = id;
    this.description = description;
    this.reference = reference;
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
