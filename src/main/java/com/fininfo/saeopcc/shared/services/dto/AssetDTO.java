package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import com.fininfo.saeopcc.shared.domains.enumeration.BusinessRisk;
import com.fininfo.saeopcc.shared.domains.enumeration.DetentionForm;
import com.fininfo.saeopcc.shared.domains.enumeration.LegalStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.MarketType;
import com.fininfo.saeopcc.shared.domains.enumeration.QuantityType;
import com.fininfo.saeopcc.shared.domains.enumeration.QuotationPriceMode;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import com.fininfo.saeopcc.shared.domains.enumeration.SettlementPlace;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
public class AssetDTO implements Serializable {

  private Long id;

  private String isin;

  private String description;

  private String shortCode;

  private String issuerDescription;
  private String issuerReference;

  private String sdgDescription;
  private String sdgReference;

  private String reference;

  private String aditionalDescription;

  private Boolean active;
  private Boolean inscriMaroclear;

  private LocalDate deactivationDate;

  private Integer securitiesInCirculationNumber;

  private LocalDate premiumDate;

  private Boolean dematerialized;

  private Boolean taxExemption;

  private Boolean listed;

  private Boolean canceled;

  private SettlementPlace settlementPlace;

  private LocalDate cancellationDate;

  private LocalDate quotationFirstDate;

  private AssetType assetType;

  private String code;

  private BigDecimal nominalValue;

  private BigDecimal currentNominalValue;

  private BigDecimal issuePremium;

  private BigDecimal amountPremium;

  private BigDecimal amountOutstandingPremium;

  private Long quotationGroupId;

  private MarketType marketType;

  private BusinessRisk businessRisk;

  private Long fiscalNatureId;

  private SecurityForm securityForm;

  private QuantityType quantityType;

  private Long securityTypeId;

  private QuotationPriceMode quotationPriceMode;

  private Long settlementTypeId;

  private DetentionForm detentionForm;

  private long deviseId;

  private Long securitySectorId;

  private Long custodianId;

  private Long centraliserId;

  private Long issueAccountId;

  private Long securityClassId;

  private Long securityClassificationId;

  private Long registrarId;

  private LegalStatus legalStatus;

  private Long issuerId;

  private Long marketId;

  private Long csdId;
  private Long sdgId;

  private Long issueId;

  public AssetDTO() {
    super();
  }
}
