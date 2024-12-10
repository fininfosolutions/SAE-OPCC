package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Appeal;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.AppealStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface AppealRepository
    extends JpaRepository<Appeal, Long>, JpaSpecificationExecutor<Appeal> {
  List<Appeal> findBySubscriptionIdAndAppealStatusOrderByIdDesc(
      Long subscriptionId, AppealStatus appealStatus);
}
