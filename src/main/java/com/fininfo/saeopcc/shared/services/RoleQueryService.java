package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.shared.domains.Address_;
import com.fininfo.saeopcc.shared.domains.EconomicAgentCategory_;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.domains.Role_;
import com.fininfo.saeopcc.shared.domains.SocioProfessionalCategory_;
import com.fininfo.saeopcc.shared.repositories.RoleRepository;
import com.fininfo.saeopcc.shared.services.dto.RoleCriteria;
import com.fininfo.saeopcc.shared.services.dto.RoleDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.JoinType;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for executing complex queries for {@link Role} entities in the database. The main input
 * is a {@link RoleCriteria} which gets converted to {@link Specification}, in a way that all the
 * filters must apply. It returns a {@link List} of {@link RoleDTO} or a {@link Page} of {@link
 * RoleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoleQueryService extends QueryService<Role> {

  private final Logger log = LoggerFactory.getLogger(RoleQueryService.class);

  @Autowired(required = false)
  private RoleRepository roleRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  /*
   * public RoleQueryService(RoleRepository roleRepository, RoleMapper roleMapper)
   * { this.roleRepository = roleRepository; this.roleMapper = roleMapper; }
   */

  /**
   * Return a {@link List} of {@link RoleDTO} which matches the criteria from the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<RoleDTO> findByCriteria(RoleCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Role> specification = createSpecification(criteria);
    return roleRepository.findAll(specification).stream()
        .map(x -> modelMapper.map(x, RoleDTO.class))
        .collect(Collectors.toList());
  }

  /**
   * Return a {@link Page} of {@link RoleDTO} which matches the criteria from the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @param page The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<RoleDTO> findByCriteria(RoleCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Role> specification = createSpecification(criteria);
    return roleRepository.findAll(specification, page).map(x -> modelMapper.map(x, RoleDTO.class));
  }

  /**
   * Return the number of matching entities in the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(RoleCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Role> specification = createSpecification(criteria);
    return roleRepository.count(specification);
  }

  /**
   * Function to convert {@link RoleCriteria} to a {@link Specification}
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching {@link Specification} of the entity.
   */
  protected Specification<Role> createSpecification(RoleCriteria criteria) {
    Specification<Role> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Role_.id));
      }
      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Role_.description));
      }
      if (criteria.getActingAs() != null && specification != null) {
        specification =
            specification.and(buildSpecification(criteria.getActingAs(), Role_.actingAs));
      }
      if (criteria.getDefinition() != null && specification != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getDefinition(), Role_.definition));
      }
      if (criteria.getReference() != null && specification != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getReference(), Role_.reference));
      }
      // if (criteria.getExternal() != null && specification != null) {
      // specification =
      // specification.and(buildSpecification(criteria.getExternal(),
      // Role_.external));
      // }
      if (criteria.getEconomicAgentCategoryId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getEconomicAgentCategoryId(),
                    root ->
                        root.join(Role_.economicAgentCategory, JoinType.LEFT)
                            .get(EconomicAgentCategory_.id)));
      }
      if (criteria.getSocioProfessionalCategoryId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getSocioProfessionalCategoryId(),
                    root ->
                        root.join(Role_.socioProfessionalCategory, JoinType.LEFT)
                            .get(SocioProfessionalCategory_.id)));
      }
      if (criteria.getAddressId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getAddressId(),
                    root -> root.join(Role_.addresses, JoinType.LEFT).get(Address_.id)));
      }
    }
    return specification;
  }
}
