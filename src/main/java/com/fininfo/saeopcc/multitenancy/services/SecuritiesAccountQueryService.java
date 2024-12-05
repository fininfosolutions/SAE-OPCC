package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount_;
import com.fininfo.saeopcc.multitenancy.domains.Shareholder_;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.SecuritiesAccountCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.SecuritiesAccountDTO;
import com.fininfo.saeopcc.shared.domains.Asset_;
import com.fininfo.saeopcc.shared.domains.Intermediary_;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.JoinType;
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
public class SecuritiesAccountQueryService extends QueryService<SecuritiesAccount> {

  private final Logger log = LoggerFactory.getLogger(SecuritiesAccountQueryService.class);

  @Autowired(required = false)
  private SecuritiesAccountRepository SecuritiesAccountRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<SecuritiesAccountDTO> findByCriteria(SecuritiesAccountCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<SecuritiesAccount> specification = createSpecification(criteria);
    return SecuritiesAccountRepository.findAll(specification).stream()
        .map(SecuritiesAccount -> modelMapper.map(SecuritiesAccount, SecuritiesAccountDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<SecuritiesAccountDTO> findByCriteria(
      SecuritiesAccountCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<SecuritiesAccount> specification = createSpecification(criteria);
    return SecuritiesAccountRepository.findAll(specification, page)
        .map(SecuritiesAccount -> modelMapper.map(SecuritiesAccount, SecuritiesAccountDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(SecuritiesAccountCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<SecuritiesAccount> specification = createSpecification(criteria);
    return SecuritiesAccountRepository.count(specification);
  }

  public Specification<SecuritiesAccount> createSpecification(SecuritiesAccountCriteria criteria) {
    Specification<SecuritiesAccount> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getId(), SecuritiesAccount_.id));
      }
      if (criteria.getAccountNumber() != null) {
        specification =
            specification.and(
                buildStringSpecification(
                    criteria.getAccountNumber(), SecuritiesAccount_.accountNumber));
      }
      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getDescription(), SecuritiesAccount_.description));
      }
      if (criteria.getOpeningDate() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getOpeningDate(), SecuritiesAccount_.openingDate));
      }

      if (criteria.getAssetIsin() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getAssetIsin(),
                    root -> root.join(SecuritiesAccount_.asset, JoinType.LEFT).get(Asset_.isin)));
      }
      if (criteria.getShareholderDescription() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getShareholderDescription(),
                    root ->
                        root.join(SecuritiesAccount_.shareholder, JoinType.LEFT)
                            .get(Shareholder_.description)));
      }
      if (criteria.getIntermediaryDescription() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getIntermediaryDescription(),
                    root ->
                        root.join(SecuritiesAccount_.intermediary, JoinType.LEFT)
                            .get(Intermediary_.description)));
      }
      if (criteria.getAccountType() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getAccountType(), SecuritiesAccount_.accountType));
      }
    }
    return specification;
  }
}
