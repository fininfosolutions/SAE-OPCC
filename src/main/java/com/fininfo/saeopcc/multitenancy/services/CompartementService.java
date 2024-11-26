package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.repositories.CompartementRepository;
import com.fininfo.saeopcc.util.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompartementService {

  @Autowired private CompartementRepository compartementRepository;

  public Compartement syncCompartement(Compartement compartement, String tenant) {

    Compartement synced = null;
    TenantContext.setTenantId(tenant);
    if (compartement.getId() != null) {
      synced = compartementRepository.save(compartement);

    } else log.error("Synchronize Compartement Process Cannot Execute without ID !!!");

    return synced;
  }
}
