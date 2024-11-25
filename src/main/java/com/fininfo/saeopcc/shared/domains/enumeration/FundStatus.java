package com.fininfo.saeopcc.shared.domains.enumeration;

public enum FundStatus {
  REALESTATE("Real estate"),
  DEBTINSTRUMENT("Instrument de Dette"),
  EQUITY("Actions"),
  CONVERTIBLESECURITY("Titres convertibles"),
  MIXEDFUND("Mixte"),
  COMMODITYBASEDFUNDS("Produits de base"),
  DERIVATIVESFUND("Dérivés"),
  BENCHMARKINSTRUMENTSEXCLUDINGCOMMODITIES(
      "Instrument à référence  à\\n"
          + //
          "l'exclusion des produits de base"),
  CREDIT("Crédits"),
  OTHER("autre");

  private final String label;

  FundStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
