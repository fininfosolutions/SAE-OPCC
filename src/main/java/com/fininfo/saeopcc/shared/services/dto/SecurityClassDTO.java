package com.fininfo.saeopcc.shared.services.dto;

import java.io.Serializable;

public class SecurityClassDTO implements Serializable {
  private Long id;
  private String code;

  private String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SecurityClassDTO other = (SecurityClassDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (code == null) {
      if (other.code != null) return false;
    } else if (!code.equals(other.code)) return false;
    if (description == null) {
      if (other.description != null) return false;
    } else if (!description.equals(other.description)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "SecurityClassDTO [id=" + id + ", code=" + code + ", description=" + description + "]";
  }
}
