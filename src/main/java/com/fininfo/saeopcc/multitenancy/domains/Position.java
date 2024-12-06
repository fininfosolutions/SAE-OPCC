package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.PositionType;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.Account;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = false)
public class Position extends AbstractAuditingEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private PositionType positionType;

  private String reference;

  private Instant positionDate;

  private LocalDate valueDate;

  private LocalDate endDate;

  @ManyToOne private Account account;
}
