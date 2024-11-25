package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.flow.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FlowRepository extends JpaRepository<Flow, Long>, JpaSpecificationExecutor<Flow> {}
