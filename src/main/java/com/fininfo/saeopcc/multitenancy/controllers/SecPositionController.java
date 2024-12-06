package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.multitenancy.services.SecPositionService;
import com.fininfo.saeopcc.multitenancy.services.dto.SecPositionDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SecPositionController {
  @Autowired private SecPositionService secpositionService;

  @Autowired ModelMapper modelMapper;

  @GetMapping("/secpositionsbydate")
  public ResponseEntity<List<SecPositionDTO>> getSecPositionByDateAndParameters(
      @RequestParam(required = false) Long shareholderId,
      @RequestParam(required = false) Long intermediaryId,
      @RequestParam(required = false) Long accountId,
      @RequestParam LocalDate date,
      @RequestParam Boolean isValueDate,
      @RequestParam(required = false) Long assetId) {

    List<SecPositionDTO> positions =
        secpositionService.getSecPositionByDateAndParameters(
            shareholderId, intermediaryId, accountId, date, isValueDate, assetId);

    return ResponseEntity.ok(positions);
  }
}
