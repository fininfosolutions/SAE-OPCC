package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.SDG;
import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import com.fininfo.saeopcc.shared.repositories.AddressRepository;
import com.fininfo.saeopcc.shared.repositories.SDGRepository;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SDGService {
  @Autowired private SDGRepository sdgRepository;
  @Autowired private AddressRepository addressRepo;
  @Autowired private RoleService roleService;

  public SDG findByExternalReference(String externalReference) {

    Role roleSdg = roleService.findByExternalReferenceAndActingAs(externalReference, ActingAs.SDG);

    if (roleSdg != null) {
      Optional<SDG> sdg = sdgRepository.findById(roleSdg.getId());
      if (sdg.isPresent()) {
        return sdg.get();
      } else return null;
    } else return null;
  }

  public SDG syncSDG(SDG sdg) {

    SDG synced = null;

    if (sdg.getId() != null) {
      Set<Address> addressList = sdg.getAddresses();
      if (!addressList.isEmpty()) {
        addressRepo.saveAll(addressList);
      }

      synced = sdgRepository.save(sdg);
      for (Address address : addressList) {
        address.setRole(synced);
      }
      addressRepo.saveAll(addressList);

    } else log.error("Synchronize Bank Process Cannot Execute without ID !!!");

    return synced;
  }
}
