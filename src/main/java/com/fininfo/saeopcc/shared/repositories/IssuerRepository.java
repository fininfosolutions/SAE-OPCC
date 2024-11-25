package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Issuer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Issuer entity. */
@SuppressWarnings("unused")
@Repository
public interface IssuerRepository
    extends JpaRepository<Issuer, Long>, JpaSpecificationExecutor<Issuer> {
  Issuer getIssuerByReference(String ref);

  Issuer getIssuerByActiveIsTrueAndReference(String ref);
}
