package com.fininfo.saeopcc.multitenancy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;

@SuppressWarnings("unused")
@Repository
public interface CallRepository
    extends JpaRepository<Call, Long>, JpaSpecificationExecutor<Call> {
  List<Call> findBySubscriptionIdAndCallStatusOrderByIdDesc(
      Long subscriptionId, CallStatus appealStatus);
}
