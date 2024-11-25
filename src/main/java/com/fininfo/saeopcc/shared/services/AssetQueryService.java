package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.domains.Asset_;
import com.fininfo.saeopcc.shared.domains.CSD_;
import com.fininfo.saeopcc.shared.domains.Centraliser_;
import com.fininfo.saeopcc.shared.domains.Custodian_;
import com.fininfo.saeopcc.shared.domains.FiscalNature_;
import com.fininfo.saeopcc.shared.domains.Issuer_;
import com.fininfo.saeopcc.shared.domains.QuotationGroup_;
import com.fininfo.saeopcc.shared.domains.SecuritySector_;
import com.fininfo.saeopcc.shared.domains.SecurityType_;
import com.fininfo.saeopcc.shared.domains.SettlementType_;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import com.fininfo.saeopcc.shared.services.dto.AssetCriteria;
import com.fininfo.saeopcc.shared.services.dto.AssetDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
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
 * Service for executing complex queries for {@link Asset} entities in the database. The main input
 * is a {@link AssetCriteria} which gets converted to {@link Specification}, in a way that all the
 * filters must apply. It returns a {@link List} of {@link AssetDTO} or a {@link Page} of {@link
 * AssetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetQueryService extends QueryService<Asset> {

  private final Logger log = LoggerFactory.getLogger(AssetQueryService.class);

  @Autowired private AssetRepository assetRepository;

  @Autowired private ModelMapper modelMapper;
  @Autowired private EntityManager em;

  // public AssetQueryService(EntityManager em) {
  // this.em = em;
  // }

  /**
   * Return a {@link List} of {@link AssetDTO} which matches the criteria from the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<AssetDTO> findByCriteria(AssetCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Asset> specification = createSpecification(criteria);
    return assetRepository.findAll(specification).stream()
        .map(x -> modelMapper.map(x, AssetDTO.class))
        .collect(Collectors.toList());
  }

  /**
   * Return a {@link Page} of {@link AssetDTO} which matches the criteria from the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @param page The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<AssetDTO> findByCriteria(AssetCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Asset> specification = createSpecification(criteria);
    return assetRepository
        .findAll(specification, page)
        .map(x -> modelMapper.map(x, AssetDTO.class));
  }

  /**
   * Return the number of matching entities in the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(AssetCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Asset> specification = createSpecification(criteria);
    return assetRepository.count(specification);
  }

  /**
   * Function to convert {@link AssetCriteria} to a {@link Specification}
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching {@link Specification} of the entity.
   */
  public Specification<Asset> createSpecification(AssetCriteria criteria) {
    Specification<Asset> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Asset_.id));
      }

      if (criteria.getIsin() != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getIsin(), Asset_.isin));
      }
      if (criteria.getIsinNotNull() != null && criteria.getIsinNotNull()) {
        specification = specification.and((root, query, cb) -> cb.isNotNull(root.get(Asset_.isin)));
      }

      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Asset_.description));
      }
      if (criteria.getShortCode() != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getShortCode(), Asset_.shortCode));
      }
      if (criteria.getAditionalDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(
                    criteria.getAditionalDescription(), Asset_.aditionalDescription));
      }
      if (criteria.getActive() != null) {
        specification = specification.and(buildSpecification(criteria.getActive(), Asset_.active));
      }
      if (criteria.getDeactivationDate() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getDeactivationDate(), Asset_.deactivationDate));
      }
      if (criteria.getSecuritiesInCirculationNumber() != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getSecuritiesInCirculationNumber(),
                    Asset_.securitiesInCirculationNumber));
      }
      if (criteria.getPremiumDate() != null) {
        specification =
            specification.and(
                buildLocalDateSpecification(criteria.getPremiumDate(), Asset_.premiumDate));
      }
      if (criteria.getDematerialized() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getDematerialized(), Asset_.dematerialized));
      }
      if (criteria.getInscriMaroclear() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getInscriMaroclear(), Asset_.inscriMaroclear));
      }

      if (criteria.getTaxExemption() != null) {
        specification =
            specification.and(buildSpecification(criteria.getTaxExemption(), Asset_.taxExemption));
      }
      if (criteria.getListed() != null) {
        specification = specification.and(buildSpecification(criteria.getListed(), Asset_.listed));
      }
      if (criteria.getCanceled() != null) {
        specification =
            specification.and(buildSpecification(criteria.getCanceled(), Asset_.canceled));
      }
      if (criteria.getCancellationDate() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getCancellationDate(), Asset_.cancellationDate));
      }
      if (criteria.getQuotationFirstDate() != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getQuotationFirstDate(), Asset_.quotationFirstDate));
      }
      if (criteria.getAssetType() != null) {
        specification =
            specification.and(buildSpecification(criteria.getAssetType(), Asset_.assetType));
      }
      if (criteria.getCode() != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getCode(), Asset_.code));
      }
      if (criteria.getNominalValue() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getNominalValue(), Asset_.nominalValue));
      }
      if (criteria.getCurrentNominalValue() != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getCurrentNominalValue(), Asset_.currentNominalValue));
      }
      if (criteria.getIssuePremium() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getIssuePremium(), Asset_.issuePremium));
      }
      if (criteria.getAmountPremium() != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getAmountPremium(), Asset_.amountPremium));
      }
      if (criteria.getAmountOutstandingPremium() != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getAmountOutstandingPremium(), Asset_.amountOutstandingPremium));
      }
      if (criteria.getQuotationGroupId() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getQuotationGroupId(),
                    root ->
                        root.join(Asset_.quotationGroup, JoinType.LEFT).get(QuotationGroup_.id)));
      }
      if (criteria.getMarketType() != null) {
        specification =
            specification.and(buildSpecification(criteria.getMarketType(), Asset_.marketType));
      }
      if (criteria.getBusinessRisk() != null) {
        specification =
            specification.and(buildSpecification(criteria.getBusinessRisk(), Asset_.businessRisk));
      }
      if (criteria.getFiscalNatureId() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getFiscalNatureId(),
                    root -> root.join(Asset_.fiscalNature, JoinType.LEFT).get(FiscalNature_.id)));
      }
      if (criteria.getSecurityForm() != null) {
        specification =
            specification.and(buildSpecification(criteria.getSecurityForm(), Asset_.securityForm));
      }
      if (criteria.getQuantityType() != null) {
        specification =
            specification.and(buildSpecification(criteria.getQuantityType(), Asset_.quantityType));
      }
      if (criteria.getSecurityTypeId() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getSecurityTypeId(),
                    root -> root.join(Asset_.securityType, JoinType.LEFT).get(SecurityType_.id)));
      }
      if (criteria.getQuotationPriceMode() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getQuotationPriceMode(), Asset_.quotationPriceMode));
      }
      if (criteria.getSettlementTypeId() != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getSettlementTypeId(),
                    root ->
                        root.join(Asset_.settlementType, JoinType.LEFT).get(SettlementType_.id)));
      }
      if (criteria.getSecuritySectorId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getSecuritySectorId(),
                    root ->
                        root.join(Asset_.securitySector, JoinType.LEFT).get(SecuritySector_.id)));
      }
      if (criteria.getCustodianId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getCustodianId(),
                    root -> root.join(Asset_.custodian, JoinType.LEFT).get(Custodian_.id)));
      }
      if (criteria.getCentraliserId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getCentraliserId(),
                    root -> root.join(Asset_.centraliser, JoinType.LEFT).get(Centraliser_.id)));
      }

      if (criteria.getLegalStatus() != null && specification != null) {
        specification =
            specification.and(buildSpecification(criteria.getLegalStatus(), Asset_.legalStatus));
      }
      if (criteria.getIssuerId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getIssuerId(),
                    root -> root.join(Asset_.issuer, JoinType.LEFT).get(Issuer_.id)));
      }

      if (criteria.getCsdId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getCsdId(),
                    root -> root.join(Asset_.csd, JoinType.LEFT).get(CSD_.id)));
      }
    }
    return specification;
  }
}
