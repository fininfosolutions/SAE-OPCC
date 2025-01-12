package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.services.LiberationService;
import com.fininfo.saeopcc.multitenancy.services.dto.LiberationDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LiberationController {
  private static final String ENTITY_NAME = "Liberation";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private LiberationRepository liberationRepository;
  @Autowired private LiberationService liberationService;

  @PutMapping("/liberation")
  public ResponseEntity<LiberationDTO> updateLiberation(@RequestBody LiberationDTO liberationDTO)
      throws URISyntaxException {
    log.debug("REST request to update Liberation : {}", liberationDTO);
    if (liberationDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    Optional<Liberation> existingLiberation = liberationRepository.findById(liberationDTO.getId());
    if (existingLiberation.isEmpty()) {
      throw new EntityNotFoundException(
          "liberationDTO with ID " + liberationDTO.getId() + " not found");
    }
    LiberationDTO result = liberationService.save(liberationDTO);
    return ResponseEntity.ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, true, ENTITY_NAME, liberationDTO.getId().toString()))
        .body(result);
  }

  @GetMapping("/liberation/{id}")
  public ResponseEntity<LiberationDTO> getLiberation(@PathVariable Long id) {
    log.debug("REST request to get Liberation : {}", id);
    Optional<LiberationDTO> liberationDTO = liberationService.findOne(id);
    return ResponseUtil.wrapOrNotFound(liberationDTO);
  }

  @GetMapping("/liberation/byGlobalLiberation/{id}")
  public ResponseEntity<List<LiberationDTO>> getLiberationsByEvent(
      @PathVariable Long id, Pageable pageable) {
    log.debug("REST request to get liberations by Issue ID : {}", id);
    Page<LiberationDTO> page = liberationService.getByGlobalLiberation(id, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/liberation/count/{globalLiberationId}")
  public ResponseEntity<Long> countEventsByEvent(@PathVariable Long globalLiberationId) {
    long count = liberationService.countByGlobalLiberation(globalLiberationId);
    return ResponseEntity.ok(count);
  }
}
