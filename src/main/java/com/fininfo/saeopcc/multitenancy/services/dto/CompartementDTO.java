package com.fininfo.saeopcc.multitenancy.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.ShareClass;
import com.fininfo.saeopcc.shared.domains.enumeration.ShareForm;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class CompartementDTO {
  private Long id;
  private String fonds;
  private String reference;
  private String extensionDecision;
  private String investmentPeriod;
  private String dinvestmentPeriod;
  private BigDecimal socialExercise;
  private String investmentOrientation;
  private String geographicIntervention;
  private String internationalStrategy;
  private BigDecimal investmentSize;
  private String investmentPolicy;
  private String debtPolicy;
  private String businessPractices;
  private String environmentalProtection;
  private String reinvestmentPolicy;
  private ShareForm shareForm;
  private String period;
  private LocalDate approvalDate;
  private LocalDate endDate;
  private BigDecimal targetSize;
  private Double sectorLimit;
  private ShareClass shareClass;
  private Long deviseId;
  private String deviseCodeAlpha;
  private Long clientId;
  private String clientDescription;
  private Long fundId;
  private String fundIsin;
  private String fundCode;
  private String fundSdgDescription;
}
