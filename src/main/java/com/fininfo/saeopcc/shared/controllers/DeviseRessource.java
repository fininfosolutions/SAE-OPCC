package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.DeviseRepository;
import com.fininfo.saeopcc.shared.services.dto.DeviseDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DeviseRessource {

  @Autowired DeviseRepository deviseRepository;

  @Autowired ModelMapper modelMapper;

  @GetMapping("/devises")
  public ResponseEntity<List<DeviseDTO>> getAllDevises() {

    List<DeviseDTO> DeviseDTOs =
        deviseRepository.findAll().stream()
            .map(x -> modelMapper.map(x, DeviseDTO.class))
            .collect(Collectors.toList());

    return ResponseEntity.ok(DeviseDTOs);
  }

  @GetMapping("/devises/{id}")
  public ResponseEntity<DeviseDTO> getDevise(@PathVariable Long id) {

    Optional<DeviseDTO> deviseDTO =
        deviseRepository.findById(id).map(x -> modelMapper.map(x, DeviseDTO.class));

    return ResponseUtil.wrapOrNotFound(deviseDTO);
  }

  @GetMapping("/alldevises")
  public ResponseEntity<List<DeviseDTO>> getDevises() {
    List<DeviseDTO> devisesDTOs =
        deviseRepository.findAll().stream()
            .map(x -> modelMapper.map(x, DeviseDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(devisesDTOs);
  }
}
