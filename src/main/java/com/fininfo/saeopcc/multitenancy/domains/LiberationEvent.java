package com.fininfo.saeopcc.multitenancy.domains;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "liberation_event")
@SuppressWarnings("squid:S2160")
@EqualsAndHashCode(callSuper = false)
public class LiberationEvent extends Event  {

  @Id
  private Long id;

    @ManyToOne @EqualsAndHashCode.Exclude private CallEvent callEvent;

  
}
