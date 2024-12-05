package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.ClientSecAccount;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSecAccountRepository
    extends JpaRepository<ClientSecAccount, Long>, JpaSpecificationExecutor<ClientSecAccount> {

  ClientSecAccount findFirstByAccountNumber(String accountNumber);

  @Query(
      value =
          "select * from securities_account "
              + "where asset_id = :assetId "
              + "and shareholder_id = :shareholderId "
              + "and (:intermediaryId is null or intermediary_id = :intermediaryId) "
              + "and account_type = :accountType ",
      nativeQuery = true)
  ClientSecAccount findSecAccountBy(
      @Param("assetId") Long assetId,
      @Param("shareholderId") Long shareholderId,
      @Param("intermediaryId") Long intermediaryId,
      @Param("accountType") String accountType);

  @Query(
      value =
          "select * from securities_account "
              + "where asset_id = :assetId "
              + "and shareholder_id = :shareholderId "
              + "and (:intermediaryId is null or intermediary_id = :intermediaryId) "
              + "and account_type = :accountType "
              + "and account_category_id in (select id from public.account_category where code = :categoryCode)",
      nativeQuery = true)
  ClientSecAccount findSecAccountByWithCategory(
      @Param("assetId") Long assetId,
      @Param("shareholderId") Long shareholderId,
      @Param("intermediaryId") Long intermediaryId,
      @Param("accountType") String accountType,
      @Param("categoryCode") String categoryCode);

  ClientSecAccount
      findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountTypeAndAccountCategory_id(
          Long assetId,
          Long shareholderId,
          Long intermediaryId,
          AccountType accountType,
          Long accountCategoryId);

  List<ClientSecAccount> findByAsset_IdAndShareholder_IdAndAccountType(
      Long id, Long id2, AccountType accountType);

  List<ClientSecAccount> findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountType(
      Long id, Long id2, Long id3, AccountType accountType);

  Optional<ClientSecAccount> findByAccountNumberAndIsActiveTrue(String accountNumber);
}
