package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.multitenancy.services.IssueQueryService;
import com.fininfo.saeopcc.multitenancy.services.IssueService;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class IssueRessource {
  private static final String ENTITY_NAME = "saeopccIssue";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private IssueService issueService;
  @Autowired private IssueQueryService issueQueryService;

  @GetMapping("/issues")
  public ResponseEntity<List<IssueDTO>> getAllIssues(Pageable pageable) {
    Page<IssueDTO> page = issueService.getAllIssues(pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @PutMapping("/issues")
  public ResponseEntity<IssueDTO> updateIssue(@RequestBody IssueDTO issueDTO)
      throws URISyntaxException {
    if (issueDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    IssueDTO result = issueService.save(issueDTO);
    return ResponseEntity.ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, true, ENTITY_NAME, issueDTO.getId().toString()))
        .body(result);
  }

  @PostMapping("/issues")
  public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO issueDTO)
      throws URISyntaxException {
    if (issueDTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new slice cannot already have an ID", ENTITY_NAME, "idexists");
    }

    IssueDTO result = issueService.save(issueDTO);

    return ResponseEntity.created(new URI("/api/issues/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @GetMapping("/IssuesByQuery")
  public ResponseEntity<List<IssueDTO>> getAllIssues(IssueCriteria criteria, Pageable pageable) {
    log.debug("REST request to get Issues by criteria: {}", criteria);
    Page<IssueDTO> page = issueQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/issues/count")
  public ResponseEntity<Long> getTotalIssuesCount(IssueCriteria criteria) {
    log.debug("REST request to count Issues by criteria: {}", criteria);
    return ResponseEntity.ok().body(issueQueryService.countByCriteria(criteria));
  }

  @GetMapping("/issues/issue-account/{id}")
  public List<IssueDTO> getByIssueAccountId(@PathVariable("id") Long id, Pageable pageable) {
    return issueService.getByIssueAccount(id, pageable);
  }
}
