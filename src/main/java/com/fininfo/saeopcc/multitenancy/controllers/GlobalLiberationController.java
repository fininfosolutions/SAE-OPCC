package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.services.GlobalLiberationService;
import com.fininfo.saeopcc.multitenancy.services.dto.GlobalLiberationDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class GlobalLiberationController {

  @Autowired private GlobalLiberationService globalLiberationService;
  @Autowired private GlobalLiberationRepository globalLiberationRepository;
  @Autowired private ModelMapper modelMapper;

  private static final String ENTITY_NAME = "sae_event";

  @Value("${spring.application.name}")
  private String applicationName;

  @GetMapping("/global-liberations")
  public ResponseEntity<List<GlobalLiberationDTO>> getAllGlobalLiberations(
      @RequestParam Long issueId, Pageable pageable) {

    Page<GlobalLiberation> page =
        globalLiberationRepository.findByCallEventIssueId(issueId, pageable);

    List<GlobalLiberation> globalLiberationList = page.getContent();

    List<GlobalLiberationDTO> dtoList =
        globalLiberationList.stream()
            .map(x -> modelMapper.map(x, GlobalLiberationDTO.class))
            .collect(Collectors.toList());

    return ResponseEntity.ok(dtoList);
  }

  @PostMapping("/global-liberations")
  public ResponseEntity<List<GlobalLiberationDTO>> creategloballiberations(
      @RequestBody List<GlobalLiberationDTO> globalLiberationDTOs) throws URISyntaxException {

    log.debug("REST request to save multiple GlobalLiberation : {}", globalLiberationDTOs);

    for (GlobalLiberationDTO dto : globalLiberationDTOs) {
      if (dto.getId() != null) {
        throw new BadRequestAlertException(
            "A new GlobalLiberation cannot already have an ID", ENTITY_NAME, "idexists");
      }
    }
    List<GlobalLiberationDTO> results = globalLiberationService.saveAll(globalLiberationDTOs);

    return ResponseEntity.status(HttpStatus.CREATED).body(results);
  }

  @GetMapping("/global-liberations/{id}")
  public ResponseEntity<GlobalLiberationDTO> getgloballiberation(@PathVariable Long id) {
    log.debug("REST request to get GlobalLiberation : {}", id);
    Optional<GlobalLiberationDTO> globalLiberationDTO = globalLiberationService.findOne(id);
    return ResponseUtil.wrapOrNotFound(globalLiberationDTO);
  }

  @PutMapping("/global-liberations")
  public GlobalLiberationDTO updateManuelGlobalLiberation(
      @RequestBody GlobalLiberationDTO globalLiberationDTO) {
    if (globalLiberationDTO.getId() == null
        || !globalLiberationDTO.getEventStatus().equals(EventStatus.PREVALIDATED))
      throw new BadRequestAlertException(
          "This global-liberation cannot be updated", ENTITY_NAME, "invalide");

    return globalLiberationService.updateGlobalLiberation(globalLiberationDTO);
  }
}
