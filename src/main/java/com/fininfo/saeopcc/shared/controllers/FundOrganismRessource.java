package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.FundOrganismRepository;
import com.fininfo.saeopcc.shared.services.dto.FundOrganismDTO;
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
public class FundOrganismRessource {
  @Autowired ModelMapper modelMapper;
  @Autowired FundOrganismRepository fundOrganismRepository;

  @GetMapping("/fund-organisms")
  public ResponseEntity<List<FundOrganismDTO>> getAllFundClassifications() {
    List<FundOrganismDTO> fundClassificationDTOs =
        fundOrganismRepository.findAll().stream()
            .map(x -> modelMapper.map(x, FundOrganismDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(fundClassificationDTOs);
  }

  @GetMapping("/fund-organisms/{id}")
  public ResponseEntity<FundOrganismDTO> getFundclassification(@PathVariable Long id) {
    Optional<FundOrganismDTO> fundClassificationDTO =
        fundOrganismRepository.findById(id).map(x -> modelMapper.map(x, FundOrganismDTO.class));
    return ResponseUtil.wrapOrNotFound(fundClassificationDTO);
  }
}
