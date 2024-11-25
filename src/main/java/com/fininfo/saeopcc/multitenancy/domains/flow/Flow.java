package com.fininfo.saeopcc.multitenancy.domains.flow;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.FlowStatus;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.enumeration.Sens;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A Flow. */
@Entity
@Table(name = "flow")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
public class Flow extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "exception")
  private String exception;

  @Column(name = "exception_description")
  private String exceptionDescription;

  @Enumerated(EnumType.STRING)
  @Column(name = "flow_status")
  private FlowStatus flowStatus;

  @Column(name = "total_record")
  private Integer totalRecord;

  @Enumerated(EnumType.STRING)
  @Column(name = "sens")
  private Sens sens;

  @Column(name = "flow_date")
  private LocalDate flowDate;

  @Column(name = "nbre_of_valid_record")
  private Integer nbreOfValidRecord;

  @Column(name = "nbre_of_failed_record")
  private Integer nbreOfFailedRecord;

  @Column(name = "file_type")
  private String fileType;

  @OneToMany(mappedBy = "flow", cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  @ToString.Exclude
  private Set<Notification> notifications = new HashSet<>();

  @OneToMany(mappedBy = "flow", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  private Set<Document> documents = new HashSet<>();
}
