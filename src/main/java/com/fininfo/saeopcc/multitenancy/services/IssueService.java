package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueDTO;
import java.util.List;
import java.util.Optional;
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

  @Transactional(readOnly = true)
  public Page<IssueDTO> getAllIssues(Pageable pageable) {
    Page<Issue> page = issueRepository.findAll(pageable);
    return page.map(issue -> modelMapper.map(issue, IssueDTO.class));
  }

  public IssueDTO save(IssueDTO issueDTO) {
    Issue issue = modelMapper.map(issueDTO, Issue.class);
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
}
