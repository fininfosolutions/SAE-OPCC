package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Intermediary;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Intermediary entity. */
@SuppressWarnings("unused")
@Repository
public interface IntermediaryRepository
    extends JpaRepository<Intermediary, Long>, JpaSpecificationExecutor<Intermediary> {
  <Option> Intermediary findByAffiliedCode(String code);

  <Option> Intermediary findByReference(String reference);

  List<Intermediary> findByAffiliedCodeNot(String code);
}
