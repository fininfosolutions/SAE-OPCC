package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.FiscalNatureRepository;
import com.fininfo.saeopcc.shared.services.dto.FiscalNatureDTO;
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
public class FiscalNatureRessource {
  @Autowired FiscalNatureRepository FiscalNatureRepository;
  @Autowired ModelMapper modelMapper;

  @GetMapping("/fiscal-natures")
  public ResponseEntity<List<FiscalNatureDTO>> getAllfiscs() {
    List<FiscalNatureDTO> FiscalNatureDTOs =
        FiscalNatureRepository.findAll().stream()
            .map(x -> modelMapper.map(x, FiscalNatureDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(FiscalNatureDTOs);
  }

  @GetMapping("/fiscal-natures/{id}")
  public ResponseEntity<FiscalNatureDTO> getfisc(@PathVariable Long id) {
    Optional<FiscalNatureDTO> FiscalNatureDTO =
        FiscalNatureRepository.findById(id).map(x -> modelMapper.map(x, FiscalNatureDTO.class));
    return ResponseUtil.wrapOrNotFound(FiscalNatureDTO);
  }
}
