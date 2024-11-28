package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface IssueAccountRepository
    extends JpaRepository<IssueAccount, Long>, JpaSpecificationExecutor<IssueAccount> {
  Optional<IssueAccount> findByCompartement(Compartement compartement);

  Optional<IssueAccount> findByCompartement_Id(Long id);
}
