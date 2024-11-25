package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.SecuritySectorRepository;
import com.fininfo.saeopcc.shared.services.dto.SecuritySectorDTO;
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
public class SecuritySectorRessource {
  @Autowired SecuritySectorRepository securitySectorRepository;
  @Autowired private ModelMapper modelMapper;

  @GetMapping("/security-sectors")
  public ResponseEntity<List<SecuritySectorDTO>> getAllSecuritySectors() {
    List<SecuritySectorDTO> SecuritySectorDTOs =
        securitySectorRepository.findAll().stream()
            .map(x -> modelMapper.map(x, SecuritySectorDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(SecuritySectorDTOs);
  }

  @GetMapping("/security-sectors/{id}")
  public ResponseEntity<SecuritySectorDTO> getsecuritysector(@PathVariable Long id) {
    Optional<SecuritySectorDTO> SecuritySectorDTO =
        securitySectorRepository.findById(id).map(x -> modelMapper.map(x, SecuritySectorDTO.class));
    return ResponseUtil.wrapOrNotFound(SecuritySectorDTO);
  }
}
