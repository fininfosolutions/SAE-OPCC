package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.ClientType;
import com.fininfo.saeopcc.shared.domains.enumeration.FiscalStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.JuridicalNature;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import com.fininfo.saeopcc.shared.services.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class ClientDTO extends RoleDTO {
  private Long id;
  private String mandateManager;
  private Integer mandateManagerFax;
  private Integer mandateManagerTel;
  private String mandateManagerMail;
  private Boolean ebanking;
  private Boolean cashDepositor;
  private String bankDomiciliation;
  private Boolean subjectTax;
  private ClientType clientType;
  private Integer customerAnalyticsSegment;
  private FiscalStatus fiscalStatus;
  private Long residenceCountryId;
  private String residenceCountryIso3;
  private Long residenceStatusId;
  private Boolean isfund;
  private Boolean isHouse;
  private JuridicalNature juridicalNature;
  private String residenceStatusCode;
  private String residenceStatusDescription;
  private Long clientCategoryId;
  private String clientCategoryDescription;
  private String clientCategoryType;
  private String clientCategoryNature;
  private Long nationalityId;
  private String nationalityIso2;
  private String securitySectorDescription;
  private Boolean isResident;
  private SecurityForm securityForm;
}
