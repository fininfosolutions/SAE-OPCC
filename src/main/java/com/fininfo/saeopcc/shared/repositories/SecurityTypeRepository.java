package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.SecurityType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the SecurityType entity. */
@SuppressWarnings("unused")
@Repository
public interface SecurityTypeRepository
    extends JpaRepository<SecurityType, Long>, JpaSpecificationExecutor<SecurityType> {}
