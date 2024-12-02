package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.repositories.ShareholderRepository;
import com.fininfo.saeopcc.multitenancy.services.ShareholderQueryService;
import com.fininfo.saeopcc.multitenancy.services.dto.ShareholderCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.ShareholderDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
public class ShareholderController {

  @Autowired private ShareholderRepository shareholderRepository;
  @Autowired private ShareholderQueryService shareholderQueryService;

  @Autowired ModelMapper modelMapper;

  @GetMapping("/shareholders")
  public ResponseEntity<List<ShareholderDTO>> getAllshareholders() {
    List<ShareholderDTO> ShareholderDTOs =
        shareholderRepository.findAll().stream()
            .map(x -> modelMapper.map(x, ShareholderDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(ShareholderDTOs);
  }

  @GetMapping("/allshareholders")
  public ResponseEntity<List<ShareholderDTO>> getAllShareholders(
      ShareholderCriteria criteria, Pageable pageable) {
    Page<ShareholderDTO> page = shareholderQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ShareholderDTO> getshareholder(@PathVariable Long id) {
    Optional<ShareholderDTO> ShareholderDTO =
        shareholderRepository.findById(id).map(x -> modelMapper.map(x, ShareholderDTO.class));
    return ResponseUtil.wrapOrNotFound(ShareholderDTO);
  }

  @GetMapping("/allshareholders/withOr")
  public ResponseEntity<List<ShareholderDTO>> getAllCustodiansWithOr(
      @RequestParam("reference") String ref,
      @RequestParam("description") String des,
      Pageable pageable) {
    Page<ShareholderDTO> page =
        shareholderRepository
            .getAllByReferenceContainsOrDescriptionContains(ref, des, pageable)
            .map(x -> modelMapper.map(x, ShareholderDTO.class));
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }
}
