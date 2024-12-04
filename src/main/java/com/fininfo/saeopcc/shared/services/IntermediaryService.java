package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Intermediary;
import com.fininfo.saeopcc.shared.repositories.AddressRepository;
import com.fininfo.saeopcc.shared.repositories.IntermediaryRepository;
import com.fininfo.saeopcc.shared.services.dto.IntermediaryDTO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link Intermediary}. */
@Slf4j
@Service
public class IntermediaryService {
  @Autowired private IntermediaryRepository intermediaryRepository;
  @Autowired private AddressRepository addressRepo;
  @Autowired private ModelMapper modelMapper;

  public Intermediary syncIntermediary(Intermediary intermediary) {

    Intermediary synced = null;

    if (intermediary.getId() != null) {
      Set<Address> addressList = intermediary.getAddresses();
      if (!addressList.isEmpty()) {
        addressRepo.saveAll(addressList);
      }

      synced = intermediaryRepository.save(intermediary);
      for (Address address : addressList) {
        address.setRole(synced);
      }
      addressRepo.saveAll(addressList);

    } else log.error("Synchronize Intermediary Process Cannot Execute without ID !!!");

    return synced;
  }

  public List<IntermediaryDTO> getAllIntermediaries() {

    return intermediaryRepository.findAll().stream()
        .map(x -> modelMapper.map(x, IntermediaryDTO.class))
        .collect(Collectors.toList());
  }

  public Intermediary findByCode(String code) {
    return intermediaryRepository.findByAffiliedCode(code);
  }

  public Intermediary findByReference(String reference) {
    return intermediaryRepository.findByReference(reference);
  }
}
