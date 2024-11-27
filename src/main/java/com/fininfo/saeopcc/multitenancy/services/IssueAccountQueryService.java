package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount_;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueAccountCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueAccountDTO;
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
public class IssueAccountQueryService extends QueryService<IssueAccount> {

  private final Logger log = LoggerFactory.getLogger(IssueAccountQueryService.class);

  @Autowired(required = false)
  private IssueAccountRepository issueAccountRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<IssueAccountDTO> findByCriteria(IssueAccountCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<IssueAccount> specification = createSpecification(criteria);
    List<IssueAccount> issueAccounts = issueAccountRepository.findAll(specification);
    return issueAccounts.stream()
        .map(issueAccount -> modelMapper.map(issueAccount, IssueAccountDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<IssueAccountDTO> findByCriteria(IssueAccountCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<IssueAccount> specification = createSpecification(criteria);
    Page<IssueAccount> issueAccountPage = issueAccountRepository.findAll(specification, page);
    return issueAccountPage.map(
        issueAccount -> modelMapper.map(issueAccount, IssueAccountDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(IssueAccountCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<IssueAccount> specification = createSpecification(criteria);
    return issueAccountRepository.count(specification);
  }

  public Specification<IssueAccount> createSpecification(IssueAccountCriteria criteria) {
    Specification<IssueAccount> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getId(), IssueAccount_.id));
      }

      if (criteria.getDescription() != null && specification != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), IssueAccount_.description));
      }
      if (criteria.getValueDescription() != null && specification != null) {
        specification =
            specification.and(
                buildStringSpecification(
                    criteria.getValueDescription(), IssueAccount_.valueDescription));
      }

      if (criteria.getSecuritiesquantity() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getSecuritiesquantity(), IssueAccount_.securitiesquantity));
      }
      if (criteria.getSecuritiesquantity() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getSecuritiesquantity(), IssueAccount_.securitiesquantity));
      }
      if (criteria.getReference() != null && specification != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getReference(), IssueAccount_.reference));
      }
      if (criteria.getActif() != null && specification != null) {
        specification =
            specification.and(buildSpecification(criteria.getActif(), IssueAccount_.actif));
      }
      if (criteria.getIssueaccountNumber() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getIssueaccountNumber(), IssueAccount_.issueaccountNumber));
      }
    }
    return specification;
  }
}
