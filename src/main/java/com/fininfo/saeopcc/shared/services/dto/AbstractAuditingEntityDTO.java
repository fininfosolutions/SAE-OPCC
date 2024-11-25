package com.fininfo.saeopcc.shared.services.dto;

import java.io.Serializable;
import java.time.Instant;
import lombok.Data;

@Data
public class AbstractAuditingEntityDTO implements Serializable {

  private String createdBy;

  private Instant createdDate;

  private String lastModifiedBy;

  private Instant lastModifiedDate;
}
