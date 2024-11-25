package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.CSDRepository;
import com.fininfo.saeopcc.shared.services.dto.CSDDTO;
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
public class CSDRessource {
  @Autowired CSDRepository cSDRepository;
  @Autowired ModelMapper modelMapper;

  @GetMapping("/csds")
  public ResponseEntity<List<CSDDTO>> getAllcsds() {
    List<CSDDTO> cSDDTOs =
        cSDRepository.findAll().stream()
            .map(x -> modelMapper.map(x, CSDDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(cSDDTOs);
  }

  @GetMapping("/csds/{id}")
  public ResponseEntity<CSDDTO> getcsd(@PathVariable Long id) {
    Optional<CSDDTO> cSDDTO = cSDRepository.findById(id).map(x -> modelMapper.map(x, CSDDTO.class));
    return ResponseUtil.wrapOrNotFound(cSDDTO);
  }
}
