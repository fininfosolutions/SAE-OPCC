package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountClassification;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountNature;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = false)
public class Account extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id private Long id;

  @Column(name = "description")
  private String description;

  @Column(name = "account_number", unique = true)
  private String accountNumber;

  @Column(name = "account_classification")
  @Enumerated(EnumType.STRING)
  private AccountClassification accountClassification;

  @Column(name = "account_type")
  @Enumerated(EnumType.STRING)
  private AccountType accountType;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_nature")
  private AccountNature accountNature;

  @Column(name = "root")
  private String root;

  @Column(name = "code1")
  private String code1;

  @Column(name = "code2")
  private String code2;

  @Column(name = "is_active")
  private Boolean isActive;
}
