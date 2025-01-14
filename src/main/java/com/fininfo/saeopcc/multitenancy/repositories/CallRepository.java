package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CallRepository extends JpaRepository<Call, Long>, JpaSpecificationExecutor<Call> {
  List<Call> findBySubscriptionIdAndStatusOrderByIdDesc(Long subscriptionId, CallStatus status);

  Page<Call> findByCallEvent_Id(Long issueId, Pageable pageable);

  List<Call> findByCallEvent_IdAndStatus(Long issueId, CallStatus status);

  List<Call> findByCallEventIdInAndStatus(List<Long> callEventIds, CallStatus status);

  long countByCallEvent_Id(Long issueId);
}
