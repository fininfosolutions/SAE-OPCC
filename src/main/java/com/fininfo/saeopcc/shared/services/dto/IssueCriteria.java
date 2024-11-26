package com.fininfo.saeopcc.shared.services.dto;

import java.io.Serializable;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.IntegerFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IssueCriteria implements Serializable, Criteria {

  private LongFilter id;
  private IntegerFilter currentStep;
  private LongFilter assetId;
  private StringFilter assetIsin;
  private StringFilter assetCode;
  private StringFilter assetDescription;

  public IssueCriteria(IssueCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.currentStep = other.currentStep == null ? null : other.currentStep.copy();
   
  }

  public IssueCriteria(
      LongFilter id,
      IntegerFilter currentStep
      ) {
    this.id = id;
    this.currentStep = currentStep;
   
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
