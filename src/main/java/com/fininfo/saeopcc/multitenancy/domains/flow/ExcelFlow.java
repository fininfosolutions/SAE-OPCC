package com.fininfo.saeopcc.multitenancy.domains.flow;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.ExcelContentType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ExcelFlow. */
@Entity
@Table(name = "excel_flow")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
public class ExcelFlow extends FlowConfig {

  @Id private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "excel_content_type")
  private ExcelContentType excelContentType;

  @OneToMany(mappedBy = "excelFlow", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<InputExcelFlow> inputExcelFlows = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ExcelFlow excelFlow = (ExcelFlow) o;

    if (id != null ? !id.equals(excelFlow.id) : excelFlow.id != null) return false;
    return excelContentType == excelFlow.excelContentType;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (excelContentType != null ? excelContentType.hashCode() : 0);
    return result;
  }
}
