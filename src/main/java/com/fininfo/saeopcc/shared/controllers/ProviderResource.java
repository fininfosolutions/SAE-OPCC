package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.services.ProviderQueryService;
import com.fininfo.saeopcc.shared.services.ProviderService;
import com.fininfo.saeopcc.shared.services.dto.ProviderCriteria;
import com.fininfo.saeopcc.shared.services.dto.ProviderDTO;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class ProviderResource {

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private ProviderService providerService;

  @Autowired private ProviderQueryService providerQueryService;

  @GetMapping("/providers")
  public ResponseEntity<List<ProviderDTO>> getAllProviders(
      ProviderCriteria criteria, Pageable pageable) {
    log.debug("REST request to get Providers by criteria: {}", criteria);
    Page<ProviderDTO> page = providerQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/providers/count")
  public ResponseEntity<Long> countProviders(ProviderCriteria criteria) {
    log.debug("REST request to count Providers by criteria: {}", criteria);
    return ResponseEntity.ok().body(providerQueryService.countByCriteria(criteria));
  }

  @GetMapping("/providers/{id}")
  public ResponseEntity<ProviderDTO> getProvider(@PathVariable Long id) {
    log.debug("REST request to get Provider : {}", id);
    Optional<ProviderDTO> providerDTO = providerService.findOne(id);
    return ResponseUtil.wrapOrNotFound(providerDTO);
  }
}
