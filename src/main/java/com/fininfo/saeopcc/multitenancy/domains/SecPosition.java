package com.fininfo.saeopcc.multitenancy.domains;

import com.fininfo.saeopcc.shared.domains.Asset;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SecPosition extends Position {

  @Id private Long id;

  private BigDecimal quantity;

  @ManyToOne private Asset asset;
}
