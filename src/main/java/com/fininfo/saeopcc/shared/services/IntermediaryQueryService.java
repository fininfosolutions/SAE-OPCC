package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.shared.domains.Intermediary;
import com.fininfo.saeopcc.shared.domains.Intermediary_;
import com.fininfo.saeopcc.shared.repositories.IntermediaryRepository;
import com.fininfo.saeopcc.shared.services.dto.IntermediaryCriteria;
import com.fininfo.saeopcc.shared.services.dto.IntermediaryDTO;
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

@Service
@Transactional(readOnly = true)
public class IntermediaryQueryService extends QueryService<Intermediary> {

  private final Logger log = LoggerFactory.getLogger(IntermediaryQueryService.class);

  @Autowired(required = false)
  private IntermediaryRepository IntermediaryRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<IntermediaryDTO> findByCriteria(IntermediaryCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Intermediary> specification = createSpecification(criteria);
    return IntermediaryRepository.findAll(specification).stream()
        .map(Intermediary -> modelMapper.map(Intermediary, IntermediaryDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<IntermediaryDTO> findByCriteria(IntermediaryCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Intermediary> specification = createSpecification(criteria);
    return IntermediaryRepository.findAll(specification, page)
        .map(Intermediary -> modelMapper.map(Intermediary, IntermediaryDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(IntermediaryCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Intermediary> specification = createSpecification(criteria);
    return IntermediaryRepository.count(specification);
  }

  public Specification<Intermediary> createSpecification(IntermediaryCriteria criteria) {
    Specification<Intermediary> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getId(), Intermediary_.id));
      }
      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Intermediary_.description));
      }
    }
    return specification;
  }
}
