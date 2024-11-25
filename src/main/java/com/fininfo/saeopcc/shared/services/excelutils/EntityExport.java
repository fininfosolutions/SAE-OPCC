package com.fininfo.saeopcc.shared.services.excelutils;

import java.io.Serializable;
import java.util.List;

public class EntityExport implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  public EntityExport() {
    super();
  }

  private transient List<Object> data;

  private List<EntityColumns> entityColumns;

  public List<EntityColumns> getEntityColumns() {
    return entityColumns;
  }

  public void setEntityColumns(List<EntityColumns> entityColumns) {
    this.entityColumns = entityColumns;
  }

  public List<Object> getData() {
    return data;
  }

  public void setData(List<Object> data) {
    this.data = data;
  }
}
