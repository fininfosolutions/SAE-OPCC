package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Correspondent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Correspondent entity. */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentRepository
    extends JpaRepository<Correspondent, Long>, JpaSpecificationExecutor<Correspondent> {}
