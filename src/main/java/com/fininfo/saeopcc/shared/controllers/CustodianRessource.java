package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.CustodianRepository;
import com.fininfo.saeopcc.shared.services.CustodianQueryService;
import com.fininfo.saeopcc.shared.services.dto.CustodianCriteria;
import com.fininfo.saeopcc.shared.services.dto.CustodianDTO;
import java.util.List;
import java.util.Optional;
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

@RestController
@RequestMapping("/api/v1")
public class CustodianRessource {
  @Autowired CustodianRepository custodianRepository;
  @Autowired ModelMapper modelMapper;
  @Autowired CustodianQueryService CustodianQueryService;

  @GetMapping("/custodians")
  public ResponseEntity<List<CustodianDTO>> getAllCustodians(
      CustodianCriteria criteria, Pageable pageable) {
    Page<CustodianDTO> page = CustodianQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/custodians/{id}")
  public ResponseEntity<CustodianDTO> getCustodian(@PathVariable Long id) {
    Optional<CustodianDTO> CustodianDTO =
        custodianRepository.findById(id).map(x -> modelMapper.map(x, CustodianDTO.class));
    return ResponseUtil.wrapOrNotFound(CustodianDTO);
  }
}
