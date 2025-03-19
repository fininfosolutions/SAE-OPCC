package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.Origin;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.services.SubscriptionQueryService;
import com.fininfo.saeopcc.multitenancy.services.SubscriptionService;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SubscriptionController {
  @Autowired private SubscriptionService subscriptionService;
  @Autowired ModelMapper modelMapper;
  private static final String ENTITY_NAME = "sae_subscription";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private SubscriptionQueryService subscriptionQueryService;

  @PostMapping("/subscriptions")
  public ResponseEntity<SubscriptionDTO> createSubscription(
      @RequestBody SubscriptionDTO subscriptionDTO) throws URISyntaxException {
    log.debug("REST request to save Subscription : {}", subscriptionDTO);
    if (subscriptionDTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new subscription cannot already have an ID", ENTITY_NAME, "idexists");
    }
    subscriptionDTO.setOrigin(Origin.MANUAL);
    SubscriptionDTO result = subscriptionService.save(subscriptionDTO);

    return ResponseEntity.created(new URI("/api/subscriptions/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @GetMapping("/subscriptions/byIssue/{id}")
  public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByIssue(
      @PathVariable Long id,
      @RequestParam(required = false) SubscriptionStatus status,
      Pageable pageable) {

    Page<SubscriptionDTO> page = subscriptionService.getSubscriptionsByIssue(id, status, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);

    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/subscriptions/count/{issueId}")
  public ResponseEntity<Long> countSubscriptionsByIssue(
      @PathVariable Long issueId, @RequestParam(required = false) SubscriptionStatus status) {

    long count = subscriptionService.countSubscriptionsByIssue(issueId, status);
    return ResponseEntity.ok(count);
  }

  @GetMapping("/subscriptions/{id}")
  public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable Long id) {
    log.debug("REST request to get Subscription : {}", id);
    Optional<SubscriptionDTO> subDTO = subscriptionService.findOne(id);
    return ResponseUtil.wrapOrNotFound(subDTO);
  }

  @PutMapping("/subscription/manuel")
  public SubscriptionDTO updateManuelSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
    if (subscriptionDTO.getId() == null
        || !subscriptionDTO.getOrigin().equals(Origin.MANUAL)
        || !subscriptionDTO.getStatus().equals(SubscriptionStatus.PREVALIDATED))
      throw new BadRequestAlertException(
          "This subscription cannot be update", ENTITY_NAME, "invalide");

    return subscriptionService.updateManuelSubscription(subscriptionDTO);
  }

  @PutMapping("/rejectSubscriptions/{id}")
  public SubscriptionDTO updateSubscriptionStatus(@PathVariable Long id) {
    return subscriptionService.rejectSubscription(id);
  }

  @PutMapping("/subscriptions/validate")
  public List<SubscriptionDTO> updateSubscriptions(
      @RequestBody List<SubscriptionDTO> subscriptionDtos) {
    return subscriptionService.validateSubscription(subscriptionDtos);
  }

  @GetMapping("/SubscriptionByQuery")
  public ResponseEntity<List<SubscriptionDTO>> getAllSubscription(
      SubscriptionCriteria criteria, Pageable pageable) {
    log.debug("REST request to get subscription by criteria: {}", criteria);
    Page<SubscriptionDTO> page = subscriptionQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/subscriptions/count")
  public ResponseEntity<Long> getTotalSubscriptionCount(SubscriptionCriteria criteria) {
    log.debug("REST request to count subscription by criteria: {}", criteria);
    return ResponseEntity.ok().body(subscriptionQueryService.countByCriteria(criteria));
  }
}
