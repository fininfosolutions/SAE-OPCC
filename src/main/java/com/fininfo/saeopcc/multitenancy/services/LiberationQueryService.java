package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.domains.Liberation_;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.LiberationCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.LiberationDTO;
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
public class LiberationQueryService extends QueryService<Liberation> {

  private final Logger log = LoggerFactory.getLogger(LiberationQueryService.class);

  @Autowired private LiberationRepository liberationRepository;

  @Autowired private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<LiberationDTO> findByCriteria(LiberationCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Liberation> specification = createSpecification(criteria);
    List<Liberation> events = liberationRepository.findAll(specification);
    return events.stream()
        .map(event -> modelMapper.map(event, LiberationDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<LiberationDTO> findByCriteria(LiberationCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Liberation> specification = createSpecification(criteria);
    Page<Liberation> eventPage = liberationRepository.findAll(specification, page);
    return eventPage.map(event -> modelMapper.map(event, LiberationDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(LiberationCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Liberation> specification = createSpecification(criteria);
    return liberationRepository.count(specification);
  }

  public Specification<Liberation> createSpecification(LiberationCriteria criteria) {
    Specification<Liberation> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getId(), Liberation_.id));
      }

      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Liberation_.description));
      }

      if (criteria.getPercentage() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getPercentage(), Liberation_.percentage));
      }
    }
    return specification;
  }
}
