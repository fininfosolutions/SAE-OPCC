package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.shared.domains.Provider;
import com.fininfo.saeopcc.shared.domains.Provider_;
import com.fininfo.saeopcc.shared.repositories.ProviderRepository;
import com.fininfo.saeopcc.shared.services.dto.ProviderCriteria;
import com.fininfo.saeopcc.shared.services.dto.ProviderDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ProviderQueryService extends QueryService<Provider> {

  @Autowired(required = false)
  private ProviderRepository providerRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<ProviderDTO> findByCriteria(ProviderCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Provider> specification = createSpecification(criteria);
    return providerRepository.findAll(specification).stream()
        .map(provider -> modelMapper.map(provider, ProviderDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<ProviderDTO> findByCriteria(ProviderCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Provider> specification = createSpecification(criteria);
    return providerRepository
        .findAll(specification, page)
        .map(provider -> modelMapper.map(provider, ProviderDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(ProviderCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Provider> specification = createSpecification(criteria);
    return providerRepository.count(specification);
  }

  /**
   * Function to convert {@link ProviderCriteria} to a {@link Specification}
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching {@link Specification} of the entity.
   */
  protected Specification<Provider> createSpecification(ProviderCriteria criteria) {
    Specification<Provider> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null && specification != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Provider_.id));
      }
      if (criteria.getName() != null && specification != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getName(), Provider_.name));
      }
    }
    return specification;
  }
}
