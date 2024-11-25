package com.fininfo.saeopcc.shared.domains.enumeration;

public enum FundClassification {
  OPCVM_ACTIONS("Action"),
  CONTRACTUELS_OPCVM("Contractuel"),
  DIVERSIFIED_OPCVM("Diversifié"),
  MONEY_MARKET_OPCVM("Monétaire"),
  OBLIGATION_MLT("Oblig MLT"),
  OBLIGATION_CT("Oblig CT");

  private final String label;

  FundClassification(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
