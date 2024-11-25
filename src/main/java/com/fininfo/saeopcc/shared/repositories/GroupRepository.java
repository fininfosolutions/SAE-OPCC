package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Group;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Group entity. */
@SuppressWarnings("unused")
@Repository
public interface GroupRepository
    extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {}
