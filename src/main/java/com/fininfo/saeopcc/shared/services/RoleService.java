package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import com.fininfo.saeopcc.shared.repositories.RoleRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService {

  @Autowired private RoleRepository roleRepository;

  public Role syncRole(Role role) {

    Role synced = null;
    if (role.getId() != null) {
      synced = roleRepository.save(role);
    } else log.error("Synchronize Role Process Cannot Execute without ID !!!");

    return synced;
  }

  public Role findByReferenceAndActingAs(String reference, ActingAs actingAs) {

    List<Role> roleByReference = roleRepository.findByReferenceAndActingAs(reference, actingAs);

    if (!roleByReference.isEmpty()) return roleByReference.get(0);
    else return null;
  }

  public Role findByExternalReferenceAndActingAs(String externalReference, ActingAs actingAs) {

    List<Role> roleByReferenceExterne =
        roleRepository.findByExternalReferenceAndActingAs(externalReference, actingAs);

    if (!roleByReferenceExterne.isEmpty()) return roleByReferenceExterne.get(0);
    else return null;
  }
}
