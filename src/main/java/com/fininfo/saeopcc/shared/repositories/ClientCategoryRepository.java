package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.ClientCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the ClientCategory entity. */
@SuppressWarnings("unused")
@Repository
public interface ClientCategoryRepository
    extends JpaRepository<ClientCategory, Long>, JpaSpecificationExecutor<ClientCategory> {}
