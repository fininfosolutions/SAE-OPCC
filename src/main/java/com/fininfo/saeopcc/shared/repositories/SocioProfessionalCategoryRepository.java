package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.SocioProfessionalCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the SocioProfessionalCategory entity. */
@SuppressWarnings("unused")
@Repository
public interface SocioProfessionalCategoryRepository
    extends JpaRepository<SocioProfessionalCategory, Long>,
        JpaSpecificationExecutor<SocioProfessionalCategory> {}
