package com.fininfo.saeopcc.shared.domains;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fund_organism", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class FundOrganism extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "identifier")
  private String identifier;

  @Column(name = "description")
  private String description;

  @Column(name = "long_name")
  private String longName;



  @ManyToOne
  @JsonIgnoreProperties(value = "fundOrganisms", allowSetters = true)
  private FundManager fundManager;

  public FundOrganism identifier(String identifier) {
    this.identifier = identifier;
    return this;
  }

  public FundOrganism description(String description) {
    this.description = description;
    return this;
  }

  public FundOrganism longName(String longName) {
    this.longName = longName;
    return this;
  }

 

  public FundOrganism fundManager(FundManager fundManager) {
    this.fundManager = fundManager;
    return this;
  }
}
