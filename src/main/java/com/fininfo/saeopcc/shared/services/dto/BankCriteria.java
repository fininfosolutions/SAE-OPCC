package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private LocalDateFilter affiliationDate;

  public BankCriteria(BankCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.affiliationDate = other.affiliationDate == null ? null : other.affiliationDate.copy();
  }

  @Override
  public BankCriteria copy() {
    return new BankCriteria(this);
  }
}
