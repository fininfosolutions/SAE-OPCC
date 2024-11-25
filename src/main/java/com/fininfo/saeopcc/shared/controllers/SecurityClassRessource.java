package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.SecurityClassRepository;
import com.fininfo.saeopcc.shared.services.dto.SecurityClassDTO;
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
public class SecurityClassRessource {
  @Autowired SecurityClassRepository securityClassRepository;
  @Autowired ModelMapper modelMapper;

  @GetMapping("/security-class")
  public ResponseEntity<List<SecurityClassDTO>> getAllsecclasses() {
    List<SecurityClassDTO> SecurityClassDTOs =
        securityClassRepository.findAll().stream()
            .map(x -> modelMapper.map(x, SecurityClassDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(SecurityClassDTOs);
  }

  @GetMapping("/security-class/{id}")
  public ResponseEntity<SecurityClassDTO> getSecurityclass(@PathVariable Long id) {
    Optional<SecurityClassDTO> SecurityClassDTO =
        securityClassRepository.findById(id).map(x -> modelMapper.map(x, SecurityClassDTO.class));
    return ResponseUtil.wrapOrNotFound(SecurityClassDTO);
  }
}
