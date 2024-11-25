// package com.fininfo.saeopcc.shared.controllers;

// import com.fininfo.saeopcc.multitenancy.services.dto.IRLDTO;
// import com.fininfo.saeopcc.shared.services.UploadFileService;
// import java.util.ArrayList;
// import java.util.List;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// @RestController
// @RequestMapping("/api/v1")
// @Slf4j
// public class UploadFileRessource {

//   @Autowired private UploadFileService uploadFileService;

//   @PostMapping("/upload/irls")
//   public ResponseEntity<List<IRLDTO>> generateNotificationsFromOperationsExcel(
//       @RequestParam("inputfile") MultipartFile inputFile)
//       throws NoSuchFieldException, SecurityException {
//     List<IRLDTO> irldtos = new ArrayList<>();

//     if (inputFile == null) return ResponseEntity.badRequest().build();
//     else {
//       log.info("Process Extracting/Generating Operation Started !");
//       uploadFileService.extractFlowConfig(inputFile, irldtos);
//     }

//     return ResponseEntity.ok().body(irldtos);
//   }
// }
