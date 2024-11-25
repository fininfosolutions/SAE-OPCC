package com.fininfo.saeopcc.multitenancy.domains.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ExcelMessageDescriptionDetails. */
@Entity
@Table(name = "excel_message_description_details")
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

  @Column(name = "default_value")
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
