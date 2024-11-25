package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LocalDateFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.EntityType;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.BusinessEntity} entity. This
 * class is used in {@link com.fininfo.saeopcc.shared.controllers.BusinessEntityResource} to receive
 * all the possible filtering options from the Http GET request parameters. For example the
 * following could be a valid request: {@code
 * /business-entities?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class BusinessEntityCriteria implements Serializable, Criteria {
  /** Class for filtering EntityType */
  public static class EntityTypeFilter extends Filter<EntityType> {

    public EntityTypeFilter() {}

    public EntityTypeFilter(EntityTypeFilter filter) {
      super(filter);
    }

    @Override
    public EntityTypeFilter copy() {
      return new EntityTypeFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter identifier;

  private EntityTypeFilter entityType;

  private StringFilter entityDescription;

  private LocalDateFilter incroporationDate;

  private BooleanFilter mainEntity;

  private LongFilter nationalityId;

  private LongFilter residenceId;

  private LongFilter roleId;

  public BusinessEntityCriteria() {}

  public BusinessEntityCriteria(BusinessEntityCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.identifier = other.identifier == null ? null : other.identifier.copy();
    this.entityType = other.entityType == null ? null : other.entityType.copy();
    this.entityDescription =
        other.entityDescription == null ? null : other.entityDescription.copy();
    this.incroporationDate =
        other.incroporationDate == null ? null : other.incroporationDate.copy();
    this.mainEntity = other.mainEntity == null ? null : other.mainEntity.copy();
    this.nationalityId = other.nationalityId == null ? null : other.nationalityId.copy();
    this.residenceId = other.residenceId == null ? null : other.residenceId.copy();
    this.roleId = other.roleId == null ? null : other.roleId.copy();
  }

  @Override
  public BusinessEntityCriteria copy() {
    return new BusinessEntityCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getIdentifier() {
    return identifier;
  }

  public void setIdentifier(StringFilter identifier) {
    this.identifier = identifier;
  }

  public EntityTypeFilter getEntityType() {
    return entityType;
  }

  public void setEntityType(EntityTypeFilter entityType) {
    this.entityType = entityType;
  }

  public StringFilter getEntityDescription() {
    return entityDescription;
  }

  public void setEntityDescription(StringFilter entityDescription) {
    this.entityDescription = entityDescription;
  }

  public LocalDateFilter getIncroporationDate() {
    return incroporationDate;
  }

  public void setIncroporationDate(LocalDateFilter incroporationDate) {
    this.incroporationDate = incroporationDate;
  }

  public BooleanFilter getMainEntity() {
    return mainEntity;
  }

  public void setMainEntity(BooleanFilter mainEntity) {
    this.mainEntity = mainEntity;
  }

  public LongFilter getNationalityId() {
    return nationalityId;
  }

  public void setNationalityId(LongFilter nationalityId) {
    this.nationalityId = nationalityId;
  }

  public LongFilter getResidenceId() {
    return residenceId;
  }

  public void setResidenceId(LongFilter residenceId) {
    this.residenceId = residenceId;
  }

  public LongFilter getRoleId() {
    return roleId;
  }

  public void setRoleId(LongFilter roleId) {
    this.roleId = roleId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final BusinessEntityCriteria that = (BusinessEntityCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(identifier, that.identifier)
        && Objects.equals(entityType, that.entityType)
        && Objects.equals(entityDescription, that.entityDescription)
        && Objects.equals(incroporationDate, that.incroporationDate)
        && Objects.equals(mainEntity, that.mainEntity)
        && Objects.equals(nationalityId, that.nationalityId)
        && Objects.equals(residenceId, that.residenceId)
        && Objects.equals(roleId, that.roleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        identifier,
        entityType,
        entityDescription,
        incroporationDate,
        mainEntity,
        nationalityId,
        residenceId,
        roleId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "BusinessEntityCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (identifier != null ? "identifier=" + identifier + ", " : "")
        + (entityType != null ? "entityType=" + entityType + ", " : "")
        + (entityDescription != null ? "entityDescription=" + entityDescription + ", " : "")
        + (incroporationDate != null ? "incroporationDate=" + incroporationDate + ", " : "")
        + (mainEntity != null ? "mainEntity=" + mainEntity + ", " : "")
        + (nationalityId != null ? "nationalityId=" + nationalityId + ", " : "")
        + (residenceId != null ? "residenceId=" + residenceId + ", " : "")
        + (roleId != null ? "roleId=" + roleId + ", " : "")
        + "}";
  }
}
