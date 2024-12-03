package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.Compartement_;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount_;
import com.fininfo.saeopcc.multitenancy.domains.Issue_;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueDTO;
import com.fininfo.saeopcc.shared.domains.Asset_;
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
public class IssueQueryService extends QueryService<Issue> {

  private final Logger log = LoggerFactory.getLogger(IssueQueryService.class);

  @Autowired(required = false)
  private IssueRepository issueRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<IssueDTO> findByCriteria(IssueCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Issue> specification = createSpecification(criteria);
    List<Issue> issues = issueRepository.findAll(specification);

    return issues.stream()
        .map(issue -> modelMapper.map(issue, IssueDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<IssueDTO> findByCriteria(IssueCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Issue> specification = createSpecification(criteria);
    Page<Issue> issuePage = issueRepository.findAll(specification, page);

    return issuePage.map(issue -> modelMapper.map(issue, IssueDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(IssueCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Issue> specification = createSpecification(criteria);
    return issueRepository.count(specification);
  }

  public Specification<Issue> createSpecification(IssueCriteria criteria) {
    Specification<Issue> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Issue_.id));
      }

      if (criteria.getIssueIssueAccountIssueCompartementFundIsin() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getIssueIssueAccountIssueCompartementFundIsin(),
                    root ->
                        root.join(Issue_.issueAccount, JoinType.LEFT)
                            .join(IssueAccount_.compartement, JoinType.LEFT)
                            .join(Compartement_.fund, JoinType.LEFT)
                            .get(Asset_.isin)));
      }
      if (criteria.getIssueIssueAccountIssueCompartementFundCode() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getIssueIssueAccountIssueCompartementFundCode(),
                    root ->
                        root.join(Issue_.issueAccount, JoinType.LEFT)
                            .join(IssueAccount_.compartement, JoinType.LEFT)
                            .join(Compartement_.fund, JoinType.LEFT)
                            .get(Asset_.code)));
      }
      if (criteria.getIssueIssueAccountIssueCompartementFundDescription() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getIssueIssueAccountIssueCompartementFundDescription(),
                    root ->
                        root.join(Issue_.issueAccount, JoinType.LEFT)
                            .join(IssueAccount_.compartement, JoinType.LEFT)
                            .join(Compartement_.fund, JoinType.LEFT)
                            .get(Asset_.description)));
      }
    }
    return specification;
  }
}
