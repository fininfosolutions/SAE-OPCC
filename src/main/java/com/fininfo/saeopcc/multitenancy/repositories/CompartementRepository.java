package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.enumeration.UnitCategory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CompartementRepository
    extends JpaRepository<Compartement, Long>, JpaSpecificationExecutor<Compartement> {

        Page<Fund> findByIdNotIn( List<Long> l, Pageable pageable);

}
