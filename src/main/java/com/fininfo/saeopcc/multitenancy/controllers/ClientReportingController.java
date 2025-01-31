package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.services.ClientReportingService;
import com.fininfo.saeopcc.multitenancy.services.ExcelReportingService;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientReportingDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ClientReportingController {
  @Autowired private ExcelReportingService excelGenerationService;
  @Autowired private ClientReportingService clientReportingService;

  @GetMapping("/clients-reporting")
  public ResponseEntity<List<ClientReportingDTO>> getAllClientsReporting() {
    List<ClientReportingDTO> list = clientReportingService.getAllClientsReporting();
    return ResponseEntity.ok(list);
  }
}
