package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.Group} entity. This class is
 * used in {@link com.fininfo.saeopcc.shared.controllers.GroupResource} to receive all the possible
 * filtering options from the Http GET request parameters. For example the following could be a
 * valid request: {@code /groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As
 * Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we
 * need to use fix type specific filters.
 */
public class GroupCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter level;

  private StringFilter description;

  public GroupCriteria() {}

  public GroupCriteria(GroupCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.level = other.level == null ? null : other.level.copy();
    this.description = other.description == null ? null : other.description.copy();
  }

  @Override
  public GroupCriteria copy() {
    return new GroupCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getLevel() {
    return level;
  }

  public void setLevel(StringFilter level) {
    this.level = level;
  }

  public StringFilter getDescription() {
    return description;
  }

  public void setDescription(StringFilter description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final GroupCriteria that = (GroupCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(level, that.level)
        && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, level, description);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "GroupCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (level != null ? "level=" + level + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + "}";
  }
}
