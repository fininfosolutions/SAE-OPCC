package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  List<Subscription> findByIssue_IdAndStatus(Long issueId, SubscriptionStatus status);

  List<Subscription> findByIssue_Id(Long issueId);

  Page<Subscription> findByIssue_Id(Long issueId, Pageable pageable);

  long countByIssue_Id(Long issueId);
}
