package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.Direction;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "account_sla", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class AccountTypeSLA extends AbstractAuditingEntity implements Serializable {
  @Id private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type", unique = true)
  private TransactionType transactionType;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_type")
  private AccountType accountType;

  @Column(name = "prefix")
  private String prefix;

  @Enumerated(EnumType.STRING)
  @Column(name = "sens")
  private Direction sens;

  @Enumerated(EnumType.STRING)
  @Column(name = "sens_cash")
  private Direction sensCash;

  @Column(name = "trans_direction")
  private Integer transDirection;

  @Column(name = "trans_direction_cash")
  private Integer transDirectionCash;
}
