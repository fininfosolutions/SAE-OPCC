package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.shared.domains.flow.Document;
import com.fininfo.saeopcc.shared.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DocumentResource {

  private final DocumentService documentService;

  public DocumentResource(DocumentService documentService) {
    this.documentService = documentService;
  }

  @GetMapping("/documents/{reference}")
  public ResponseEntity<byte[]> documentById(@PathVariable String reference) {
    log.debug("REST request to get Document by Reference: {}", reference);
    Document document = documentService.findByReference(reference);
    return document != null
        ? ResponseEntity.ok()
            .header("Accept", "text/plain")
            .header("Content-Disposition", "attachment; filename=\"" + document.getTitle() + "\"")
            .body(document.getContent())
        : ResponseEntity.notFound().build();
  }

  @GetMapping("/documents/filename/{reference}")
  public ResponseEntity<String> documentfilenameById(@PathVariable String reference) {
    log.debug("REST request to get Document filename by Reference: {}", reference);
    Document document = documentService.findByReference(reference);
    return document != null
        ? ResponseEntity.ok().body(document.getTitle())
        : ResponseEntity.notFound().build();
  }
}
