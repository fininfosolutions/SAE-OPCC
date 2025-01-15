package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.services.SummaryService;
import com.fininfo.saeopcc.multitenancy.services.dto.SummaryDTO;
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
public class SummaryController {
  @Autowired private SummaryService summaryService;

  @GetMapping("/summary/{issueId}")
  public ResponseEntity<SummaryDTO> getSummary(@PathVariable Long issueId) {
    SummaryDTO summaryDTO = summaryService.getSummary(issueId);
    return ResponseEntity.ok(summaryDTO);
  }
}
