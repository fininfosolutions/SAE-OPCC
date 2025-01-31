package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface IssueRepository
    extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {
  Optional<Issue> findByIssueAccount(IssueAccount issueAccount);

  List<Issue> findAllByIssueAccount_Id(Long id, Pageable pageable);

  List<Issue> findByIssueAccount_Id(Long id);

  List<Issue> findByIssueAccount_IdIn(List<Long> issueAccount_id);
}
