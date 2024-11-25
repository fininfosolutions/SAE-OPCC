package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Provider;
import com.fininfo.saeopcc.shared.repositories.AddressRepository;
import com.fininfo.saeopcc.shared.repositories.ProviderRepository;
import com.fininfo.saeopcc.shared.services.dto.ProviderDTO;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProviderService {

  @Autowired private ProviderRepository providerRepository;

  @Autowired private ModelMapper modelMapper;
  @Autowired private AddressRepository addressRepo;

  public Provider syncPrice(Provider provider) {
    Provider synced = null;
    if (provider.getId() != null) {
      synced = providerRepository.save(provider);
    } else log.error("Provider Synchronising Failed, Please Check Price ID Existance!!!");

    return synced;
  }

  public Optional<ProviderDTO> findOne(Long id) {
    log.debug("Request to get Provider : {}", id);
    return providerRepository
        .findById(id)
        .map(entity -> modelMapper.map(entity, ProviderDTO.class));
  }

  public Provider syncProvider(Provider provider) {

    Provider synced = null;

    if (provider.getId() != null) {
      Set<Address> addressList = provider.getAddresses();
      if (!addressList.isEmpty()) {
        addressRepo.saveAll(addressList);
      }

      synced = providerRepository.save(provider);
      for (Address address : addressList) {
        address.setRole(synced);
      }
      addressRepo.saveAll(addressList);

    } else log.error("Synchronize provider Process Cannot Execute without ID !!!");

    return synced;
  }
}
