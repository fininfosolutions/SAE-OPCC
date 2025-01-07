package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface GlobalLiberationRepository
    extends JpaRepository<GlobalLiberation, Long>, JpaSpecificationExecutor<GlobalLiberation> {}
