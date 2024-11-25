package com.fininfo.saeopcc.shared.domains.flow;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.ExcelContentType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ExcelFlow. */
@Entity
@Table(name = "excel_flow", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExcelFlow extends FlowConfig {

  @Id private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "excel_content_type")
  private ExcelContentType excelContentType;

  @OneToMany(mappedBy = "excelFlow", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<InputExcelFlow> inputExcelFlows = new HashSet<>();
}
