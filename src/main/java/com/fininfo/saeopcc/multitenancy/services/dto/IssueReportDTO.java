package com.fininfo.saeopcc.multitenancy.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class IssueReportDTO {
  private Long id;
  private Long issueaccountId;
}
