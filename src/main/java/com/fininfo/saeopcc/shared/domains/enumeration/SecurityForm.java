package com.fininfo.saeopcc.shared.domains.enumeration;

public enum SecurityForm {
  BEARER("Au porteur"),
  REGISTERED("Nominatif");
  private final String label;

  SecurityForm(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
