package com.fininfo.saeopcc.shared.domains.enumeration;

public enum FundGuarantee {
  ALLOCATIONFUND("Fond de répartition"),
  ACCUMULATIONFUND("Fond d'accumulation"),
  MIXEDFUND("Fond Mixte");

  private final String label;

  FundGuarantee(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
