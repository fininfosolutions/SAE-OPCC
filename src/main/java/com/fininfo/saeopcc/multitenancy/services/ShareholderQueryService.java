package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
import com.fininfo.saeopcc.multitenancy.domains.Shareholder_;
import com.fininfo.saeopcc.multitenancy.repositories.ShareholderRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.ShareholderCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.ShareholderDTO;
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
public class ShareholderQueryService extends QueryService<Shareholder> {

  private final Logger log = LoggerFactory.getLogger(ShareholderQueryService.class);

  @Autowired(required = false)
  private ShareholderRepository ShareholderRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<ShareholderDTO> findByCriteria(ShareholderCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Shareholder> specification = createSpecification(criteria);
    return ShareholderRepository.findAll(specification).stream()
        .map(Shareholder -> modelMapper.map(Shareholder, ShareholderDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<ShareholderDTO> findByCriteria(ShareholderCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Shareholder> specification = createSpecification(criteria);
    return ShareholderRepository.findAll(specification, page)
        .map(Shareholder -> modelMapper.map(Shareholder, ShareholderDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(ShareholderCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Shareholder> specification = createSpecification(criteria);
    return ShareholderRepository.count(specification);
  }

  public Specification<Shareholder> createSpecification(ShareholderCriteria criteria) {
    Specification<Shareholder> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getId(), Shareholder_.id));
      }
      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Shareholder_.description));
      }
      if (criteria.getReference() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getReference(), Shareholder_.reference));
      }
    }
    return specification;
  }
}
