package com.fininfo.saeopcc.multitenancy.domains.enumeration;

public enum ReportingLiberationStatus {
  SUCCEEDED("Réussi"),
  PENDING("En cours"),
  UNLIBERATED("Non libéré"),
  NOT_SUBSCRIBED("Non souscrit");

  private final String label;

  ReportingLiberationStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
