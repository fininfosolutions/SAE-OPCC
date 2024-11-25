package com.fininfo.saeopcc.shared.domains.swift;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A SwiftBlocFour. */
@Entity
@Table(name = "swift_bloc_four", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class SwiftBlocFour implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "swift_type")
  private String swiftType;

  @OneToMany(mappedBy = "swiftBlocFour")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @ToString.Exclude
  private Set<SwiftBlocSequence> swiftBlocSequences = new HashSet<>();
}
