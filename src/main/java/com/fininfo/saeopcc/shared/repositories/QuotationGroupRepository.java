package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.QuotationGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the QuotationGroup entity. */
@SuppressWarnings("unused")
@Repository
public interface QuotationGroupRepository
    extends JpaRepository<QuotationGroup, Long>, JpaSpecificationExecutor<QuotationGroup> {}
