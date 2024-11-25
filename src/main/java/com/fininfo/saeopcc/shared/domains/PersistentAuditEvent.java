package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jhi_persistent_audit_event", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
public class PersistentAuditEvent implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "event_id")
  private Long id;

  @NotNull
  @Column(nullable = false)
  private String principal;

  @Column(name = "event_date")
  private Instant auditEventDate;

  @Column(name = "event_type")
  private String auditEventType;

  @ElementCollection
  @MapKeyColumn(name = "name")
  @Column(name = "value")
  @CollectionTable(
      name = "jhi_persistent_audit_evt_data",
      joinColumns = @JoinColumn(name = "event_id"))
  private Map<String, String> data = new HashMap<>();
}
