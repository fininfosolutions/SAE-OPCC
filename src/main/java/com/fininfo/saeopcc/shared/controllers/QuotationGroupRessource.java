package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.QuotationGroupRepository;
import com.fininfo.saeopcc.shared.services.dto.QuotationGroupDTO;
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
public class QuotationGroupRessource {
  @Autowired QuotationGroupRepository quotationGroupRepository;
  @Autowired private ModelMapper modelMapper;

  @GetMapping("/quotation-groups")
  public ResponseEntity<List<QuotationGroupDTO>> getAllquotationgroups() {
    List<QuotationGroupDTO> QuotationGroupDTOs =
        quotationGroupRepository.findAll().stream()
            .map(x -> modelMapper.map(x, QuotationGroupDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(QuotationGroupDTOs);
  }

  @GetMapping("/quotation-groups/{id}")
  public ResponseEntity<QuotationGroupDTO> getquotationgroup(@PathVariable Long id) {
    Optional<QuotationGroupDTO> QuotationGroupDTO =
        quotationGroupRepository.findById(id).map(x -> modelMapper.map(x, QuotationGroupDTO.class));
    return ResponseUtil.wrapOrNotFound(QuotationGroupDTO);
  }
}
