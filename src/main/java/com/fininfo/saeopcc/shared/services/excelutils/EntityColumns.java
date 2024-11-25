package com.fininfo.saeopcc.shared.services.excelutils;

import java.io.Serializable;

public class EntityColumns implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private String entityColumnName;

  private String frontColumnName;

  private int columnIndex;

  public EntityColumns() {
    super();
  }

  public String getEntityColumnName() {
    return entityColumnName;
  }

  public void setEntityColumnName(String entityColumnName) {
    this.entityColumnName = entityColumnName;
  }

  public String getFrontColumnName() {
    return frontColumnName;
  }

  public void setFrontColumnName(String frontColumnName) {
    this.frontColumnName = frontColumnName;
  }

  public int getColumnIndex() {
    return columnIndex;
  }

  public void setColumnIndex(int columnIndex) {
    this.columnIndex = columnIndex;
  }
}
