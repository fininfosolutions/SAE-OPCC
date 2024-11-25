package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Role entity. */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

  List<Role> findByReferenceAndActingAs(String reference, ActingAs actingAs);

  Role getRoleByReference(String s);

  List<Role> findByExternalReferenceAndActingAs(String externalReference, ActingAs actingAs);
}
