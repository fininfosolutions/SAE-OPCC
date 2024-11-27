package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.shared.domains.enumeration.IssueStatus;
import java.util.Set;
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
  Set<Subscription> subscriptions;
}
