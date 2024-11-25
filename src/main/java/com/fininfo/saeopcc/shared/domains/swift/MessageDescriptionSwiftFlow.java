package com.fininfo.saeopcc.shared.domains.swift;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A MessageDescriptionSwiftFlow. */
@Entity
@Table(name = "message_description_swift_flow", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class MessageDescriptionSwiftFlow implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "description")
  private String description;

  @OneToOne
  @JoinColumn(unique = true)
  private SwiftBlocFour swiftBlocFour;
}
