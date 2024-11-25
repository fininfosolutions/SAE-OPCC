package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import com.fininfo.saeopcc.shared.domains.enumeration.FiscalStatus;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "counterpart", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class Counterpart extends Role {

  @Id private Long id;

  @Column(name = "affilation_date")
  private LocalDate affilationDate;

  @Column(name = "affiliation_date")
  private LocalDate affiliationDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "fiscal_status")
  private FiscalStatus fiscalStatus;

  public Counterpart affilationDate(LocalDate affilationDate) {
    this.affilationDate = affilationDate;
    return this;
  }

  public Counterpart affiliationDate(LocalDate affiliationDate) {
    this.affiliationDate = affiliationDate;
    return this;
  }

  public Counterpart fiscalStatus(FiscalStatus fiscalStatus) {
    this.fiscalStatus = fiscalStatus;
    return this;
  }
}
