package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.shared.domains.Counterpart;
import com.fininfo.saeopcc.shared.domains.Counterpart_;
import com.fininfo.saeopcc.shared.repositories.CounterpartRepository;
import com.fininfo.saeopcc.shared.services.dto.CounterpartCriteria;
import com.fininfo.saeopcc.shared.services.dto.CounterpartDTO;
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
public class CounterpartQueryService extends QueryService<Counterpart> {

  private final Logger log = LoggerFactory.getLogger(CounterpartQueryService.class);

  @Autowired(required = false)
  private CounterpartRepository CounterpartRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<CounterpartDTO> findByCriteria(CounterpartCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Counterpart> specification = createSpecification(criteria);
    return CounterpartRepository.findAll(specification).stream()
        .map(Counterpart -> modelMapper.map(Counterpart, CounterpartDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<CounterpartDTO> findByCriteria(CounterpartCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Counterpart> specification = createSpecification(criteria);
    return CounterpartRepository.findAll(specification, page)
        .map(Counterpart -> modelMapper.map(Counterpart, CounterpartDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(CounterpartCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Counterpart> specification = createSpecification(criteria);
    return CounterpartRepository.count(specification);
  }

  public Specification<Counterpart> createSpecification(CounterpartCriteria criteria) {
    Specification<Counterpart> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getId(), Counterpart_.id));
      }
      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Counterpart_.description));
      }
    }
    return specification;
  }
}
