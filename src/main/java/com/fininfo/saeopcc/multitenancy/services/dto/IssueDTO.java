package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class IssueDTO {
  private Long id;
  private Integer currentStep;
  private IssueStatus issueStatus;
  private Long issueAccountId;
  private String issueAccountReference;
  private String issueAccountDescription;
  private String issueAccountIssueCompartementId;
  private String issueAccountIssueCompartementFundDescription;
  private String issueAccountIssueCompartementFundCode;
  private String issueAccountIssueCompartementFundIsin;

  // Set<Subscription> subscriptions;
}
