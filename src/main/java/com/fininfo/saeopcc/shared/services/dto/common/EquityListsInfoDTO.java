package com.fininfo.saeopcc.shared.services.dto.common;

import com.fininfo.saeopcc.shared.services.dto.CSDDTO;
import com.fininfo.saeopcc.shared.services.dto.CentraliserDTO;
import com.fininfo.saeopcc.shared.services.dto.CustodianDTO;
import com.fininfo.saeopcc.shared.services.dto.FiscalNatureDTO;
import com.fininfo.saeopcc.shared.services.dto.IssuerDTO;
import com.fininfo.saeopcc.shared.services.dto.IssuerRiskCategoryDTO;
import com.fininfo.saeopcc.shared.services.dto.MarketDTO;
import com.fininfo.saeopcc.shared.services.dto.QuotationGroupDTO;
import com.fininfo.saeopcc.shared.services.dto.RegistrarDTO;
import com.fininfo.saeopcc.shared.services.dto.SecuritySectorDTO;
import com.fininfo.saeopcc.shared.services.dto.SecurityTypeDTO;
import com.fininfo.saeopcc.shared.services.dto.SettlementTypeDTO;
import java.util.List;

public class EquityListsInfoDTO {

  private List<IssuerRiskCategoryDTO> issuerRiskCategory;

  private List<QuotationGroupDTO> quotationGroup;
  private List<FiscalNatureDTO> fiscalNature;
  private List<SecurityTypeDTO> securityType;
  private List<SettlementTypeDTO> settlementType;
  private List<SecuritySectorDTO> securitySector;
  private List<CustodianDTO> custodian;
  private List<CentraliserDTO> centraliser;
  private List<RegistrarDTO> registrar;
  private List<IssuerDTO> issuer;
  private List<MarketDTO> market;
  private List<CSDDTO> csd;

  public List<IssuerRiskCategoryDTO> getIssuerRiskCategory() {
    return issuerRiskCategory;
  }

  public void setIssuerRiskCategory(List<IssuerRiskCategoryDTO> issuerRiskCategory) {
    this.issuerRiskCategory = issuerRiskCategory;
  }

  public List<QuotationGroupDTO> getQuotationGroup() {
    return quotationGroup;
  }

  public void setQuotationGroup(List<QuotationGroupDTO> quotationGroup) {
    this.quotationGroup = quotationGroup;
  }

  public List<FiscalNatureDTO> getFiscalNature() {
    return fiscalNature;
  }

  public void setFiscalNature(List<FiscalNatureDTO> fiscalNature) {
    this.fiscalNature = fiscalNature;
  }

  public List<SecurityTypeDTO> getSecurityType() {
    return securityType;
  }

  public void setSecurityType(List<SecurityTypeDTO> securityType) {
    this.securityType = securityType;
  }

  public List<SettlementTypeDTO> getSettlementType() {
    return settlementType;
  }

  public void setSettlementType(List<SettlementTypeDTO> settlementType) {
    this.settlementType = settlementType;
  }

  public List<SecuritySectorDTO> getSecuritySector() {
    return securitySector;
  }

  public void setSecuritySector(List<SecuritySectorDTO> securitySector) {
    this.securitySector = securitySector;
  }

  public List<CustodianDTO> getCustodian() {
    return custodian;
  }

  public void setCustodian(List<CustodianDTO> custodian) {
    this.custodian = custodian;
  }

  public List<CentraliserDTO> getCentraliser() {
    return centraliser;
  }

  public void setCentraliser(List<CentraliserDTO> centraliser) {
    this.centraliser = centraliser;
  }

  public List<RegistrarDTO> getRegistrar() {
    return registrar;
  }

  public void setRegistrar(List<RegistrarDTO> registrar) {
    this.registrar = registrar;
  }

  public List<IssuerDTO> getIssuer() {
    return issuer;
  }

  public void setIssuer(List<IssuerDTO> issuer) {
    this.issuer = issuer;
  }

  public List<MarketDTO> getMarket() {
    return market;
  }

  public void setMarket(List<MarketDTO> market) {
    this.market = market;
  }

  public List<CSDDTO> getCsd() {
    return csd;
  }

  public void setCsd(List<CSDDTO> csd) {
    this.csd = csd;
  }

  @Override
  public String toString() {
    return "EquityListsInfoDTO [centraliser="
        + centraliser
        + ", csd="
        + csd
        + ", custodian="
        + custodian
        + ", fiscalNature="
        + fiscalNature
        + ", issuer="
        + issuer
        + ", issuerRiskCategory="
        + issuerRiskCategory
        + ", market="
        + market
        + ", quotationGroup="
        + quotationGroup
        + ", registrar="
        + registrar
        + ", securitySector="
        + securitySector
        + ", securityType="
        + securityType
        + ", settlementType="
        + settlementType
        + "]";
  }
}
