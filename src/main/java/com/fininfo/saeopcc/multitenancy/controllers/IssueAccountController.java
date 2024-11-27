package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.IssueAccountService;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueAccountDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class IssueAccountController {
  private String applicationName;

  private static final String ENTITY_NAME = "saeOpccIssueAccountDTO";

  @Autowired private IssueAccountService issueAccountDTOService;

  @Autowired private IssueAccountRepository issueAccountDTORepository;

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
}
