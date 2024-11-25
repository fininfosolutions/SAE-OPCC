package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.shared.services.FundService;
import com.fininfo.saeopcc.shared.services.dto.FundDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FundResource {

  @Autowired private FundService fundService;

  @GetMapping("/funds")
  public ResponseEntity<List<FundDTO>> getAllbonds() {
    List<FundDTO> fundDTOs = fundService.findAll();
    return ResponseEntity.ok(fundDTOs);
  }
}
