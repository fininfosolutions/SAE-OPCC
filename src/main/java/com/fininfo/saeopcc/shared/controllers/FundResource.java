package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import com.fininfo.saeopcc.shared.services.FundService;
import com.fininfo.saeopcc.shared.services.dto.FundDTO;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FundResource {
  private static final String ENTITY_NAME = "saeopccFund";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private FundService fundService;

  @GetMapping("/funds")
  public ResponseEntity<List<FundDTO>> getAllbonds() {
    List<FundDTO> fundDTOs = fundService.findAll();
    return ResponseEntity.ok(fundDTOs);
  }

  @GetMapping("/funds/{id}")
  public ResponseEntity<FundDTO> getFund(@PathVariable Long id) {
    log.debug("REST request to get Fund : {}", id);
    Optional<FundDTO> fundDTO = fundService.findOne(id);
    return ResponseUtil.wrapOrNotFound(fundDTO);
  }

  @PutMapping("/funds")
  public ResponseEntity<FundDTO> updateFund(@RequestBody FundDTO fundDTO)
      throws URISyntaxException {
    log.debug("REST request to update Fund : {}", fundDTO);

    if (fundDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }

    if (fundDTO.getIsin() != null && !fundDTO.getIsin().isEmpty()) {
      log.debug("Request to update ISIN for Fund ID: {}", fundDTO.getId());
      fundService.updateIsin(fundDTO.getId(), fundDTO.getIsin());
      return ResponseEntity.ok()
          .headers(
              HeaderUtil.createEntityUpdateAlert(
                  applicationName, true, ENTITY_NAME, fundDTO.getId().toString()))
          .build();
    }

    return ResponseEntity.ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, true, ENTITY_NAME, fundDTO.getId().toString()))
        .body(fundDTO);
  }
}
