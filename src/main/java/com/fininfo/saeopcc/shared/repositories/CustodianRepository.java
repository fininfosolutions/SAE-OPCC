package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Custodian;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Custodian entity. */
@SuppressWarnings("unused")
@Repository
public interface CustodianRepository
    extends JpaRepository<Custodian, Long>, JpaSpecificationExecutor<Custodian> {

  Optional<Custodian> findOneById(Long id);

  Custodian getCustodianByReference(String ref);

  Custodian getCustodianByActiveTrueAndReference(String reference);
}
