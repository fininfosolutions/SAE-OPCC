package com.fininfo.saeopcc.multitenancy.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fininfo.saeopcc.multitenancy.domains.CallEvent;

@SuppressWarnings("unused")
@Repository
public interface CallEventRepository
    extends JpaRepository<CallEvent, Long>, JpaSpecificationExecutor<CallEvent> {

  Page<CallEvent> findByIssue_id(Long issueId, Pageable pageable);

  long countByIssue_Id(Long issueId);
}
