package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository
    extends JpaRepository<Provider, Long>, JpaSpecificationExecutor<Provider> {}
