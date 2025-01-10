package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.GlobalLiberationDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class GlobalLiberationService {
  @Autowired private ModelMapper modelMapper;
  @Autowired private CallEventRepository callEventRepository;
  @Autowired private GlobalLiberationRepository globalLiberationRepository;

  @Transactional
  public List<GlobalLiberationDTO> saveAll(List<GlobalLiberationDTO> globalLiberationDTOs) {
    List<GlobalLiberationDTO> results = new ArrayList<>();

    for (GlobalLiberationDTO dto : globalLiberationDTOs) {
      GlobalLiberation globalLiberation = modelMapper.map(dto, GlobalLiberation.class);

      globalLiberation.setEventStatus(EventStatus.PREVALIDATED);

      Optional<CallEvent> optionalCallevent = callEventRepository.findById(dto.getCallEventId());
      if (!optionalCallevent.isPresent()) {
        throw new IllegalArgumentException("GlobalLiberation without associated CallEvent");
      }

      globalLiberation = globalLiberationRepository.save(globalLiberation);

      if (globalLiberation.getId() != null) {
        String reference =
            optionalCallevent.get().getReference() + "-GL" + globalLiberation.getId();
        globalLiberation.setReference(reference);

        globalLiberation = globalLiberationRepository.save(globalLiberation);
      }

      GlobalLiberationDTO globallibDTOResult =
          modelMapper.map(globalLiberation, GlobalLiberationDTO.class);
      results.add(globallibDTOResult);
    }

    return results;
  }

  public GlobalLiberationDTO updateGlobalLiberation(GlobalLiberationDTO globalLiberationDTO) {
    GlobalLiberation globallib = modelMapper.map(globalLiberationDTO, GlobalLiberation.class);
    return modelMapper.map(globalLiberationRepository.save(globallib), GlobalLiberationDTO.class);
  }

  @Transactional(readOnly = true)
  public Optional<GlobalLiberationDTO> findOne(Long id) {
    log.debug("Request to get GlobalLiberation : {}", id);
    return globalLiberationRepository
        .findById(id)
        .map(globallib -> modelMapper.map(globallib, GlobalLiberationDTO.class));
  }
}
