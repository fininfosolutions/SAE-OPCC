package com.fininfo.saeopcc.shared.domains;

import com.fininfo.saeopcc.config.multitenant.CurrentTenantResolver;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "issue_account", schema = CurrentTenantResolver.DEFAULT_SCHEMA)
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class IssueAccount extends AbstractAuditingEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull(message = "Le champ 'issueaccountNumber' est obligatoire")
  @Column(name = "issue_account_number")
  private BigDecimal issueaccountNumber;

  @Column(name = "description")
  private String description;

  @NotNull(message = "Le champ 'valueDescription' est obligatoire")
  @Column(name = "value_description")
  private String valueDescription;

  @NotNull(message = "Le champ 'openingaccountDate' est obligatoire")
  @Column(name = "opening_account_date")
  private LocalDate openingAccountDate;

  @NotNull(message = "Le champ 'closingaccountDate' est obligatoire")
  @Column(name = "closing_account_date")
  private LocalDate closingAccountDate;

  @NotNull(message = "Le champ 'securitiesquantity' est obligatoire")
  @Column(name = "securities_quantity")
  private BigDecimal securitiesquantity;

  @Column(name = "actif")
  private Boolean actif;

  @NotNull(message = "Le champ 'reference' est obligatoire")
  @Column(name = "reference", unique = true)
  private String reference;

  @OneToOne private Issue issue;
}
