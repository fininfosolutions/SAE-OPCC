package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.ResidenceStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the ResidenceStatus entity. */
@SuppressWarnings("unused")
@Repository
public interface ResidenceStatusRepository
    extends JpaRepository<ResidenceStatus, Long>, JpaSpecificationExecutor<ResidenceStatus> {}
