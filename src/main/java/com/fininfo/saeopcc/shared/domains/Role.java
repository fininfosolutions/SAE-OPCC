package com.fininfo.saeopcc.shared.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class Role extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id private Long id;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "acting_as")
  private ActingAs actingAs;

  @Column(name = "definition")
  private String definition;

  @Column(name = "reference")
  private String reference;

  @Column(name = "external_reference")
  private String externalReference;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "affiliedCode")
  private String affiliedCode;

  @ManyToOne
  @JsonIgnoreProperties(value = "roles", allowSetters = true)
  private EconomicAgentCategory economicAgentCategory;

  @ManyToOne
  @JsonIgnoreProperties(value = "roles", allowSetters = true)
  private SocioProfessionalCategory socioProfessionalCategory;

  @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<Address> addresses = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public Role description(String description) {
    this.description = description;
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ActingAs getActingAs() {
    return actingAs;
  }

  public Role ActingAs(ActingAs actingAs) {
    this.actingAs = actingAs;
    return this;
  }

  public void setActingAs(ActingAs actingAs) {
    this.actingAs = actingAs;
  }

  public String getDefinition() {
    return definition;
  }

  public Role definition(String definition) {
    this.definition = definition;
    return this;
  }

  public void setDefinition(String definition) {
    this.definition = definition;
  }

  public String getReference() {
    return reference;
  }

  public Role reference(String reference) {
    this.reference = reference;
    return this;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  // public Boolean isExternal() {
  // return external;
  // }

  // public Role external(Boolean external) {
  // this.external = external;
  // return this;
  // }

  // public void setExternal(Boolean external) {
  // this.external = external;
  // }

  public EconomicAgentCategory getEconomicAgentCategory() {
    return economicAgentCategory;
  }

  public Role economicAgentCategory(EconomicAgentCategory economicAgentCategory) {
    this.economicAgentCategory = economicAgentCategory;
    return this;
  }

  public void setEconomicAgentCategory(EconomicAgentCategory economicAgentCategory) {
    this.economicAgentCategory = economicAgentCategory;
  }

  public SocioProfessionalCategory getSocioProfessionalCategory() {
    return socioProfessionalCategory;
  }

  public Role socioProfessionalCategory(SocioProfessionalCategory socioProfessionalCategory) {
    this.socioProfessionalCategory = socioProfessionalCategory;
    return this;
  }

  public void setSocioProfessionalCategory(SocioProfessionalCategory socioProfessionalCategory) {
    this.socioProfessionalCategory = socioProfessionalCategory;
  }

  public Set<Address> getAddresses() {
    return addresses;
  }

  public Role addresses(Set<Address> addresses) {
    this.addresses = addresses;
    return this;
  }

  public Role addAddress(Address address) {
    this.addresses.add(address);
    address.setRole(this);
    return this;
  }

  public Role removeAddress(Address address) {
    this.addresses.remove(address);
    address.setRole(null);
    return this;
  }

  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Role)) {
      return false;
    }
    return id != null && id.equals(((Role) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  public Role(Long id, String reference, String description) {
    this.id = id;
    this.description = description;
    this.reference = reference;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "Role{"
        + "id="
        + getId()
        + ", description='"
        + getDescription()
        + "'"
        + ", ActingAs='"
        + getActingAs()
        + "'"
        + ", definition='"
        + getDefinition()
        + "'"
        + ", reference='"
        + getReference()
        + "'"
        // + ", external='"
        // + isExternal()
        // + "'"
        + "}";
  }
}
