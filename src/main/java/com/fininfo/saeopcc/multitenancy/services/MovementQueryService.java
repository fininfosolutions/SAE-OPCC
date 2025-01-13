package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.multitenancy.domains.Movement;
import com.fininfo.saeopcc.multitenancy.domains.Movement_;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.repositories.MovementRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.MovementCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.MovementDTO;
import com.fininfo.saeopcc.shared.domains.Account_;
import com.fininfo.saeopcc.shared.domains.Asset_;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MovementQueryService extends QueryService<Movement> {

  private final Logger log = LoggerFactory.getLogger(MovementQueryService.class);

  @Autowired(required = false)
  private MovementRepository movementRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Autowired private SecuritiesAccountRepository securitiesAccountRepository;

  @Transactional(readOnly = true)
  public List<MovementDTO> findByCriteria(MovementCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Movement> specification = createSpecification(criteria);
    return movementRepository.findAll(specification).stream()
        .map(movement -> modelMapper.map(movement, MovementDTO.class))
        .collect(Collectors.toList());
  }

  //  @Transactional(readOnly = true)
  // public Page<MovementDTO> findByCriteria(MovementCriteria criteria, Pageable page) {
  //   log.debug("find by criteria : {}, page: {}", criteria, page);
  //   final Specification<Movement> specification = createSpecification(criteria);
  //   return movementRepository
  //       .findAll(specification, page)
  //       .map(movement -> modelMapper.map(movement, MovementDTO.class));
  // }

  @Transactional(readOnly = true)
  public Page<MovementDTO> findByCriteria(MovementCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);

    final Specification<Movement> specification = createSpecification(criteria);
    return movementRepository
        .findAll(specification, page)
        .map(
            movement -> {
              MovementDTO movementDTO = modelMapper.map(movement, MovementDTO.class);
              if (movement.getInstructionId() != null) {
                Optional<SecuritiesAccount> secAccount =
                    securitiesAccountRepository.findById(movement.getAccount().getId());
                secAccount.ifPresent(
                    sec -> {
                      if (sec.getShareholder() != null) {
                        movementDTO.setClientDescription(sec.getShareholder().getDescription());
                      }
                      if (sec.getAccountType() != null) {
                        movementDTO.setAccountAccountType(sec.getAccountType());
                      }
                    });
              }
              return movementDTO;
            });
  }

  @Transactional(readOnly = true)
  public long countByCriteria(MovementCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Movement> specification = createSpecification(criteria);
    return movementRepository.count(specification);
  }

  /** Create a specification from MovementCriteria to query the database. */
  public Specification<Movement> createSpecification(MovementCriteria criteria) {
    Specification<Movement> specification = Specification.where(null);
    if (criteria != null) {

      // Handle ID
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Movement_.id));
      }

      // Handle quantity
      if (criteria.getQuantity() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getQuantity(), Movement_.quantity));
      }

      // Handle reference
      if (criteria.getReference() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getReference(), Movement_.reference));
      }

      // Handle transactionType
      if (criteria.getTransactionType() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getTransactionType(), Movement_.transactionType));
      }

      // Handle status
      if (criteria.getStatus() != null) {
        specification =
            specification.and(buildSpecification(criteria.getStatus(), Movement_.status));
      }

      // Handle sens (Direction)
      if (criteria.getSens() != null) {
        specification = specification.and(buildSpecification(criteria.getSens(), Movement_.sens));
      }

      // Handle MovementType
      if (criteria.getType() != null) {
        specification = specification.and(buildSpecification(criteria.getType(), Movement_.type));
      }

      // Handle amount
      if (criteria.getAmount() != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getAmount(), Movement_.amount));
      }

      // Handle dates
      if (criteria.getMovementDate() != null) {
        specification =
            specification.and(
                buildLocalDateSpecification(criteria.getMovementDate(), Movement_.movementDate));
      }

      if (criteria.getImpactedDate() != null) {
        specification =
            specification.and(
                buildLocalDateSpecification(criteria.getImpactedDate(), Movement_.impactedDate));
      }

      if (criteria.getAccountNumber() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getAccountNumber(),
                    root ->
                        root.join(Movement_.account, JoinType.LEFT).get(Account_.accountNumber)));
      }

      if (criteria.getAssetId() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getAssetId(),
                    root -> root.join(Movement_.asset, JoinType.LEFT).get(Asset_.id)));
      }
      if (criteria.getAssetCode() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getAssetCode(),
                    root -> root.join(Movement_.asset, JoinType.LEFT).get(Asset_.code)));
      }

      if (criteria.getIsin() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getIsin(),
                    root -> root.join(Movement_.asset, JoinType.LEFT).get(Asset_.isin)));
      }
      if (criteria.getShareholderDescription() != null) {
        specification =
            specification.and(
                (root, query, builder) -> {
                  From<Movement, SecuritiesAccount> sAccJoin =
                      builder.treat(
                          root.join(Movement_.account, JoinType.LEFT), SecuritiesAccount.class);

                  Path<String> descPath =
                      sAccJoin.join("shareholder", JoinType.LEFT).<String>get("description");

                  StringFilter filter = criteria.getShareholderDescription();

                  if (filter.getContains() != null) {
                    return builder.like(
                        builder.lower(descPath), "%" + filter.getContains().toLowerCase() + "%");
                  }
                  if (filter.getEquals() != null) {
                    return builder.equal(descPath, filter.getEquals());
                  }
                  if (filter.getNotEquals() != null) {
                    return builder.notEqual(descPath, filter.getNotEquals());
                  }
                  return builder.conjunction();
                });
      }
    }
    return specification;
  }
}
