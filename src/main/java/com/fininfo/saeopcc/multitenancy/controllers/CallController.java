package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.multitenancy.services.CallService;
import com.fininfo.saeopcc.multitenancy.services.dto.CallDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CallController {
  private static final String ENTITY_NAME = "Appeal";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private CallService appealService;

  // @GetMapping("/calculate-appeal")
  // public CallDTO getAppeal(
  //     @RequestParam Long subscriptionId,
  //     @RequestParam(required = false) BigDecimal percentage,
  //     @RequestParam(required = false) BigDecimal amount) {

  //   if (percentage == null && amount == null) {
  //     throw new IllegalArgumentException("Either 'percentage' or 'amount' must be provided.");
  //   }
  //   return appealService.getAppealDTO(subscriptionId, percentage, amount);
  // }

  // @GetMapping("/isamount-exceeding")
  // public Boolean isAmountExceeding(
  //     @RequestParam Long subscriptionId, @RequestParam BigDecimal amount) {

  //   return appealService.isAmountExceeding(subscriptionId, amount);
  // }

  @PostMapping("/appeals")
  public ResponseEntity<CallDTO> createAppeal(@RequestBody CallDTO appealDTO)
      throws URISyntaxException {
    if (appealDTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new appeal cannot already have an ID", ENTITY_NAME, "idexists");
    }

    CallDTO result = appealService.save(appealDTO);

    return ResponseEntity.created(new URI("/api/appeals/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }
}
