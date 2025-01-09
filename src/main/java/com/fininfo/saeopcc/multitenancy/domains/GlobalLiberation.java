package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "global_liberation")
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class GlobalLiberation extends AbstractAuditingEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(precision = 21, scale = 4)
  private BigDecimal percentage;

  private String reference;

  private String description;

  @Enumerated(EnumType.STRING)
  private EventStatus eventStatus;

  @ManyToOne @EqualsAndHashCode.Exclude private CallEvent callEvent;
}
