package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.services.ClientReportingService;
import com.fininfo.saeopcc.multitenancy.services.ExcelReportingService;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientReportingDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartmentsReportingDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.ShareholderReportingDTO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ExcelReportingController {
  @Autowired private ExcelReportingService excelGenerationService;
  @Autowired private ClientReportingService clientReportingService;

  @PostMapping("/excel/opcc")
  public ResponseEntity<byte[]> ClientExcel(
      @RequestBody List<ClientReportingDTO> clientReportings) {
    try {
      byte[] excelFile = excelGenerationService.generateOpccReportingExcel(clientReportings);

      String filename =
          "opccParFonds_"
              + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
              + ".xlsx";

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Disposition", "attachment; filename=" + filename);

      return ResponseEntity.ok().headers(headers).body(excelFile);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/excel/opcc-by-compartement")
  public ResponseEntity<byte[]> CompartementExcel(
      @RequestBody Map<String, List<CompartmentsReportingDTO>> clientData) {
    try {
      Map<String, byte[]> excelFiles =
          excelGenerationService.generateOpccByCompartementExcelFiles(clientData);
      Map.Entry<String, byte[]> firstEntry = excelFiles.entrySet().iterator().next();

      String fileName =
          firstEntry.getKey()
              + "_"
              + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
              + ".xlsx";
      byte[] excelFile = firstEntry.getValue();

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Disposition", "attachment; filename=" + fileName);

      return ResponseEntity.ok().headers(headers).body(excelFile);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/excel/opcc-by-shareholder")
  public ResponseEntity<byte[]> ShareholderExcel(
      @RequestBody
          Map<String, Map<String, Map<String, ShareholderReportingDTO[]>>> shareholderData) {
    try {
      byte[] excelFile = excelGenerationService.generateShareholderReportingExcel(shareholderData);

      String filename =
          "shareholderReporting_"
              + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
              + ".xlsx";

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Disposition", "attachment; filename=" + filename);

      return ResponseEntity.ok().headers(headers).body(excelFile);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @FunctionalInterface
  private interface ExcelFileGenerator {
    byte[] generate() throws IOException;
  }
}
