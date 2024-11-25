package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Devise;
import com.fininfo.saeopcc.shared.repositories.DeviseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviseService {

  @Autowired private DeviseRepository deviseRepository;

  public Devise syncDevise(Devise devise) {
    Devise synced = null;

    if (devise.getId() != null) {
      synced = deviseRepository.save(devise);
    } else log.error("Synchronize Devise Process Cannot Execute without ID !!!");

    return synced;
  }
}
