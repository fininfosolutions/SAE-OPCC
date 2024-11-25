package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.SecurityClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SecurityClassRepository
    extends JpaRepository<SecurityClass, Long>, JpaSpecificationExecutor<SecurityClass> {}
