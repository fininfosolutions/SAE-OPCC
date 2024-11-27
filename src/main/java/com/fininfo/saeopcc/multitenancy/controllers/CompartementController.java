package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.multitenancy.services.CompartementQueryService;
import com.fininfo.saeopcc.multitenancy.services.CompartementService;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartementCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartementDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
public class CompartementController {

  @Autowired private CompartementQueryService compartementqueryservice;
  @Autowired private CompartementService compartementService;

  @GetMapping("/compartements/without-issue-account")
  public ResponseEntity<List<CompartementDTO>> getAllCompartementswithoutissueaccount(
      CompartementCriteria criteria, Pageable pageable) {
    log.debug("REST request to get Compartements without IssueAccount by criteria: {}", criteria);
    Page<CompartementDTO> page =
        compartementqueryservice.findCompartementsWithoutIssueAccount(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/compartements/without-issue")
  public ResponseEntity<List<CompartementDTO>> getAllCompartementswithoutissue(
      CompartementCriteria criteria, Pageable pageable) {
    log.debug("REST request to get Compartements without Issue by criteria: {}", criteria);
    Page<CompartementDTO> page =
        compartementqueryservice.findCompartementsWithoutIssue(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("compatements/{id}")
  public CompartementDTO getbyId(@PathVariable("id") Long id) {
    return compartementService.getOne(id);
  }
}
