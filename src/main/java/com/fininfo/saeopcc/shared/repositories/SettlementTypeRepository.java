package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.SettlementType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the SettlementType entity. */
@SuppressWarnings("unused")
@Repository
public interface SettlementTypeRepository
    extends JpaRepository<SettlementType, Long>, JpaSpecificationExecutor<SettlementType> {}
