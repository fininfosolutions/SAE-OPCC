package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.IssuerRepository;
import com.fininfo.saeopcc.shared.services.dto.IssuerDTO;
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
public class IssuerRessource {
  @Autowired IssuerRepository issuerRepository;
  @Autowired private ModelMapper modelMapper;

  @GetMapping("/issuers")
  public ResponseEntity<List<IssuerDTO>> getAllissuers() {
    List<IssuerDTO> IssuerDTOs =
        issuerRepository.findAll().stream()
            .map(x -> modelMapper.map(x, IssuerDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(IssuerDTOs);
  }

  @GetMapping("/issuers/{id}")
  public ResponseEntity<IssuerDTO> getpaiement(@PathVariable Long id) {
    Optional<IssuerDTO> IssuerDTO =
        issuerRepository.findById(id).map(x -> modelMapper.map(x, IssuerDTO.class));
    return ResponseUtil.wrapOrNotFound(IssuerDTO);
  }
}
