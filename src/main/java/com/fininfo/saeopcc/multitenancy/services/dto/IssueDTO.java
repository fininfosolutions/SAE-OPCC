package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.IssueStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
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
  private Long issueAccountId;
  private String issueAccountReference;
  private String issueAccountDescription;
  private String issueAccountIssueCompartementId;
  private String issueAccountIssueCompartementFundDescription;
  private String issueAccountIssueCompartementFundCode;
  private String issueAccountIssueCompartementFundIsin;
  private String description;
  private LocalDate openingDate;
  private LocalDate closingDate;
  private BigDecimal quantity;
  private BigDecimal amount;
  private BigDecimal price;
  private Set<SubscriptionDTO> subscriptions = new HashSet<>();
}
