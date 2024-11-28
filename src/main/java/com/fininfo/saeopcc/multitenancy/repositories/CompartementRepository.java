package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CompartementRepository
    extends JpaRepository<Compartement, Long>, JpaSpecificationExecutor<Compartement> {

  @Query(
      "SELECT c FROM Compartement c WHERE c.id NOT IN (SELECT ia.compartement.id FROM IssueAccount ia) ")
  Page<Compartement> findAllWithoutIssueAccount(
      Specification<Compartement> specification, Pageable pageable);

  Optional<Compartement> findByFund_Id(Long id);
}
