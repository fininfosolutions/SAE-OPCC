package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.ExcelContentType;
import com.fininfo.saeopcc.multitenancy.services.UploadFileService;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UploadFileController {
  @Autowired private UploadFileService uploadFileService;

  @PostMapping("/upload/subscriptions")
  public ResponseEntity<List<SubscriptionDTO>> generateNotificationsFromSubscriptionsExcel(
      @RequestParam("inputfile") MultipartFile inputFile,
      @RequestParam("fileType") ExcelContentType fileType)
      throws NoSuchFieldException, SecurityException {
    List<SubscriptionDTO> subscriptionsDTOs = new ArrayList<>();

    if (inputFile == null) return ResponseEntity.badRequest().build();
    else {
      log.info("Process Extracting/Generating Subscriptions Started !");
      uploadFileService.extractFlowConfig(inputFile, subscriptionsDTOs, fileType);
    }

    return ResponseEntity.ok().body(subscriptionsDTOs);
  }
}
