package com.fininfo.saeopcc.shared.domains.swift;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagFieldValueNatureList;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagValueTypeList;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A SwiftTagFieldValues. */
@Data
@Entity
@Table(name = "swift_tag_field_values", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SwiftTagFieldValues implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "swift_tag_field_value_type")
  private SwiftTagValueTypeList swiftTagFieldValueType;

  @Column(name = "default_value")
  private String defaultValue;

  @Column(name = "mapped_value")
  private String mappedValue;

  @Enumerated(EnumType.STRING)
  @Column(name = "swift_tag_field_value_nature")
  private SwiftTagFieldValueNatureList swiftTagFieldValueNature;

  @ManyToOne
  @JsonIgnoreProperties(value = "swiftTagFieldValues", allowSetters = true)
  @EqualsAndHashCode.Exclude
  private SwiftTagField swiftTagField;
}
