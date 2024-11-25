package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Custodian;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import com.fininfo.saeopcc.shared.repositories.AddressRepository;
import com.fininfo.saeopcc.shared.repositories.CustodianRepository;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustodianService {

  @Autowired private CustodianRepository custodianRepository;
  @Autowired private RoleService roleService;
  @Autowired private AddressRepository addressRepo;

  public Custodian findByExternalReference(String externalReference) {

    Role roleCustodian =
        roleService.findByExternalReferenceAndActingAs(externalReference, ActingAs.CUSTODIAN);

    if (roleCustodian != null) {
      Optional<Custodian> custodian = custodianRepository.findById(roleCustodian.getId());
      if (custodian.isPresent()) {
        return custodian.get();
      } else return null;
    } else return null;
  }

  public Custodian syncCustodian(Custodian custodian) {

    Custodian synced = null;

    if (custodian.getId() != null) {
      Set<Address> addressList = custodian.getAddresses();
      if (!addressList.isEmpty()) {
        addressRepo.saveAll(addressList);
      }

      synced = custodianRepository.save(custodian);
      for (Address address : addressList) {
        address.setRole(synced);
      }
      addressRepo.saveAll(addressList);

    } else log.error("Synchronize Custodian Process Cannot Execute without ID !!!");

    return synced;
  }
}
