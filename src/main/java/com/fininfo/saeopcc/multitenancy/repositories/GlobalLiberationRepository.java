package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface GlobalLiberationRepository
    extends JpaRepository<GlobalLiberation, Long>, JpaSpecificationExecutor<GlobalLiberation> {
  List<GlobalLiberation> findByEventStatusAndCallEvent_Id(EventStatus eventStatus, Long id);

  Page<GlobalLiberation> findByCallEventIssueId(Long issueId, Pageable pageable);

  List<GlobalLiberation> findByCallEventIdInAndEventStatus(
      List<Long> callEventIds, EventStatus eventStatus);

  long countByCallEventIssueId(Long callEventIssueId);
}
