package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.services.SubscriptionService;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SubscriptionController {
  @Autowired private SubscriptionService subscriptionService;

  @GetMapping("/subscriptions/{id}")
  public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsbyissueID(@PathVariable Long id) {
    List<SubscriptionDTO> subscriptions = subscriptionService.findSubscriptionsbyIssue(id);

    return ResponseEntity.ok(subscriptions);
  }
}
