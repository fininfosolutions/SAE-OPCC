package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.services.CallEventService;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CallEventController {

  @Autowired private CallEventService calleventService;

  private static final String ENTITY_NAME = "sae_event";

  @Value("${spring.application.name}")
  private String applicationName;

  @PostMapping("/call-events")
  public ResponseEntity<CallEventDTO> createEvent(@RequestBody CallEventDTO eventDTO)
      throws URISyntaxException {
    log.debug("REST request to save Event : {}", eventDTO);
    if (eventDTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new event cannot already have an ID", ENTITY_NAME, "idexists");
    }
    CallEventDTO result = calleventService.save(eventDTO);

    return ResponseEntity.created(new URI("/api/events/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @PutMapping("/call-events/validate")
  public ResponseEntity<List<CallEventDTO>> validateCallEvents(
      @RequestBody List<CallEventDTO> callEventDTOs) {
    List<CallEventDTO> validatedEvents = calleventService.validateEvents(callEventDTOs);
    return ResponseEntity.ok(validatedEvents);
  }

  @GetMapping("/call-events/{id}")
  public ResponseEntity<CallEventDTO> getEvent(@PathVariable Long id) {
    log.debug("REST request to get Event : {}", id);
    Optional<CallEventDTO> eventDTO = calleventService.findOne(id);
    return ResponseUtil.wrapOrNotFound(eventDTO);
  }

  @GetMapping("/parameters-exceeding")
  public Boolean isAmountExceeding(
      @RequestParam Long issueId, @RequestParam(required = false) BigDecimal percentage) {
    if (percentage == null) {
      throw new IllegalArgumentException("Either 'percentage' or 'amount' must be provided.");
    }
    return calleventService.parametersexceeding(issueId, percentage);
  }

  @PutMapping("/call-events")
  public CallEventDTO updateManuelCallEvent(@RequestBody CallEventDTO callEventDTO) {
    if (callEventDTO.getId() == null
        || !callEventDTO.getEventStatus().equals(EventStatus.PREVALIDATED))
      throw new BadRequestAlertException(
          "This call-event cannot be updated", ENTITY_NAME, "invalide");

    return calleventService.updateEvent(callEventDTO);
  }

  @GetMapping("/events/byIssue/{id}")
  public ResponseEntity<List<CallEventDTO>> getEventsByIssue(
      @PathVariable Long id, Pageable pageable) {
    log.debug("REST request to get Events by Issue ID : {}", id);
    Page<CallEventDTO> page = calleventService.getEventsByIssue(id, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/events/count/{issueId}")
  public ResponseEntity<Long> countEventsByIssue(@PathVariable Long issueId) {
    long count = calleventService.countEventsByIssue(issueId);
    return ResponseEntity.ok(count);
  }
}
