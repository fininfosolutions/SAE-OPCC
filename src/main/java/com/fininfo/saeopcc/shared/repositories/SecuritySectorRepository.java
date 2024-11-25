package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.SecuritySector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the SecuritySector entity. */
@SuppressWarnings("unused")
@Repository
public interface SecuritySectorRepository
    extends JpaRepository<SecuritySector, Long>, JpaSpecificationExecutor<SecuritySector> {}
