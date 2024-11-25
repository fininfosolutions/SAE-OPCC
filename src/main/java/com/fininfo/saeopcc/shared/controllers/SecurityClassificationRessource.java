package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.SecurityClassificationRepository;
import com.fininfo.saeopcc.shared.services.dto.SecurityClassificationDTO;
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
public class SecurityClassificationRessource {
  @Autowired SecurityClassificationRepository securityClassRepository;
  @Autowired ModelMapper modelMapper;

  @GetMapping("/security-classifications")
  public ResponseEntity<List<SecurityClassificationDTO>> getAllsecclassifications() {
    List<SecurityClassificationDTO> SecurityClassDTOs =
        securityClassRepository.findAll().stream()
            .map(x -> modelMapper.map(x, SecurityClassificationDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(SecurityClassDTOs);
  }

  @GetMapping("/security-classifications/{id}")
  public ResponseEntity<SecurityClassificationDTO> getSecurityclassification(
      @PathVariable Long id) {
    Optional<SecurityClassificationDTO> SecurityClassDTO =
        securityClassRepository
            .findById(id)
            .map(x -> modelMapper.map(x, SecurityClassificationDTO.class));
    return ResponseUtil.wrapOrNotFound(SecurityClassDTO);
  }
}
