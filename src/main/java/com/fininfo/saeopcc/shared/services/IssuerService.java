package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Issuer;
import com.fininfo.saeopcc.shared.repositories.AddressRepository;
import com.fininfo.saeopcc.shared.repositories.IssuerRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IssuerService {

  @Autowired private IssuerRepository issuerRepository;
  @Autowired private AddressRepository addressRepo;

  public Issuer syncIssuer(Issuer issuer) {

    Issuer synced = null;

    if (issuer.getId() != null) {
      Set<Address> addressList = issuer.getAddresses();
      if (!addressList.isEmpty()) {
        addressRepo.saveAll(addressList);
      }

      synced = issuerRepository.save(issuer);
      for (Address address : addressList) {
        address.setRole(synced);
      }
      addressRepo.saveAll(addressList);

    } else log.error("Synchronize Issuer Process Cannot Execute without ID !!!");

    return synced;
  }
}
