package com.fininfo.saeopcc.shared.domains.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ExcelMessageDescriptionDetails. */
@Entity
@Table(name = "excel_message_description_details", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class ExcelMessageDescriptionDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "field_name")
  private String fieldName;

  @Column(name = "field_type")
  private String fieldType;

  @Column(name = "defaultvalue")
  private String defaultvalue;

  @Column(name = "is_nullable")
  private Boolean isNullable;

  @Column(name = "field_index")
  private Integer fieldIndex;

  @Column(name = "field_comment")
  private String fieldComment;

  @ManyToOne
  @JsonIgnoreProperties(value = "excelMessageDescriptionDetails", allowSetters = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private InputExcelFlow inputExcelFlow;
}
