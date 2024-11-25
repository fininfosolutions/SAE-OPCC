package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.FundManager;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the FundManager entity. */
@SuppressWarnings("unused")
@Repository
public interface FundManagerRepository
    extends JpaRepository<FundManager, Long>, JpaSpecificationExecutor<FundManager> {
  FundManager getFundManagerByReference(String ref);

  @Query(
      value =
          "select fm.id, rl.description, rl.definition, rl.reference, rl.external from fund_manager fm JOIN fund_organism fo ON fm.id = fo.fund_manager_id \n"
              + " JOIN fund f ON  fo.id = f.fund_organism_id JOIN role rl on fm.id = rl.id where f.id = ?1 ",
      nativeQuery = true)
  Optional<FundManager> findFundManagerOfFund(Long id);
}
