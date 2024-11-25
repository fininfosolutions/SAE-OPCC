package com.fininfo.saeopcc.shared.domains;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fund_manager", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class FundManager extends Role {

  private static final long serialVersionUID = 1L;

  @Id private Long id;

  @OneToMany(mappedBy = "fundManager", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private Set<FundOrganism> managedFunds = new HashSet<>();

 
}
