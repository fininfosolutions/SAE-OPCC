package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository
    extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {}
