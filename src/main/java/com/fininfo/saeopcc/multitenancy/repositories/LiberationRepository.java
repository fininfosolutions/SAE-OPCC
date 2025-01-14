package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LiberationRepository
    extends JpaRepository<Liberation, Long>, JpaSpecificationExecutor<Liberation> {

  Page<Liberation> findByGlobalLiberation_Id(Long issueId, Pageable pageable);

  List<Liberation> findByGlobalLiberationIdInAndStatus(
      List<Long> globalLiberationIds, LiberationStatus status);

  long countByGlobalLiberation_Id(Long issueId);
}
