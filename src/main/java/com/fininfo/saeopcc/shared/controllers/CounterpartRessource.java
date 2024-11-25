package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.CounterpartRepository;
import com.fininfo.saeopcc.shared.services.CounterpartQueryService;
import com.fininfo.saeopcc.shared.services.dto.CounterpartCriteria;
import com.fininfo.saeopcc.shared.services.dto.CounterpartDTO;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
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
public class CounterpartRessource {
  @Autowired CounterpartRepository counterpartRepository;
  @Autowired ModelMapper modelMapper;
  @Autowired private CounterpartQueryService CounterpartQueryService;

  @GetMapping("/counterparts")
  public ResponseEntity<List<CounterpartDTO>> getAllCounterparts(
      CounterpartCriteria criteria, Pageable pageable) {
    Page<CounterpartDTO> page = CounterpartQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/counterparts/{id}")
  public ResponseEntity<CounterpartDTO> getcounterpart(@PathVariable Long id) {
    Optional<CounterpartDTO> counterpartDTO =
        counterpartRepository.findById(id).map(x -> modelMapper.map(x, CounterpartDTO.class));
    return ResponseUtil.wrapOrNotFound(counterpartDTO);
  }
}
