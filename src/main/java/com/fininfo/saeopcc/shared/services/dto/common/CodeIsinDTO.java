package com.fininfo.saeopcc.shared.services.dto.common;

import java.io.Serializable;
import java.util.List;

public class CodeIsinDTO implements Serializable {

  private String assetClass;

  private List<String> isins;

  public String getAssetClass() {
    return assetClass;
  }

  public void setAssetClass(String assetClass) {
    this.assetClass = assetClass;
  }

  public List<String> getIsins() {
    return isins;
  }

  public void setIsins(List<String> isins) {
    this.isins = isins;
  }

  @Override
  public String toString() {
    return "CodeIsinDTO [assetClass=" + assetClass + ", isins=" + isins + "]";
  }
}
