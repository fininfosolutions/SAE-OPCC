package com.fininfo.saeopcc.shared.domains.swift;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A SwiftTagField. */
@Entity
@Table(name = "swift_tag_field", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class SwiftTagField implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tag")
  private String tag;

  @ManyToOne
  @JsonIgnoreProperties(value = "swiftTagFields", allowSetters = true)
  @EqualsAndHashCode.Exclude
  private SwiftBlocSequence swiftBlocSequence;

  @OneToMany(mappedBy = "swiftTagField", cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @ToString.Exclude
  private Set<SwiftTagFieldValues> swiftTagFieldValues = new HashSet<>();
}
