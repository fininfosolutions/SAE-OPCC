package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.BusinessRiskCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the BusinessRiskCategory entity. */
@SuppressWarnings("unused")
@Repository
public interface BusinessRiskCategoryRepository
    extends JpaRepository<BusinessRiskCategory, Long>,
        JpaSpecificationExecutor<BusinessRiskCategory> {}
