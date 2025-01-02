package com.fininfo.saeopcc.multitenancy.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.Event_;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventDTO;

/** */
@Service
@Transactional(readOnly = true)
public class CallEventQueryService extends QueryService<CallEvent> {

  private final Logger log = LoggerFactory.getLogger(CallEventQueryService.class);

  @Autowired private CallEventRepository eventRepository;

  @Autowired private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<CallEventDTO> findByCriteria(CallEventCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<CallEvent> specification = createSpecification(criteria);
    List<CallEvent> events = eventRepository.findAll(specification);
    return events.stream()
        .map(event -> modelMapper.map(event, CallEventDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<CallEventDTO> findByCriteria(CallEventCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<CallEvent> specification = createSpecification(criteria);
    Page<CallEvent> eventPage = eventRepository.findAll(specification, page);
    return eventPage.map(event -> modelMapper.map(event, CallEventDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(CallEventCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<CallEvent> specification = createSpecification(criteria);
    return eventRepository.count(specification);
  }

  public Specification<CallEvent> createSpecification(CallEventCriteria criteria) {
    Specification<CallEvent> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Event_.id));
      }

      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Event_.description));
      }

      // if (criteria.getClosingDate() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(criteria.getClosingDate(), Event_.closingDate));
      // }

      // if (criteria.getAppealDate() != null) {
      //   specification =
      //       specification.and(buildRangeSpecification(criteria.getAppealDate(), Event_.appealDate));
      // }

      // if (criteria.getGlobalSousInitialAmount() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(
      //               criteria.getGlobalSousInitialAmount(), Event_.globalSousInitialAmount));
      // }

      // if (criteria.getGlobalSousInitialQuantity() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(
      //               criteria.getGlobalSousInitialQuantity(), Event_.globalSousInitialQuantity));
      // }

      // if (criteria.getGlobalSousAmount() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(criteria.getGlobalSousAmount(), Event_.globalSousAmount));
      // }

      // if (criteria.getGlobalSousQuantity() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(
      //               criteria.getGlobalSousQuantity(), Event_.globalSousQuantity));
      // }

      // if (criteria.getGlobalAppealAmount() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(
      //               criteria.getGlobalAppealAmount(), Event_.globalAppealAmount));
      // }

      // if (criteria.getGlobalAppealQuantity() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(
      //               criteria.getGlobalAppealQuantity(), Event_.globalAppealQuantity));
      // }

      if (criteria.getPercentage() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getPercentage(), Event_.percentage));
      }

      if (criteria.getEventStatus() != null) {
        specification =
            specification.and(buildSpecification(criteria.getEventStatus(), Event_.eventStatus));
      }

      // if (criteria.getGlobalUnfundedAmount() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(
      //               criteria.getGlobalUnfundedAmount(), Event_.globalUnfundedAmount));
      // }

      // if (criteria.getGlobalUnfundedQuantity() != null) {
      //   specification =
      //       specification.and(
      //           buildRangeSpecification(
      //               criteria.getGlobalUnfundedQuantity(), Event_.globalUnfundedQuantity));
      // }

      // if (criteria.getDinvestmentPeriod() != null) {
      //   specification =
      //       specification.and(
      //           buildStringSpecification(
      //               criteria.getDinvestmentPeriod(), Event_.dinvestmentPeriod));
      // }

      // if (criteria.getInvestmentPeriod() != null) {
      //   specification =
      //       specification.and(
      //           buildStringSpecification(criteria.getInvestmentPeriod(), Event_.investmentPeriod));
      // }

      // if (criteria.getIssueDescription() != null) {
      //   specification =
      //       specification.and(
      //           buildSpecification(
      //               criteria.getIssueDescription(),
      //               root -> root.join(Event_.issue, JoinType.LEFT).get(Issue_.description)));
      // }
      // if (criteria.getIssueAmount() != null) {
      //   specification =
      //       specification.and(
      //           (root, query, criteriaBuilder) -> {
      //             Join<CallEvent, Issue> issueJoin = root.join(Event_.issue, JoinType.LEFT);
      //             return criteriaBuilder.equal(
      //                 issueJoin.get(Issue_.amount), criteria.getIssueAmount());
      //           });
      // }
    }
    return specification;
  }
}
