package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.SDG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SDGRepository extends JpaRepository<SDG, Long>, JpaSpecificationExecutor<SDG> {}
