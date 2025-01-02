package com.fininfo.saeopcc.multitenancy.domains;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.Devise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "liberation")
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Liberation extends AbstractAuditingEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private String reference;
  private LocalDate liberationDate;

  private BigDecimal liberationAmount;
  private BigDecimal liberationQuantity;

  private BigDecimal remainingQuantity;
  private BigDecimal remainingAmount;
  private BigDecimal percentage;


  @Enumerated(EnumType.STRING)
  private LiberationStatus liberationStatus;

  @ManyToOne @EqualsAndHashCode.Exclude private LiberationEvent liberationEvent;
  @ManyToOne @EqualsAndHashCode.Exclude private Call call;
  @ManyToOne private Devise devise;
}
