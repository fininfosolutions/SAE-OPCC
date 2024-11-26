package com.fininfo.saeopcc.shared.services.dto;

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
  private Long subscriptionId;
  private String subscriptionReference;
  private IssueStatus issueStatus;
  

}
