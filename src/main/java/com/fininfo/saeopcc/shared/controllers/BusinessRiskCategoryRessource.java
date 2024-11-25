package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.BusinessRiskCategoryRepository;
import com.fininfo.saeopcc.shared.services.dto.BusinessRiskCategoryDTO;
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
public class BusinessRiskCategoryRessource {
  @Autowired BusinessRiskCategoryRepository businessRiskCategoryRepository;
  @Autowired ModelMapper modelMapper;

  @GetMapping("/business-risks-categories")
  public ResponseEntity<List<BusinessRiskCategoryDTO>> getAllBusinessrisks() {
    List<BusinessRiskCategoryDTO> businessRiskCategoryDTOs =
        businessRiskCategoryRepository.findAll().stream()
            .map(x -> modelMapper.map(x, BusinessRiskCategoryDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(businessRiskCategoryDTOs);
  }

  @GetMapping("/business-risks-categories/{id}")
  public ResponseEntity<BusinessRiskCategoryDTO> getBusinessRisk(@PathVariable Long id) {
    Optional<BusinessRiskCategoryDTO> businessRiskCategoryDTO =
        businessRiskCategoryRepository
            .findById(id)
            .map(x -> modelMapper.map(x, BusinessRiskCategoryDTO.class));
    return ResponseUtil.wrapOrNotFound(businessRiskCategoryDTO);
  }
}
