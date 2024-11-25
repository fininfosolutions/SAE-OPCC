package com.fininfo.saeopcc.shared.domains.enumeration;

public enum VotingRight {
  WITH_VOTING_RIGHTS("Avec Droit de Vote"),
  NO_VOTING_RIGHTS("Sans Droit de Vote"),
  RESTRICTED_VOTING_RIGHTS("Droit de vote restreint"),
  PREFERRED_VOTING_RIGHTS("Droit de vote Privilégié");
  private final String label;

  VotingRight(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
