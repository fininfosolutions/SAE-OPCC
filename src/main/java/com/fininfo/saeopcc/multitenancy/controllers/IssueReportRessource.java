package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.services.IssueReportService;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueReportDTO;
import com.fininfo.saeopcc.shared.services.dto.AssetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class IssueReportRessource {

  @Autowired private IssueReportService admissionService;

  @GetMapping("/admissionletters/{IssueAccountDTOId}")
  public ResponseEntity<IssueReportDTO> generateAdmissionLetter(
      @PathVariable Long issueAccountDTOId) {
    IssueReportDTO admissionLetterDTO = admissionService.generateIssueReport(issueAccountDTOId);
    return ResponseEntity.ok(admissionLetterDTO);
  }

  @PostMapping("/generateletterpdf")
  public ResponseEntity<byte[]> generatePdf(@RequestBody AssetDTO assetDTO) {
    byte[] pdfContent = admissionService.generateIssueReportPdf(assetDTO);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header("Content-Disposition", "inline; filename=admission_letter.pdf")
        .body(pdfContent);
  }
}
