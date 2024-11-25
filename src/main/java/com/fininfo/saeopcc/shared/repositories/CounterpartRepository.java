package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Counterpart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Counterpart entity. */
@SuppressWarnings("unused")
@Repository
public interface CounterpartRepository
    extends JpaRepository<Counterpart, Long>, JpaSpecificationExecutor<Counterpart> {
  Counterpart getCounterpartByReference(String identifier);

  Counterpart getCounterpartByActiveTrueAndReference(String reference);
}
