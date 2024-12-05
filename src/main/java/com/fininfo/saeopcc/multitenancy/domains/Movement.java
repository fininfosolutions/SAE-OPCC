package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.Direction;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.InvestmentType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.Origin;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.Account;
import com.fininfo.saeopcc.shared.domains.Asset;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Movement extends AbstractAuditingEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String reference;

  private String instructionReference;

  private Long instructionId;

  @Enumerated(EnumType.STRING)
  private MovementStatus status;

  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  private LocalDate movementDate;

  private LocalDate impactedDate;

  private Boolean impacted;
  private Boolean htm;

  private Boolean reversed;

  private BigDecimal amount;
  private String nostroSecurityAccount;
  private String custodian;

  private BigDecimal quantity;

  private Integer direction;

  @Enumerated(EnumType.STRING)
  private Direction sens;

  @Enumerated(EnumType.STRING)
  private MovementType type;

  @Enumerated(EnumType.STRING)
  private InvestmentType investmentType;

  @Enumerated(EnumType.STRING)
  private Origin origin;

  @ManyToOne private Asset asset;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
}
