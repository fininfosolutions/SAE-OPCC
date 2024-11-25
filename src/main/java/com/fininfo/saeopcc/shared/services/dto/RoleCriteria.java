package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.BooleanFilter;
import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.Role} entity. This class is used
 * in {@link com.fininfo.saeopcc.shared.controllers.RoleResource} to receive all the possible filtering
 * options from the Http GET request parameters. For example the following could be a valid request:
 * {@code /roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to
 * use fix type specific filters.
 */
public class RoleCriteria implements Serializable, Criteria {
  /** Class for filtering ActingAs */
  public static class ActingAsFilter
      extends Filter<com.fininfo.saeopcc.shared.domains.enumeration.ActingAs> {

    public ActingAsFilter() {}

    public ActingAsFilter(ActingAsFilter filter) {
      super(filter);
    }

    @Override
    public ActingAsFilter copy() {
      return new ActingAsFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter description;

  private ActingAsFilter ActingAs;

  private StringFilter definition;

  private StringFilter reference;

  private BooleanFilter external;

  private LongFilter businessEntityId;

  private LongFilter economicAgentCategoryId;

  private LongFilter socioProfessionalCategoryId;

  private LongFilter addressId;

  public RoleCriteria() {}

  public RoleCriteria(RoleCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.description = other.description == null ? null : other.description.copy();
    this.ActingAs = other.ActingAs == null ? null : other.ActingAs.copy();
    this.definition = other.definition == null ? null : other.definition.copy();
    this.reference = other.reference == null ? null : other.reference.copy();
    this.external = other.external == null ? null : other.external.copy();
    this.businessEntityId = other.businessEntityId == null ? null : other.businessEntityId.copy();
    this.economicAgentCategoryId =
        other.economicAgentCategoryId == null ? null : other.economicAgentCategoryId.copy();
    this.socioProfessionalCategoryId =
        other.socioProfessionalCategoryId == null ? null : other.socioProfessionalCategoryId.copy();
    this.addressId = other.addressId == null ? null : other.addressId.copy();
  }

  @Override
  public RoleCriteria copy() {
    return new RoleCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getDescription() {
    return description;
  }

  public void setDescription(StringFilter description) {
    this.description = description;
  }

  public ActingAsFilter getActingAs() {
    return ActingAs;
  }

  public void setActingAs(ActingAsFilter ActingAs) {
    this.ActingAs = ActingAs;
  }

  public StringFilter getDefinition() {
    return definition;
  }

  public void setDefinition(StringFilter definition) {
    this.definition = definition;
  }

  public StringFilter getReference() {
    return reference;
  }

  public void setReference(StringFilter reference) {
    this.reference = reference;
  }

  public BooleanFilter getExternal() {
    return external;
  }

  public void setExternal(BooleanFilter external) {
    this.external = external;
  }

  public LongFilter getBusinessEntityId() {
    return businessEntityId;
  }

  public void setBusinessEntityId(LongFilter businessEntityId) {
    this.businessEntityId = businessEntityId;
  }

  public LongFilter getEconomicAgentCategoryId() {
    return economicAgentCategoryId;
  }

  public void setEconomicAgentCategoryId(LongFilter economicAgentCategoryId) {
    this.economicAgentCategoryId = economicAgentCategoryId;
  }

  public LongFilter getSocioProfessionalCategoryId() {
    return socioProfessionalCategoryId;
  }

  public void setSocioProfessionalCategoryId(LongFilter socioProfessionalCategoryId) {
    this.socioProfessionalCategoryId = socioProfessionalCategoryId;
  }

  public LongFilter getAddressId() {
    return addressId;
  }

  public void setAddressId(LongFilter addressId) {
    this.addressId = addressId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RoleCriteria that = (RoleCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(description, that.description)
        && Objects.equals(ActingAs, that.ActingAs)
        && Objects.equals(definition, that.definition)
        && Objects.equals(reference, that.reference)
        && Objects.equals(external, that.external)
        && Objects.equals(businessEntityId, that.businessEntityId)
        && Objects.equals(economicAgentCategoryId, that.economicAgentCategoryId)
        && Objects.equals(socioProfessionalCategoryId, that.socioProfessionalCategoryId)
        && Objects.equals(addressId, that.addressId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        description,
        ActingAs,
        definition,
        reference,
        external,
        businessEntityId,
        economicAgentCategoryId,
        socioProfessionalCategoryId,
        addressId);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "RoleCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (description != null ? "description=" + description + ", " : "")
        + (ActingAs != null ? "ActingAs=" + ActingAs + ", " : "")
        + (definition != null ? "definition=" + definition + ", " : "")
        + (reference != null ? "reference=" + reference + ", " : "")
        + (external != null ? "external=" + external + ", " : "")
        + (businessEntityId != null ? "businessEntityId=" + businessEntityId + ", " : "")
        + (economicAgentCategoryId != null
            ? "economicAgentCategoryId=" + economicAgentCategoryId + ", "
            : "")
        + (socioProfessionalCategoryId != null
            ? "socioProfessionalCategoryId=" + socioProfessionalCategoryId + ", "
            : "")
        + (addressId != null ? "addressId=" + addressId + ", " : "")
        + "}";
  }
}
