package com.fininfo.saeopcc.shared.domains.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagFieldValueNatureList;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A BrokerEntityMapping. */
@Entity
@Table(name = "broker_entity_mapping", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
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

  @Enumerated(EnumType.STRING)
  @Column(name = "field_value_nature")
  private SwiftTagFieldValueNatureList fieldValueNature;

  @ManyToOne
  @JsonIgnoreProperties(value = "brokerEntityMappings", allowSetters = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private InputExcelFlow inputExcelFlow;
}
