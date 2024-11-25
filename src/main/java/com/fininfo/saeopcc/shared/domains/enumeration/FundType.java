package com.fininfo.saeopcc.shared.domains.enumeration;

public enum FundType {
  SICAV("SICAV"),
  MUTUALFUND("FCP"),
  DECIMALMUTUALFUND("FCP à décimales");

  private final String label;

  FundType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
