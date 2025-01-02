package com.fininfo.saeopcc.multitenancy.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.services.CallEventService;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.EventDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CallEventController {

  @Autowired private CallEventService eventService;
  @Autowired private ModelMapper modelMapper;

  private static final String ENTITY_NAME = "sae_event";

  @Value("${spring.application.name}")
  private String applicationName;

  @PostMapping("/events")
  public ResponseEntity<CallEventDTO> createEvent(@RequestBody CallEventDTO eventDTO)
      throws URISyntaxException {
    log.debug("REST request to save Event : {}", eventDTO);
    if (eventDTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new event cannot already have an ID", ENTITY_NAME, "idexists");
    }
    CallEventDTO result = eventService.save(eventDTO);

    return ResponseEntity.created(new URI("/api/events/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
    log.debug("REST request to get Event : {}", id);
    Optional<EventDTO> eventDTO = eventService.findOne(id);
    return ResponseUtil.wrapOrNotFound(eventDTO);
  }

  // @PutMapping("/events")
  // public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO eventDTO) {
  //   log.debug("REST request to update Event : {}", eventDTO);
  //   if (eventDTO.getId() == null) {
  //     throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
  //   }
  //   EventDTO result = eventService.updateEvent(eventDTO);
  //   return ResponseEntity.ok()
  //       .headers(
  //           HeaderUtil.createEntityUpdateAlert(
  //               applicationName, true, ENTITY_NAME, eventDTO.getId().toString()))
  //       .body(result);
  // }

  @GetMapping("/events/byIssue/{id}")
  public ResponseEntity<List<EventDTO>> getEventsByIssue(@PathVariable Long id, Pageable pageable) {
    log.debug("REST request to get Events by Issue ID : {}", id);
    Page<EventDTO> page = eventService.getEventsByIssue(id, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/events/count/{issueId}")
  public ResponseEntity<Long> countEventsByIssue(@PathVariable Long issueId) {
    long count = eventService.countEventsByIssue(issueId);
    return ResponseEntity.ok(count);
  }

  @PutMapping("/events/validate")
  public ResponseEntity<List<CallEventDTO>> validateEvents(@RequestBody List<CallEventDTO> eventDTOs) {
    log.debug("REST request to validate Events : {}", eventDTOs);
    List<CallEventDTO> validatedEvents = eventService.validateEvents(eventDTOs);
    return ResponseEntity.ok(validatedEvents);
  }
}
