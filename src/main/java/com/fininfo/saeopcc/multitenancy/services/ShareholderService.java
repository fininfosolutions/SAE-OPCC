package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
import com.fininfo.saeopcc.multitenancy.repositories.ShareholderRepository;
import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.enumeration.ActingAs;
import com.fininfo.saeopcc.shared.repositories.AddressRepository;
import com.fininfo.saeopcc.shared.repositories.RoleRepository;
import com.fininfo.saeopcc.shared.services.RoleService;
import com.fininfo.saeopcc.util.TenantContext;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShareholderService {

  @Autowired private ShareholderRepository shareholderRepository;
  @Autowired private RoleService roleService;
  @Autowired private RoleRepository roleRepository;
  @Autowired private AddressRepository addressRepo;

  public Shareholder findByReference(String reference) {

    Role roleShareholder = roleService.findByReferenceAndActingAs(reference, ActingAs.SHAREHOLDER);

    if (roleShareholder != null) {
      Optional<Shareholder> shareholder = shareholderRepository.findById(roleShareholder.getId());
      if (shareholder.isPresent()) {
        return shareholder.get();
      } else return null;
    } else return null;
  }

  // @Transactional
  public Shareholder syncShareholder(Shareholder shareholder, String tenant) {

    Shareholder synced = null;
    TenantContext.setTenantId(tenant);
    if (shareholder.getId() != null) {
      Set<Address> addressList = shareholder.getAddresses();
      if (!addressList.isEmpty()) {
        addressRepo.saveAll(addressList);
      }
      synced = shareholderRepository.save(shareholder);
      synced.setAddresses(shareholder.getAddresses());
      createOrUpdateAddresses(synced);

    } else log.error("Synchronize Shareholder Process Cannot Execute without ID !!!");

    return synced;
  }

  private void createOrUpdateAddresses(Shareholder shareholder) {
    Optional<Role> roleopt = roleRepository.findById(shareholder.getId());
    if (roleopt.isPresent()) {
      Set<Address> addressList = shareholder.getAddresses();
      if (!addressList.isEmpty()) {
        for (Address address : addressList) {
          address.setRole(roleopt.get());
        }
        addressRepo.saveAll(addressList);
      }
    }
  }
}
