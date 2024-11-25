package com.fininfo.saeopcc.shared.domains.enumeration;

public enum FundForm {
  EQUITY("Action"),
  DEDICATEDEQUITY("Action Dédié"),
  SHARE("Part"),
  DEDICATEDSHARE("Part Dédié");
  private final String label;

  FundForm(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
