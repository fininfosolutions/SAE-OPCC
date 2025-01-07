package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.repositories.CompartementRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartementDTO;
import com.fininfo.saeopcc.util.TenantContext;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompartementService {

  @Autowired private CompartementRepository compartementRepository;
  @Autowired private ModelMapper modelMapper;

  public Compartement syncCompartement(Compartement compartement, String tenant) {

    Compartement synced = null;
    TenantContext.setTenantId(tenant);
    if (compartement.getId() != null) {
      synced = compartementRepository.save(compartement);

    } else log.error("Synchronize Compartement Process Cannot Execute without ID !!!");

    return synced;
  }

  public CompartementDTO getOne(Long id) {
    Optional<Compartement> compartementOptional = compartementRepository.findById(id);
    if (compartementOptional.isPresent()) {
      return modelMapper.map(compartementOptional.get(), CompartementDTO.class);
    }
    throw new EntityNotFoundException(" no entity found to the specific id" + id);
  }

  public List<CompartementDTO> getByClientId(Long idClient, Pageable pageable) {
    List<Compartement> compartementList =
        compartementRepository.findAllByClient_id(idClient, pageable);
    return compartementList.stream().map(x -> modelMapper.map(x, CompartementDTO.class)).toList();
  }
}
