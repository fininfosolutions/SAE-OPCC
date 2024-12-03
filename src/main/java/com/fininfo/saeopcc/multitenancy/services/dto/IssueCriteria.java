package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.IntegerFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IssueCriteria implements Serializable, Criteria {

  private LongFilter id;
  private IntegerFilter currentStep;
  private StringFilter issueIssueAccountIssueCompartementFundCode;
  private StringFilter issueIssueAccountIssueCompartementFundIsin;
  private StringFilter issueIssueAccountIssueCompartementFundDescription;

  public IssueCriteria(IssueCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.currentStep = other.currentStep == null ? null : other.currentStep.copy();
    this.issueIssueAccountIssueCompartementFundIsin =
        other.issueIssueAccountIssueCompartementFundIsin == null
            ? null
            : other.issueIssueAccountIssueCompartementFundIsin.copy();

    this.issueIssueAccountIssueCompartementFundDescription =
        other.issueIssueAccountIssueCompartementFundDescription == null
            ? null
            : other.issueIssueAccountIssueCompartementFundDescription.copy();
    this.issueIssueAccountIssueCompartementFundCode =
        other.issueIssueAccountIssueCompartementFundCode == null
            ? null
            : other.issueIssueAccountIssueCompartementFundCode.copy();
  }

  public IssueCriteria(
      LongFilter id,
      IntegerFilter currentStep,
      StringFilter issueIssueAccountIssueCompartementFundCode,
      StringFilter issueIssueAccountIssueCompartementFundIsin,
      StringFilter issueIssueAccountIssueCompartementFundDescription) {
    this.id = id;
    this.currentStep = currentStep;
    this.issueIssueAccountIssueCompartementFundCode = issueIssueAccountIssueCompartementFundCode;
    this.issueIssueAccountIssueCompartementFundIsin = issueIssueAccountIssueCompartementFundIsin;
    this.issueIssueAccountIssueCompartementFundDescription =
        issueIssueAccountIssueCompartementFundDescription;
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
