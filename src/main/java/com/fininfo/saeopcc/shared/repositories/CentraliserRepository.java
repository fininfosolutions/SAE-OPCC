package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Centraliser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Centraliser entity. */
@SuppressWarnings("unused")
@Repository
public interface CentraliserRepository
    extends JpaRepository<Centraliser, Long>, JpaSpecificationExecutor<Centraliser> {}
