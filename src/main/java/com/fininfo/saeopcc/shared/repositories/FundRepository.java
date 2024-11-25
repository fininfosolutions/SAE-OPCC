package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.enumeration.UnitCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Fund entity. */
@SuppressWarnings("unused")
@Repository
public interface FundRepository extends JpaRepository<Fund, Long>, JpaSpecificationExecutor<Fund> {
  Fund getFundByIsin(String isin_);

  List<Fund> findByIsin(String isin);

  Boolean existsByIdAndUnitCategory(Long id, UnitCategory unitCategory);
}
