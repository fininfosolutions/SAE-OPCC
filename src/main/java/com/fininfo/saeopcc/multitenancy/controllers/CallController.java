package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.services.CallService;
import com.fininfo.saeopcc.multitenancy.services.dto.CallDTO;
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
public class CallController {
  private static final String ENTITY_NAME = "Appeal";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private CallService callService;
  @Autowired private CallRepository callRepository;

  @GetMapping("/call/{id}")
  public ResponseEntity<CallDTO> getCall(@PathVariable Long id) {
    log.debug("REST request to get Call : {}", id);
    Optional<CallDTO> callDTO = callService.findOne(id);
    return ResponseUtil.wrapOrNotFound(callDTO);
  }

  @PutMapping("/call/validate")
  public ResponseEntity<List<CallDTO>> validateCalls(@RequestBody List<CallDTO> callDTOs) {
    List<CallDTO> validatedCalls = callService.validatecalls(callDTOs);
    return ResponseEntity.ok(validatedCalls);
  }

  @PutMapping("/call")
  public ResponseEntity<CallDTO> updateCall(@RequestBody CallDTO callDTO)
      throws URISyntaxException {
    log.debug("REST request to update Call : {}", callDTO);
    if (callDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    Optional<Call> existingCall = callRepository.findById(callDTO.getId());
    if (existingCall.isEmpty()) {
      throw new EntityNotFoundException("callDTO with ID " + callDTO.getId() + " not found");
    }
    CallDTO result = callService.save(callDTO);
    return ResponseEntity.ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, true, ENTITY_NAME, callDTO.getId().toString()))
        .body(result);
  }

  @GetMapping("/call/byCallEvent/{id}")
  public ResponseEntity<List<CallDTO>> getEventsByIssue(@PathVariable Long id, Pageable pageable) {
    log.debug("REST request to get Events by Issue ID : {}", id);
    Page<CallDTO> page = callService.getCallsByEvent(id, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/call/count/{eventId}")
  public ResponseEntity<Long> countEventsByIssue(@PathVariable Long eventId) {
    long count = callService.countCallsByEvent(eventId);
    return ResponseEntity.ok(count);
  }
}
