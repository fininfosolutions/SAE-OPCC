package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.flow.Document;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository
    extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

  @Query(value = "SELECT max(d.mur) from Document d")
  Optional<Long> findMaxMur();

  Optional<Document> findByFlowId(Long id);

  Optional<Document> findByReferenceNotif(String referenceNotif);

  Optional<Document> findFirstByOrderByIdDesc();
}
