package com.fininfo.saeopcc.shared.domains.enumeration;

public enum PaiementStatusEquity {
  NOT_PAID("Non libéré"),
  PARTIALLY_PAID("Partiellement libéré"),
  FULLY_PAID("Totalement libéré");

  private final String label;

  PaiementStatusEquity(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
