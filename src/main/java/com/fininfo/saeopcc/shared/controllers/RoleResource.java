package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.repositories.RoleRepository;
import com.fininfo.saeopcc.shared.services.RoleQueryService;
import com.fininfo.saeopcc.shared.services.dto.RoleCriteria;
import com.fininfo.saeopcc.shared.services.dto.RoleDTO;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST Resource for controlling Role resources (APIs) */
@RestController
@RequestMapping("/api/v1")
public class RoleResource {

  private final Logger log = LoggerFactory.getLogger(RoleResource.class);

  private static final String ENTITY_NAME = "referentielRole";
  private static final String CONTENT_DISPOSITION = "Content-Disposition";
  private static final String ATTACHMENT_FILENAME = "attachment;filename=";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private RoleRepository roleRepository;

  @Autowired private RoleQueryService roleQueryService;

  @Autowired private ModelMapper modelMapper;

  /**
   * {@code GET /roles} : get all the roles.
   *
   * @param pageable the pagination information.
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roles in body.
   */
  @GetMapping("/roles")
  public ResponseEntity<List<RoleDTO>> getAllRoles(RoleCriteria criteria, Pageable pageable) {
    log.debug("REST request to get Roles by criteria: {}", criteria);
    Page<RoleDTO> page = roleQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET /roles/count} : count all the roles.
   *
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
   */
  @GetMapping("/roles/count")
  public ResponseEntity<Long> countRoles(RoleCriteria criteria) {
    log.debug("REST request to count Roles by criteria: {}", criteria);
    return ResponseEntity.ok().body(roleQueryService.countByCriteria(criteria));
  }

  /**
   * {@code GET /roles/:id} : get the "id" role.
   *
   * @param id the id of the roleDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleDTO, or
   *     with status {@code 404 (Not Found)}.
   */
  @GetMapping("/roles/{id}")
  public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
    log.debug("REST request to get Role : {}", id);
    Optional<Role> role = roleRepository.findById(id);
    if (role.isPresent()) {
      return ResponseEntity.ok().body(modelMapper.map(role.get(), RoleDTO.class));
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/roles-list")
  public List<RoleDTO> getAllRolesList(RoleCriteria criteria) {
    log.debug("REST request to get Roles by criteria: {}", criteria);
    return roleQueryService.findByCriteria(criteria);
  }
}
