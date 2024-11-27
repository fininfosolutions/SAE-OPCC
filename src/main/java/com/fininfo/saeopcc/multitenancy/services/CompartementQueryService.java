package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.domains.Compartement_;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.repositories.CompartementRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartementCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartementDTO;
import com.fininfo.saeopcc.shared.domains.Fund_;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.JoinType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CompartementQueryService extends QueryService<Compartement> {
  @Autowired private CompartementRepository compartementRepository;
  @Autowired private IssueAccountRepository issueAccountRepository;
  @Autowired private IssueRepository issueRepository;

  @Autowired private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public Page<CompartementDTO> findCompartementsWithoutIssueAccount(
      CompartementCriteria criteria, Pageable pageable) {
    Specification<Compartement> specification = createSpecification(criteria);

    Page<Compartement> page = compartementRepository.findAll(specification, pageable);

    List<Long> compartimentIds =
        issueAccountRepository.findAll().stream()
            .map(IssueAccount::getCompartement)
            .map(Compartement::getId)
            .collect(Collectors.toList());

    List<CompartementDTO> filteredList =
        page.getContent().stream()
            .filter(compartement -> !compartimentIds.contains(compartement.getId()))
            .map(compartement -> modelMapper.map(compartement, CompartementDTO.class))
            .collect(Collectors.toList());

    return new PageImpl<>(filteredList, pageable, filteredList.size());
  }

  @Transactional(readOnly = true)
  public Page<CompartementDTO> findCompartementsWithoutIssue(
      CompartementCriteria criteria, Pageable pageable) {

    Specification<Compartement> specification = createSpecification(criteria);

    List<Long> linkedIssueAccountIds =
        issueRepository.findAll().stream()
            .map(Issue::getIssueAccount)
            .map(IssueAccount::getId)
            .collect(Collectors.toList());

    List<Long> linkedCompartementIds =
        issueAccountRepository.findAll().stream()
            .filter(issueAccount -> linkedIssueAccountIds.contains(issueAccount.getId()))
            .map(IssueAccount::getCompartement)
            .map(Compartement::getId)
            .collect(Collectors.toList());

    Page<Compartement> page = compartementRepository.findAll(specification, pageable);

    List<CompartementDTO> filteredList =
        page.getContent().stream()
            .filter(compartement -> !linkedCompartementIds.contains(compartement.getId()))
            .map(compartement -> modelMapper.map(compartement, CompartementDTO.class))
            .collect(Collectors.toList());

    return new PageImpl<>(filteredList, pageable, filteredList.size());
  }

  protected Specification<Compartement> createSpecification(CompartementCriteria criteria) {
    Specification<Compartement> specification = Specification.where(null);

    if (criteria != null) {
      if (criteria.getFundCode() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getFundCode(),
                    root -> root.join(Compartement_.fund, JoinType.LEFT).get(Fund_.code)));
      }
    }

    return specification;
  }
}
