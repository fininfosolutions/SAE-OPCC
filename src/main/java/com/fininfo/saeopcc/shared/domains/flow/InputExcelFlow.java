package com.fininfo.saeopcc.shared.domains.flow;

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

/** A InputExcelFlow. */
@Entity
@Table(name = "input_excel_flow", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class InputExcelFlow implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "sheet_name")
  private String sheetName;

  @Column(name = "header_position")
  private Integer headerPosition;

  @Column(name = "first_column_position")
  private Integer firstColumnPosition;

  @Column(name = "last_column_position")
  private Integer lastColumnPosition;

  @Column(name = "content_start_position")
  private Integer contentStartPosition;

  @ManyToOne
  @JsonIgnoreProperties(value = "inputExcelFlows", allowSetters = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private ExcelFlow excelFlow;

  @OneToMany(mappedBy = "inputExcelFlow", cascade = CascadeType.ALL)
  private Set<ExcelMessageDescriptionDetails> excelMessageDescriptionDetails = new HashSet<>();

  @OneToMany(mappedBy = "inputExcelFlow", cascade = CascadeType.ALL)
  private Set<BrokerEntityMapping> brokerEntityMappings = new HashSet<>();
}
