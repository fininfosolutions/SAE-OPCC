package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareholderRepository
    extends JpaRepository<Shareholder, Long>, JpaSpecificationExecutor<Shareholder> {

  Shareholder getShareholderByActiveTrueAndReference(String reference);

  Page<Shareholder> getAllByReferenceContainsOrDescriptionContains(
      String ref, String des, Pageable pageable);
}
