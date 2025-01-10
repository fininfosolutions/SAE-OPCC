package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.Devise;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call_event")
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class CallEvent extends AbstractAuditingEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(precision = 21, scale = 4)
  private BigDecimal percentage;

  private String description;

  private String reference;

  private LocalDate closingDate;
  private LocalDate callDate;

  @Column(precision = 21, scale = 4)
  private BigDecimal calledAmount;

  @Enumerated(EnumType.STRING)
  private EventStatus eventStatus;

  @Column(precision = 21, scale = 4)
  private BigDecimal calledQuantity;

  @Column(precision = 21, scale = 4)
  private BigDecimal remainingQuantity;

  @Column(precision = 21, scale = 4)
  private BigDecimal remainingAmount;

  private String message;

  @ManyToOne @EqualsAndHashCode.Exclude private Issue issue;
  @ManyToOne private Devise devise;
}
