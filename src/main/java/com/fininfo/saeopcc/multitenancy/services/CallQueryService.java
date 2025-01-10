package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.Call_;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallCriteria;
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
public class CallQueryService extends QueryService<Call> {

  private final Logger log = LoggerFactory.getLogger(CallQueryService.class);

  @Autowired private CallRepository callRepository;

  @Autowired private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<CallEventDTO> findByCriteria(CallCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Call> specification = createSpecification(criteria);
    List<Call> events = callRepository.findAll(specification);
    return events.stream()
        .map(event -> modelMapper.map(event, CallEventDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<CallEventDTO> findByCriteria(CallCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Call> specification = createSpecification(criteria);
    Page<Call> eventPage = callRepository.findAll(specification, page);
    return eventPage.map(event -> modelMapper.map(event, CallEventDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(CallCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Call> specification = createSpecification(criteria);
    return callRepository.count(specification);
  }

  public Specification<Call> createSpecification(CallCriteria criteria) {
    Specification<Call> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Call_.id));
      }

      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Call_.description));
      }

      if (criteria.getPercentage() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getPercentage(), Call_.percentage));
      }
    }
    return specification;
  }
}
