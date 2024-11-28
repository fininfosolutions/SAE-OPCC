package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface IssueReportRepository
    extends JpaRepository<IssueReport, Long>, JpaSpecificationExecutor<IssueReport> {}
