package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.domains.Intermediary;
import com.fininfo.saeopcc.shared.repositories.IntermediaryRepository;
import com.fininfo.saeopcc.shared.services.IntermediaryQueryService;
import com.fininfo.saeopcc.shared.services.dto.IntermediaryCriteria;
import com.fininfo.saeopcc.shared.services.dto.IntermediaryDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class IntermediaryResource {

  @Autowired IntermediaryRepository proxyRepository;
  @Autowired IntermediaryQueryService intermediaryQueryService;
  @Autowired ModelMapper modelMapper;

  @GetMapping("/intermediaries")
  public ResponseEntity<List<IntermediaryDTO>> getAllintermediaryDTOs() {
    List<IntermediaryDTO> IntermediaryDTOs =
        proxyRepository.findAll().stream()
            .map(x -> modelMapper.map(x, IntermediaryDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(IntermediaryDTOs);
  }

  @GetMapping("/allintermediaries")
  public ResponseEntity<List<IntermediaryDTO>> getAllIntermediairies(
      IntermediaryCriteria criteria, Pageable pageable) {
    Page<IntermediaryDTO> page = intermediaryQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/intermediaries/{id}")
  public ResponseEntity<IntermediaryDTO> getintermediary(@PathVariable Long id) {
    Optional<IntermediaryDTO> IntermediaryDTO =
        proxyRepository.findById(id).map(x -> modelMapper.map(x, IntermediaryDTO.class));
    return ResponseUtil.wrapOrNotFound(IntermediaryDTO);
  }

  @GetMapping("/intermediaries/pur")
  public ResponseEntity<IntermediaryDTO> getPurIntermediary() {
    Intermediary intermediary = proxyRepository.findByAffiliedCode("000");
    log.info("intermediary pur : ", intermediary);
    if (intermediary != null) {
      return ResponseEntity.ok(modelMapper.map(intermediary, IntermediaryDTO.class));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/intermediaries/administrated")
  public ResponseEntity<List<IntermediaryDTO>> getAdministratedIntermediaries() {
    List<IntermediaryDTO> IntermediaryDTOs =
        proxyRepository.findByAffiliedCodeNot("000").stream()
            .map(x -> modelMapper.map(x, IntermediaryDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(IntermediaryDTOs);
  }
}
