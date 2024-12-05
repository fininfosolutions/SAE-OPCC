package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.AccountCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the AccountCategory entity. */
@SuppressWarnings("unused")
@Repository
public interface AccountCategoryRepository
    extends JpaRepository<AccountCategory, Long>, JpaSpecificationExecutor<AccountCategory> {

  Optional<AccountCategory> findByCode(String code);
}
