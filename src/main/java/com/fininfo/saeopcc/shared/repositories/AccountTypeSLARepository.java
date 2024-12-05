package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.shared.domains.AccountTypeSLA;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountTypeSLARepository
    extends JpaRepository<AccountTypeSLA, Long>, JpaSpecificationExecutor<AccountTypeSLA> {

  Optional<AccountTypeSLA> findByTransactionType(TransactionType transactionType);
}
