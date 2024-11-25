package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the BusinessEntity entity. */
@SuppressWarnings("unused")
@Repository
public interface BusinessEntityRepository
    extends JpaRepository<BusinessEntity, Long>, JpaSpecificationExecutor<BusinessEntity> {

  BusinessEntity findByIdentifier(String identifier);
}
