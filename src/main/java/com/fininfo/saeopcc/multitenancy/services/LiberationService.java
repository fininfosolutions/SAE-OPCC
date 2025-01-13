package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.LiberationDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class LiberationService {
  @Autowired private LiberationRepository liberationRepository;

  @Autowired private GlobalLiberationRepository globalLiberationRepository;
  @Autowired private ModelMapper modelMapper;
  @Autowired private MovementService movementService;

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

  @Transactional
  public List<LiberationDTO> validateliberations(List<LiberationDTO> liberationDTOs) {

    return liberationDTOs.stream()
        .map(
            dto -> {
              Liberation liberation =
                  liberationRepository
                      .findById(dto.getId())
                      .orElseThrow(
                          () ->
                              new EntityNotFoundException(
                                  "Liberation not found for id: " + dto.getId()));

              liberation.setStatus(LiberationStatus.VALIDATED);
              liberation = liberationRepository.save(liberation);

              movementService.handleMovementsAndPositionsfromliberation(dto);

              return modelMapper.map(liberation, LiberationDTO.class);
            })
        .collect(Collectors.toList());
  }

  public Page<LiberationDTO> getByGlobalLiberation(Long globalLiberationId, Pageable pageable) {
    Optional<GlobalLiberation> globalLiberationOpt =
        globalLiberationRepository.findById(globalLiberationId);
    if (globalLiberationOpt.isPresent()) {
      Page<Liberation> page =
          liberationRepository.findByGlobalLiberation_Id(globalLiberationId, pageable);
      return page.map(event -> modelMapper.map(event, LiberationDTO.class));
    } else {
      return new PageImpl<>(new ArrayList<>());
    }
  }

  public long countByGlobalLiberation(Long globalLiberationId) {
    return liberationRepository.countByGlobalLiberation_Id(globalLiberationId);
  }
}
