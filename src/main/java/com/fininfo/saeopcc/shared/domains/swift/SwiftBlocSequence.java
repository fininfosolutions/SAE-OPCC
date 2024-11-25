package com.fininfo.saeopcc.shared.domains.swift;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftBlocSequenceNumber;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A SwiftBlocSequence. */
@Entity
@Table(name = "swift_bloc_sequence", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class SwiftBlocSequence implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "sequence_number")
  private SwiftBlocSequenceNumber sequenceNumber;

  @Column(name = "occurence")
  private Integer occurence;

  @OneToMany(mappedBy = "swiftBlocSequence", cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @ToString.Exclude
  private Set<SwiftTagField> swiftTagFields = new HashSet<>();

  @ManyToOne
  @JsonIgnoreProperties(value = "swiftBlocSequences", allowSetters = true)
  @EqualsAndHashCode.Exclude
  private SwiftBlocFour swiftBlocFour;
}
