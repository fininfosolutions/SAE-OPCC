package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.LiberationDTO;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class LiberationService {
  @Autowired private LiberationRepository liberationRepository;
  @Autowired private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public Optional<LiberationDTO> findOne(Long id) {
    log.debug("Request to get Liberation : {}", id);
    return liberationRepository.findById(id).map(lib -> modelMapper.map(lib, LiberationDTO.class));
  }

  public LiberationDTO save(LiberationDTO liberationDTO) {
    Liberation liberation = modelMapper.map(liberationDTO, Liberation.class);

    Liberation savedLiberation = liberationRepository.save(liberation);
    return modelMapper.map(savedLiberation, LiberationDTO.class);
  }
}
