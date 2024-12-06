package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.SecPosition;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecPositionRepository
    extends JpaRepository<SecPosition, Long>, JpaSpecificationExecutor<SecPosition> {

  SecPosition findByAccount_IdAndAsset_IdAndEndDate(
      Long accountId, Long assetId, LocalDate eND_DATE);

  List<SecPosition> findByAccount_IdAndEndDateAndQuantity(
      Long accountId, LocalDate endDate, BigDecimal quantity);

  @Query(
      "SELECT d FROM SecPosition d "
          + "WHERE d.account.id = ?1 "
          + "AND d.asset.id = ?2 "
          + "AND d.endDate >= '9999-01-01'")
  Optional<SecPosition> findLastPosition(Long SecurityAccountId, Long assetId);

  @Query(
      "SELECT p FROM SecPosition p "
          + "JOIN SecuritiesAccount sa ON sa.id = p.account.id "
          + "WHERE (:shareholderId IS NULL OR sa.shareholder.id = :shareholderId) "
          + "AND (:intermediaryId IS NULL OR sa.intermediary.id = :intermediaryId) "
          + "AND (:accountId IS NULL OR p.account.id = :accountId) "
          + "AND (:assetId IS NULL OR p.asset.id = :assetId) "
          + "AND p.valueDate <= :valueDate "
          + "AND p.positionDate = (SELECT MAX(p2.positionDate) FROM SecPosition p2 "
          + "WHERE p2.account.id = p.account.id "
          + "AND p2.asset.id = p.asset.id "
          + "AND p2.valueDate <= :valueDate) "
          + "ORDER BY p.account.id, p.asset.id, p.positionDate DESC")
  List<SecPosition> findSecPositionsByValueDateAndDynamicFilters(
      @Param("shareholderId") Long shareholderId,
      @Param("intermediaryId") Long intermediaryId,
      @Param("accountId") Long accountId,
      @Param("assetId") Long assetId,
      @Param("valueDate") LocalDate valueDate);

  @Query(
      "SELECT p FROM SecPosition p "
          + "JOIN SecuritiesAccount sa ON sa.id = p.account.id "
          + "WHERE (:shareholderId IS NULL OR sa.shareholder.id = :shareholderId) "
          + "AND (:intermediaryId IS NULL OR sa.intermediary.id = :intermediaryId) "
          + "AND (:accountId IS NULL OR p.account.id = :accountId) "
          + "AND (:assetId IS NULL OR p.asset.id = :assetId) "
          + "AND p.positionDate <= :positionDate "
          + "AND p.positionDate = (SELECT MAX(p2.positionDate) FROM SecPosition p2 "
          + "WHERE p2.account.id = p.account.id "
          + "AND p2.asset.id = p.asset.id "
          + "AND p2.positionDate <= :positionDate) "
          + "ORDER BY p.account.id, p.asset.id, p.positionDate DESC")
  List<SecPosition> findSecPositionsByPositionDateAndDynamicFilters(
      @Param("shareholderId") Long shareholderId,
      @Param("intermediaryId") Long intermediaryId,
      @Param("accountId") Long accountId,
      @Param("assetId") Long assetId,
      @Param("positionDate") Instant positionDate);
}
