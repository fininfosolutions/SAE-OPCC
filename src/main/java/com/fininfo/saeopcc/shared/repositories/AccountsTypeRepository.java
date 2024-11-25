package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.AccountsType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the AccountsType entity. */
@SuppressWarnings("unused")
@Repository
public interface AccountsTypeRepository
    extends JpaRepository<AccountsType, Long>, JpaSpecificationExecutor<AccountsType> {}
