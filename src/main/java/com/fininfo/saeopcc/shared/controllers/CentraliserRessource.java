package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.CentraliserRepository;
import com.fininfo.saeopcc.shared.services.dto.CentraliserDTO;
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
public class CentraliserRessource {
  @Autowired CentraliserRepository centraliserRepository;
  @Autowired ModelMapper modelMapper;

  @GetMapping("/centralisers")
  public ResponseEntity<List<CentraliserDTO>> getAllcentralisers() {
    List<CentraliserDTO> CentraliserDTOs =
        centraliserRepository.findAll().stream()
            .map(x -> modelMapper.map(x, CentraliserDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(CentraliserDTOs);
  }

  @GetMapping("/centralisers/{id}")
  public ResponseEntity<CentraliserDTO> getcentraliser(@PathVariable Long id) {
    Optional<CentraliserDTO> CentraliserDTO =
        centraliserRepository.findById(id).map(x -> modelMapper.map(x, CentraliserDTO.class));
    return ResponseUtil.wrapOrNotFound(CentraliserDTO);
  }
}
