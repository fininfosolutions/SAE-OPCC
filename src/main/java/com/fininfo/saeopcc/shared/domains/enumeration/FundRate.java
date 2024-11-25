package com.fininfo.saeopcc.shared.domains.enumeration;

public enum FundRate {
  CLOSEDEND("Closed-end"),
  OPENINGEND("Open-end"),
  OTHER("Autre");

  private final String label;

  FundRate(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
