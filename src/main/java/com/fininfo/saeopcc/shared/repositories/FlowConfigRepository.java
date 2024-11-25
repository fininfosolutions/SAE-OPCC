package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.flow.FlowConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FlowConfigRepository
    extends JpaRepository<FlowConfig, Long>, JpaSpecificationExecutor<FlowConfig> {}
