package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Counterpart;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import com.fininfo.saeopcc.shared.repositories.AddressRepository;
import com.fininfo.saeopcc.shared.repositories.CounterpartRepository;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CounterpartService {

  @Autowired private CounterpartRepository counterpartRepository;
  @Autowired private RoleService roleService;
  @Autowired private AddressRepository addressRepo;

  public Counterpart findByExternalReference(String externalReference) {

    Role roleCounterpart =
        roleService.findByExternalReferenceAndActingAs(externalReference, ActingAs.COUNTERPART);

    if (roleCounterpart != null) {
      Optional<Counterpart> counterpart = counterpartRepository.findById(roleCounterpart.getId());
      if (counterpart.isPresent()) {
        return counterpart.get();
      } else return null;
    } else return null;
  }

  public Counterpart syncCounterpart(Counterpart counterpart) {

    Counterpart synced = null;

    if (counterpart.getId() != null) {
      Set<Address> addressList = counterpart.getAddresses();
      if (!addressList.isEmpty()) {
        addressRepo.saveAll(addressList);
      }

      synced = counterpartRepository.save(counterpart);
      for (Address address : addressList) {
        address.setRole(synced);
      }
      addressRepo.saveAll(addressList);

    } else log.error("Synchronize Counterpart Process Cannot Execute without ID !!!");

    return synced;
  }
}
