package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.SAEConfig;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SAEConfigRepository
    extends JpaRepository<SAEConfig, Long>, JpaSpecificationExecutor<SAEConfig> {
  Optional<SAEConfig> findSAEConfigByCode(String code);
}
