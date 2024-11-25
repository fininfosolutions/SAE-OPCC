package com.fininfo.saeopcc.shared.domains.enumeration;

// import com.fasterxml.jackson.annotation.JsonCreator;

/** The AssetType enumeration. */
public enum AssetType {
  EQUITY("Action"),
  BOND("Obligation"),
  FUND("Fonds");
  private final String label;

  AssetType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
