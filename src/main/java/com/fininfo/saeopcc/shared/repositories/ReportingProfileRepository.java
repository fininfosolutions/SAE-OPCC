package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.ReportingProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the ReportingProfile entity. */
@SuppressWarnings("unused")
@Repository
public interface ReportingProfileRepository
    extends JpaRepository<ReportingProfile, Long>, JpaSpecificationExecutor<ReportingProfile> {}
