package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CompartementRepository
    extends JpaRepository<Compartement, Long>, JpaSpecificationExecutor<Compartement> {

  // List<Compartement> findAllByClient_id(Long aLong, Pageable pageable);

  // Long countAllByClient_id(Long aLong);

  // Optional<Compartement> findByFund_Id(Long aLong);

  // Boolean existsByClient_Id(Long id);

  // List<Compartement> findAll();
}
