package com.fininfo.saeopcc.multitenancy.domains.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A BrokerEntityMapping. */
@Entity
@Table(name = "broker_entity_mapping")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
public class BrokerEntityMapping implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "source_field")
  private String sourceField;

  @Column(name = "target_field")
  private String targetField;

  @Column(name = "default_value")
  private String defaultValue;

  @ManyToOne
  @JsonIgnoreProperties(value = "brokerEntityMappings", allowSetters = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private InputExcelFlow inputExcelFlow;
}
