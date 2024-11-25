package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA repository for the {@link Authority} entity. */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
