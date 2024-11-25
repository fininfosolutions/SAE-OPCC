package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.AccountNature;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the AccountNature entity. */
@SuppressWarnings("unused")
@Repository
public interface AccountNatureRepository
    extends JpaRepository<AccountNature, Long>, JpaSpecificationExecutor<AccountNature> {}
