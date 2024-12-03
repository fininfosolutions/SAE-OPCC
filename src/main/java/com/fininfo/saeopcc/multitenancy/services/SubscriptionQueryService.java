package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.Subscription_;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
import com.fininfo.saeopcc.shared.domains.Role_;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.JoinType;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SubscriptionQueryService extends QueryService<Subscription> {

  private final Logger log = LoggerFactory.getLogger(SubscriptionQueryService.class);

  @Autowired(required = false)
  private SubscriptionRepository subscriptionRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  public List<SubscriptionDTO> findByCriteria(SubscriptionCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Subscription> specification = createSpecification(criteria);
    List<Subscription> subscriptions = subscriptionRepository.findAll(specification);
    return subscriptions.stream()
        .map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class))
        .collect(Collectors.toList());
  }

  public Page<SubscriptionDTO> findByCriteria(SubscriptionCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Subscription> specification = createSpecification(criteria);
    Page<Subscription> subscriptionPage = subscriptionRepository.findAll(specification, page);
    return subscriptionPage.map(
        subscription -> modelMapper.map(subscription, SubscriptionDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(SubscriptionCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Subscription> specification = createSpecification(criteria);
    return subscriptionRepository.count(specification);
  }

  public Specification<Subscription> createSpecification(SubscriptionCriteria criteria) {
    Specification<Subscription> specification = Specification.where(null);

    if (criteria == null) {
      return specification;
    }

    if (criteria.getId() != null) {
      specification =
          specification.and(buildRangeSpecification(criteria.getId(), Subscription_.id));
    }

    if (criteria.getAmount() != null) {
      specification =
          specification.and(buildRangeSpecification(criteria.getAmount(), Subscription_.amount));
    }

    if (criteria.getQuantity() != null) {
      specification =
          specification.and(
              buildRangeSpecification(criteria.getQuantity(), Subscription_.quantity));
    }

    if (criteria.getReference() != null) {
      specification =
          specification.and(buildSpecification(criteria.getReference(), Subscription_.reference));
    }

    // if (criteria.getAssetIsin() != null) {
    //   specification =
    //       specification.and(
    //           buildSpecification(
    //               criteria.getAssetIsin(),
    //               root -> root.join(Subscription_.asset, JoinType.LEFT).get(Asset_.isin)));
    // }

    if (criteria.getShareholderReference() != null) {
      specification =
          specification.and(
              buildSpecification(
                  criteria.getShareholderReference(),
                  root ->
                      root.join(Subscription_.shareholder, JoinType.LEFT).get(Role_.reference)));
    }

    if (criteria.getCustodianReference() != null) {
      specification =
          specification.and(
              buildSpecification(
                  criteria.getCustodianReference(),
                  root -> root.join(Subscription_.custodian, JoinType.LEFT).get(Role_.reference)));
    }

    return specification;
  }
}
