package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.FundOrganism;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the FundOrganism entity. */
@SuppressWarnings("unused")
@Repository
public interface FundOrganismRepository
    extends JpaRepository<FundOrganism, Long>, JpaSpecificationExecutor<FundOrganism> {}
