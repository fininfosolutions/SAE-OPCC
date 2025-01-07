package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent_;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
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
        specification = specification.and(buildRangeSpecification(criteria.getId(), CallEvent_.id));
      }

      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), CallEvent_.description));
      }

      if (criteria.getPercentage() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getPercentage(), CallEvent_.percentage));
      }

      if (criteria.getEventStatus() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getEventStatus(), CallEvent_.eventStatus));
      }
    }
    return specification;
  }
}
