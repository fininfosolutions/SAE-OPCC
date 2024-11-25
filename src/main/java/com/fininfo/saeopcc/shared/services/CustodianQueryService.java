package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.shared.domains.Custodian;
import com.fininfo.saeopcc.shared.domains.Custodian_;
import com.fininfo.saeopcc.shared.repositories.CustodianRepository;
import com.fininfo.saeopcc.shared.services.dto.CustodianCriteria;
import com.fininfo.saeopcc.shared.services.dto.CustodianDTO;
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
public class CustodianQueryService extends QueryService<Custodian> {

  private final Logger log = LoggerFactory.getLogger(CustodianQueryService.class);

  @Autowired(required = false)
  private CustodianRepository CustodianRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<CustodianDTO> findByCriteria(CustodianCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Custodian> specification = createSpecification(criteria);
    return CustodianRepository.findAll(specification).stream()
        .map(Custodian -> modelMapper.map(Custodian, CustodianDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<CustodianDTO> findByCriteria(CustodianCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Custodian> specification = createSpecification(criteria);
    return CustodianRepository.findAll(specification, page)
        .map(Custodian -> modelMapper.map(Custodian, CustodianDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(CustodianCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Custodian> specification = createSpecification(criteria);
    return CustodianRepository.count(specification);
  }

  public Specification<Custodian> createSpecification(CustodianCriteria criteria) {
    Specification<Custodian> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Custodian_.id));
      }
      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Custodian_.description));
      }
    }
    return specification;
  }
}
