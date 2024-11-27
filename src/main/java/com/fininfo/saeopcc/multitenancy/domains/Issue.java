package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import com.fininfo.saeopcc.shared.domains.enumeration.IssueStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "issue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Issue extends AbstractAuditingEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "current_step")
  private Integer currentStep;

  @Enumerated(EnumType.STRING)
  @Column(name = "issue_status")
  private IssueStatus issueStatus;

  @OneToOne private IssueAccount issueAccount;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "issue", cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<Subscription> subscriptions = new HashSet<>();
}
