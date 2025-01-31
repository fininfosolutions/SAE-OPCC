package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.services.ShareholderReportingService;
import com.fininfo.saeopcc.multitenancy.services.dto.ShareholderReportingDTO;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ShareholderReportingController {
  @Autowired private ShareholderReportingService shareholderReportingService;

  @GetMapping("/shareholder-report")
  public ResponseEntity<Map<String, Map<String, Map<String, List<ShareholderReportingDTO>>>>>
      generateShareholderReport() {
    try {
      Map<String, Map<String, Map<String, List<ShareholderReportingDTO>>>> report =
          shareholderReportingService.generateShareholderReport();
      return ResponseEntity.ok(report);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
