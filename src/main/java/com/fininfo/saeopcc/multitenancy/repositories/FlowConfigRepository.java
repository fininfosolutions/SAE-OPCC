package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.flow.FlowConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FlowConfigRepository
    extends JpaRepository<FlowConfig, Long>, JpaSpecificationExecutor<FlowConfig> {}
