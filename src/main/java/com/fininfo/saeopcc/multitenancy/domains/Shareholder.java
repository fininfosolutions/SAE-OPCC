package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.shared.domains.Role;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "shareholder")
@EqualsAndHashCode(callSuper = false)
public class Shareholder extends Role {

  @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
