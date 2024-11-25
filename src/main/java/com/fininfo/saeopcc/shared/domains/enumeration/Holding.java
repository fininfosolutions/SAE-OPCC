package com.fininfo.saeopcc.shared.domains.enumeration;

public enum Holding {
  WITH_RESTRICTION("Avec Restriction"),
  WITHOUT_RESTRICTION("Sans restriction");
  private final String label;

  Holding(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
