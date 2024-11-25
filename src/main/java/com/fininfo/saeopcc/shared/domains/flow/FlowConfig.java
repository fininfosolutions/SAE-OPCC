package com.fininfo.saeopcc.shared.domains.flow;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.FlowType;
import com.fininfo.saeopcc.shared.domains.enumeration.Sens;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A FlowConfig. */
@Entity
@Table(name = "flow_config", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class FlowConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "sens")
  private Sens sens;

  @Enumerated(EnumType.STRING)
  @Column(name = "flow_type")
  private FlowType flowType;

  @Column(name = "code")
  private String code;

  @Column(name = "description")
  private String description;
}
