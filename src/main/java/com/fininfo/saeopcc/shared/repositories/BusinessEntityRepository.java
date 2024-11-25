package com.fininfo.saeopcc.shared.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fininfo.saeopcc.shared.domains.BusinessEntity;

/** Spring Data repository for the BusinessEntity entity. */
@SuppressWarnings("unused")
@Repository
public interface BusinessEntityRepository
    extends JpaRepository<BusinessEntity, Long>, JpaSpecificationExecutor<BusinessEntity> {

  BusinessEntity findByIdentifier(String identifier);


}
