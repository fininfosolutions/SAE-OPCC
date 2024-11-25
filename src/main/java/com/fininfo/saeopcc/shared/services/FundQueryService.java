package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.shared.domains.Asset_;
import com.fininfo.saeopcc.shared.domains.BusinessRiskCategory_;
import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.FundOrganism_;
import com.fininfo.saeopcc.shared.domains.Fund_;
import com.fininfo.saeopcc.shared.repositories.FundRepository;
import com.fininfo.saeopcc.shared.services.dto.FundCriteria;
import com.fininfo.saeopcc.shared.services.dto.FundDTO;
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
 * Service for executing complex queries for {@link Fund} entities in the database. The main input
 * is a {@link FundCriteria} which gets converted to {@link Specification}, in a way that all the
 * filters must apply. It returns a {@link List} of {@link FundDTO} or a {@link Page} of {@link
 * FundDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FundQueryService extends QueryService<Fund> {

  private final Logger log = LoggerFactory.getLogger(FundQueryService.class);

  @Autowired(required = false)
  private FundRepository fundRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  /*
   * public FundQueryService(FundRepository fundRepository, FundMapper fundMapper)
   * { this.fundRepository = fundRepository; this.fundMapper = fundMapper; }
   */

  /**
   * Return a {@link List} of {@link FundDTO} which matches the criteria from the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<FundDTO> findByCriteria(FundCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Fund> specification = createSpecification(criteria);
    return fundRepository.findAll(specification).stream()
        .map(x -> modelMapper.map(x, FundDTO.class))
        .collect(Collectors.toList());
  }

  /**
   * Return a {@link Page} of {@link FundDTO} which matches the criteria from the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @param page The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<FundDTO> findByCriteria(FundCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Fund> specification = createSpecification(criteria);
    return fundRepository.findAll(specification, page).map(x -> modelMapper.map(x, FundDTO.class));
  }

  /**
   * Return the number of matching entities in the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(FundCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Fund> specification = createSpecification(criteria);
    return fundRepository.count(specification);
  }

  /**
   * Function to convert {@link FundCriteria} to a {@link Specification}
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching {@link Specification} of the entity.
   */
  public Specification<Fund> createSpecification(FundCriteria criteria) {
    Specification<Fund> specification = Specification.where(null);
    if (criteria != null) {
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Asset_.id));
      }
      if (criteria.getReference() != null && specification != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getReference(), Asset_.reference));
      }

      if (criteria.getPromoter() != null && specification != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getPromoter(), Fund_.promoter));
      }
      if (criteria.getMaturityDate() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getMaturityDate(), Fund_.maturityDate));
      }
      if (criteria.getKnownNAV() != null && specification != null) {
        specification =
            specification.and(buildSpecification(criteria.getKnownNAV(), Fund_.knownNAV));
      }
      if (criteria.getHigherLimitCommercialFundSensibility() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getHigherLimitCommercialFundSensibility(),
                    Fund_.higherLimitCommercialFundSensibility));
      }
      if (criteria.getLowerLimitCommercialFundSensibility() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getLowerLimitCommercialFundSensibility(),
                    Fund_.lowerLimitCommercialFundSensibility));
      }
      if (criteria.getInvestMoreThanFivePercent() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getInvestMoreThanFivePercent(), Fund_.investMoreThanFivePercent));
      }
      if (criteria.getDayNAV() != null && specification != null) {
        specification = specification.and(buildSpecification(criteria.getDayNAV(), Fund_.dayNAV));
      }
      if (criteria.getFrequencyNAV() != null && specification != null) {
        specification =
            specification.and(buildSpecification(criteria.getFrequencyNAV(), Fund_.frequencyNAV));
      }
      if (criteria.getNetAssetValueAtIssuing() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getNetAssetValueAtIssuing(), Fund_.netAssetValueAtIssuing));
      }
      if (criteria.getRightExitAmount() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getRightExitAmount(), Fund_.rightExitAmount));
      }
      if (criteria.getRightEnterAmount() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getRightEnterAmount(), Fund_.rightEnterAmount));
      }
      if (criteria.getRightEnterPercentage() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getRightEnterPercentage(), Fund_.rightEnterPercentage));
      }
      if (criteria.getRightExitPercentage() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getRightExitPercentage(), Fund_.rightExitPercentage));
      }
      if (criteria.getFundSensibilityLowerLimit() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getFundSensibilityLowerLimit(), Fund_.fundSensibilityLowerLimit));
      }
      if (criteria.getFundSensibilityHigherLimit() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getFundSensibilityHigherLimit(), Fund_.fundSensibilityHigherLimit));
      }
      if (criteria.getSubscriptionTerm() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getSubscriptionTerm(), Fund_.subscriptionTerm));
      }
      if (criteria.getRefundTerm() != null && specification != null) {
        specification =
            specification.and(buildRangeSpecification(criteria.getRefundTerm(), Fund_.refundTerm));
      }
      if (criteria.getSettlementSubscriptionTerm() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getSettlementSubscriptionTerm(), Fund_.settlementSubscriptionTerm));
      }
      if (criteria.getPriceDivisionFactor() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getPriceDivisionFactor(), Fund_.priceDivisionFactor));
      }
      if (criteria.getPriceNumber() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getPriceNumber(), Fund_.priceNumber));
      }
      if (criteria.getDecimalizationUnity() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getDecimalizationUnity(), Fund_.decimalizationUnity));
      }
      if (criteria.getMaxLimitByXOA() != null && specification != null) {
        specification =
            specification.and(
                buildRangeSpecification(criteria.getMaxLimitByXOA(), Fund_.maxLimitByXOA));
      }
      if (criteria.getFundClassification() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getFundClassification(), Fund_.fundClassification));
      }
      if (criteria.getFundOrganismId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getFundOrganismId(),
                    root -> root.join(Fund_.fundOrganism, JoinType.LEFT).get(FundOrganism_.id)));
      }
      if (criteria.getFundType() != null && specification != null) {
        specification =
            specification.and(buildSpecification(criteria.getFundType(), Fund_.fundType));
      }
      if (criteria.getBusinessRiskCategoryId() != null && specification != null) {
        specification =
            specification.and(
                buildSpecification(
                    criteria.getBusinessRiskCategoryId(),
                    root ->
                        root.join(Fund_.businessRiskCategory, JoinType.LEFT)
                            .get(BusinessRiskCategory_.id)));
      }
      if (criteria.getSecurityForm() != null) {
        specification =
            specification.and(buildSpecification(criteria.getSecurityForm(), Asset_.securityForm));
      }
      if (criteria.getDetentionForm() != null) {
        specification =
            specification.and(
                buildSpecification(criteria.getDetentionForm(), Asset_.detentionForm));
      }

      if (criteria.getMarketType() != null) {
        specification =
            specification.and(buildSpecification(criteria.getMarketType(), Asset_.marketType));
      }

      if (criteria.getPremiumDate() != null) {
        specification =
            specification.and(
                buildLocalDateSpecification(criteria.getPremiumDate(), Asset_.premiumDate));
      }

      if (criteria.getIsin() != null && specification != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getIsin(), Asset_.isin));
      }
      if (criteria.getCode() != null && specification != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getCode(), Asset_.code));
      }
      if (criteria.getDescription() != null && specification != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Asset_.description));
      }
      if (criteria.getAditionalDescription() != null && specification != null) {
        specification =
            specification.and(
                buildStringSpecification(
                    criteria.getAditionalDescription(), Asset_.aditionalDescription));
      }

      if (criteria.getActive() != null && specification != null) {
        specification = specification.and(buildSpecification(criteria.getActive(), Asset_.active));
      }

      if (criteria.getShortCode() != null && specification != null) {
        specification =
            specification.and(buildStringSpecification(criteria.getShortCode(), Asset_.shortCode));
      }
    }
    return specification;
  }
}
