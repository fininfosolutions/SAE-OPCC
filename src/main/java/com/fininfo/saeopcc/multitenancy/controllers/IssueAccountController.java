package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.IssueAccountQueryService;
import com.fininfo.saeopcc.multitenancy.services.IssueAccountService;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueAccountCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueAccountDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class IssueAccountController {
  private String applicationName;

  private static final String ENTITY_NAME = "saeOpccIssueAccountDTO";

  @Autowired private IssueAccountService issueAccountDTOService;

  @Autowired private IssueAccountRepository issueAccountDTORepository;
  @Autowired private IssueAccountQueryService issueAccountQueryService;

  @PostMapping("/issueaccounts")
  public ResponseEntity<IssueAccountDTO> createIssueAccountDTO(
      @RequestBody IssueAccountDTO issueAccountDTODTO) throws URISyntaxException {
    log.debug("REST request to save Issueaccount : {}", issueAccountDTODTO);
    if (issueAccountDTODTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new IssueAccountDTO cannot already have an ID", ENTITY_NAME, "idexists");
    }
    IssueAccountDTO result = issueAccountDTOService.save(issueAccountDTODTO);
    return ResponseEntity.created(new URI("/api/issueaccounts/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @PutMapping("/issueaccounts")
  public ResponseEntity<IssueAccountDTO> updateIssueAccountDTO(
      @RequestBody IssueAccountDTO issueAccountDTODTO) throws URISyntaxException {
    log.debug("REST request to update Right : {}", issueAccountDTODTO);
    if (issueAccountDTODTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    Optional<IssueAccount> existingIssueAccountDTOOptional =
        issueAccountDTORepository.findById(issueAccountDTODTO.getId());
    if (existingIssueAccountDTOOptional.isEmpty()) {
      throw new EntityNotFoundException(
          "IssueAccountDTO with ID " + issueAccountDTODTO.getId() + " not found");
    }
    IssueAccountDTO result = issueAccountDTOService.save(issueAccountDTODTO);
    return ResponseEntity.ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, true, ENTITY_NAME, issueAccountDTODTO.getId().toString()))
        .body(result);
  }

  @GetMapping("/issueaccounts/{id}")
  public ResponseEntity<IssueAccountDTO> getIssueAccountDTO(@PathVariable Long id) {
    log.debug("REST request to get Bond : {}", id);
    Optional<IssueAccountDTO> issueAccountDTODTO = issueAccountDTOService.findOne(id);
    return ResponseUtil.wrapOrNotFound(issueAccountDTODTO);
  }

  @GetMapping("/issueaccounts")
  public ResponseEntity<List<IssueAccountDTO>> getAllIssueAccounts(
      IssueAccountCriteria criteria, Pageable pageable) {
    log.debug("REST request to get IssueAccounts by criteria: {}", criteria);
    Page<IssueAccountDTO> page = issueAccountQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * @param criteria
   * @return
   */
  @GetMapping("/issueaccounts/count")
  public ResponseEntity<Long> countIssueAccounts(IssueAccountCriteria criteria) {
    log.debug("REST request to count Bonds by criteria: {}", criteria);
    return ResponseEntity.ok().body(issueAccountQueryService.countByCriteria(criteria));
  }

  @GetMapping("/issueaccounts/compartement/{id}")
  public ResponseEntity<IssueAccountDTO> getIssueAccountByCompartement(@PathVariable Long id) {
    log.debug("REST request to get Bond : {}", id);
    Optional<IssueAccountDTO> issueAccountDTODTO =
        issueAccountDTOService.findOneByCompartementId(id);
    return ResponseUtil.wrapOrNotFound(issueAccountDTODTO);
  }
}
