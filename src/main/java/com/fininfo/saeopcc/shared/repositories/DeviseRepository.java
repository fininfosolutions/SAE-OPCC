package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Devise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Devise entity. */
@Repository
public interface DeviseRepository
    extends JpaRepository<Devise, Long>, JpaSpecificationExecutor<Devise> {}
