package com.fininfo.saeopcc.shared.domains.enumeration;

public enum FundCategory {
  GRAND_PUBLIC("Grand Public"),
  DEDIE("Dédié");
  private final String label;

  FundCategory(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
