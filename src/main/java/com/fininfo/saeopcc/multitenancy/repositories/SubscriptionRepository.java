package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository
    extends JpaRepository<Subscription, Long>, JpaSpecificationExecutor<Subscription> {
  // Page<Subscription> findByAsset(Asset asset, Pageable pageable);

  // long countByAsset(Asset asset);

  // List<Subscription> findByAsset_idAndStatus(Long assetId, SubscriptionStatus
  // subscriptionStatus);

  List<Subscription> findByIssue_id(Long issueId);
}
