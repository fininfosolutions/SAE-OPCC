package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CallEventRepository
    extends JpaRepository<CallEvent, Long>, JpaSpecificationExecutor<CallEvent> {

  Page<CallEvent> findByIssue_id(Long issueId, Pageable pageable);

  List<CallEvent> findByIssue_IdAndEventStatus(Long issueId, EventStatus status);

  long countByIssue_Id(Long issueId);
}
