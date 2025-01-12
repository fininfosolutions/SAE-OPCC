package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation_;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventDTO;
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

/** */
@Service
@Transactional(readOnly = true)
public class GlobalLiberationQueryService extends QueryService<GlobalLiberation> {

  private final Logger log = LoggerFactory.getLogger(CallEventQueryService.class);

  @Autowired private GlobalLiberationRepository globalLiberationRepository;

  @Autowired private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<CallEventDTO> findByCriteria(CallEventCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<GlobalLiberation> specification = createSpecification(criteria);
    List<GlobalLiberation> events = globalLiberationRepository.findAll(specification);
    return events.stream()
        .map(event -> modelMapper.map(event, CallEventDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<CallEventDTO> findByCriteria(CallEventCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<GlobalLiberation> specification = createSpecification(criteria);
    Page<GlobalLiberation> eventPage = globalLiberationRepository.findAll(specification, page);
    return eventPage.map(event -> modelMapper.map(event, CallEventDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(CallEventCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<GlobalLiberation> specification = createSpecification(criteria);
    return globalLiberationRepository.count(specification);
  }

  public Specification<GlobalLiberation> createSpecification(CallEventCriteria criteria) {
    Specification<GlobalLiberation> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getId(), GlobalLiberation_.id));
      }

      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), GlobalLiberation_.description));
      }

      if (criteria.getPercentage() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getPercentage(), GlobalLiberation_.percentage));
      }

      if (criteria.getEventStatus() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getEventStatus(), GlobalLiberation_.eventStatus));
      }
    }
    return specification;
  }
}
