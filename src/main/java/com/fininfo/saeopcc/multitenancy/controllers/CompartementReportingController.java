package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.services.CompartementReportingService;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartmentsReportingDTO;
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
public class CompartementReportingController {
  @Autowired private CompartementReportingService compartementReportingService;

  @GetMapping("/compartement-reporting")
  public ResponseEntity<Map<String, List<CompartmentsReportingDTO>>> getAllCompartmentsReporting() {
    try {
      Map<String, List<CompartmentsReportingDTO>> report =
          compartementReportingService.getAllCompartmentsReporting();
      return ResponseEntity.ok(report);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
