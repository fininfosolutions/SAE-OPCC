package com.fininfo.saeopcc.shared.domains.enumeration;

public enum RateType {
  VARIABLE_RATE("Variable"),
  FIXED_RATE("Taux fixe");

  private final String label;

  RateType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
