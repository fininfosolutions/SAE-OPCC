package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.SAEConfig;
import com.fininfo.saeopcc.shared.repositories.SAEConfigRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SAEConfigService {

  @Autowired private SAEConfigRepository SAEConfigRepository;

  public SAEConfig getSAEConfigByCode(String code) {
    Optional<SAEConfig> optional = SAEConfigRepository.findSAEConfigByCode(code);
    return optional.orElse(null);
  }
}
