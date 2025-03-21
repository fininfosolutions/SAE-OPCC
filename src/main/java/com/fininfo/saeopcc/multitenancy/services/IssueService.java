package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueDTO;
import com.fininfo.saeopcc.shared.domains.enumeration.IssueStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueService {
  @Autowired IssueRepository issueRepository;
  @Autowired ModelMapper modelMapper;
  @Autowired private SubscriptionRepository subscriptionRepository;

  @Transactional(readOnly = true)
  public Page<IssueDTO> getAllIssues(Pageable pageable) {
    Page<Issue> page = issueRepository.findAll(pageable);
    return page.map(issue -> modelMapper.map(issue, IssueDTO.class));
  }

  public IssueDTO save(IssueDTO issueDTO) {
    Issue issue = modelMapper.map(issueDTO, Issue.class);
    issue.setIssueStatus(IssueStatus.VALIDATED);
    issue = issueRepository.save(issue);
    return modelMapper.map(issue, IssueDTO.class);
  }

  public long countTotalIssues() {
    return issueRepository.count();
  }

  public List<IssueDTO> getByIssueAccount(Long id, Pageable pageable) {
    List<Issue> issueList = issueRepository.findAllByIssueAccount_Id(id, pageable);
    return issueList.stream().map(x -> modelMapper.map(x, IssueDTO.class)).toList();
  }

  @Transactional(readOnly = true)
  public Optional<IssueDTO> findOne(Long id) {
    return issueRepository.findById(id).map(x -> modelMapper.map(x, IssueDTO.class));
  }

  public BigDecimal getCurrentQuantity(Long issueId, Long subscriptionId) {

    Optional<Issue> issueOptional = issueRepository.findById(issueId);
    BigDecimal sommeQuantity = BigDecimal.ZERO;
    BigDecimal availbleQuantity = BigDecimal.ZERO;

    if (issueOptional.isPresent()) {
      Issue issue = issueOptional.get();
      availbleQuantity = issue.getQuantity();

      List<Subscription> subscriptions =
          subscriptionRepository.findByIssue_Id(issue.getId()).stream()
              .filter(
                  subscription ->
                      subscription.getStatus() != SubscriptionStatus.INCORRECT
                          && subscription.getStatus() != SubscriptionStatus.REJECTED)
              .collect(Collectors.toList());

      for (Subscription subs : subscriptions) {
        sommeQuantity = sommeQuantity.add(subs.getQuantity());
      }

      if (subscriptionId == null) {
        if (!subscriptions.isEmpty()) {
          availbleQuantity = issue.getQuantity().subtract(sommeQuantity);
        }
      } else {
        Optional<Subscription> subOptional = subscriptionRepository.findById(subscriptionId);
        if (subOptional.isPresent()) {
          Subscription subscription = subOptional.get();
          availbleQuantity =
              issue.getQuantity().subtract(sommeQuantity).add(subscription.getQuantity());
        }
      }
    }
    return availbleQuantity;
  }
}
