package com.fininfo.saeopcc.multitenancy.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.shared.domains.Account;
import com.fininfo.saeopcc.shared.domains.AccountCategory;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.domains.Intermediary;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "securities_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class SecuritiesAccount extends Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JsonIgnoreProperties(value = "securitiesAccounts", allowSetters = true)
  private Asset asset;

  @ManyToOne
  @JsonIgnoreProperties(value = "securitiesAccounts", allowSetters = true)
  private Shareholder shareholder;

  @ManyToOne
  @JsonIgnoreProperties(value = "securitiesAccounts", allowSetters = true)
  private Intermediary intermediary;

  @ManyToOne
  @JsonIgnoreProperties(value = "securitiesAccounts", allowSetters = true)
  private AccountCategory accountCategory;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_type")
  private AccountType accountType;
}
