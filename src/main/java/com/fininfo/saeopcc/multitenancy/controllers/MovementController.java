package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.services.MovementQueryService;
import com.fininfo.saeopcc.multitenancy.services.MovementService;
import com.fininfo.saeopcc.multitenancy.services.dto.MovementCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.MovementDTO;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MovementController {

  @Autowired private MovementService movementService;
  @Autowired private MovementQueryService movementQueryService;
  ;

  //   @PostMapping("/movement")
  //   public MovementDTO createQuantityMovement(@RequestBody IRLDTO irlDTO) {
  //     MovementDTO result = movementService.movementInsert(irlDTO, false, false);
  //     return result;
  //   }

  @GetMapping("/movementAllQuery")
  public ResponseEntity<List<MovementDTO>> getAllMovements(
      MovementCriteria criteria, Pageable pageable) {
    log.debug("REST request to get Movement by criteria: {}", criteria);
    Page<MovementDTO> page = movementQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/movement/count")
  public ResponseEntity<Object> getTotalMovementCount(MovementCriteria criteria) {
    log.debug("REST request to count Movement by criteria: {}", criteria);
    return ResponseEntity.ok().body(movementQueryService.countByCriteria(criteria));
  }

  @GetMapping("/movement/{id}")
  public ResponseEntity<MovementDTO> getIrl(@PathVariable Long id) {
    Optional<MovementDTO> movementDTO = movementService.findOne(id);
    return ResponseUtil.wrapOrNotFound(movementDTO);
  }

  @GetMapping("/movements")
  public ResponseEntity<List<MovementDTO>> getAllSecAccounts(Pageable pageable) {
    Page<MovementDTO> page = movementService.findAll(pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/movements/count")
  public ResponseEntity<Long> countAll() {
    Long count = movementService.countAll();
    return ResponseEntity.ok().body(count);
  }
}
