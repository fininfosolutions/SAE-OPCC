package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.FiscalAuthority;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the FiscalAuthority entity. */
@SuppressWarnings("unused")
@Repository
public interface FiscalAuthorityRepository
    extends JpaRepository<FiscalAuthority, Long>, JpaSpecificationExecutor<FiscalAuthority> {
  FiscalAuthority getFiscalAuthorityByReference(String ref);
}
