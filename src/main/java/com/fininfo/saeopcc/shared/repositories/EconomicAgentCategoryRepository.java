package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.EconomicAgentCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the EconomicAgentCategory entity. */
@SuppressWarnings("unused")
@Repository
public interface EconomicAgentCategoryRepository
    extends JpaRepository<EconomicAgentCategory, Long>,
        JpaSpecificationExecutor<EconomicAgentCategory> {

  Optional<EconomicAgentCategory> getEconomicAgentCategoriesById(Long id);
}
