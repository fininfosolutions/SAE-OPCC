package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Movement;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.Direction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MovementRepository
    extends JpaRepository<Movement, Long>, JpaSpecificationExecutor<Movement> {

  //   @Query(
  //       value =
  //           "select d From Movement d WHERE d.clientDescription= ?1 AND d.clientSecAccount.id= ?2
  // AND d.refMovement= ?3 AND d.direction= ?4 AND (d.reversed=false OR d.reversed IS NULL)")
  //   Optional<Movement> findByClientAndAccountAndDirection(
  //       String clientDescription, Long clientSecurityAccountId, String ref, Direction direction);
  @Query(
      "select d from Movement d "
          + "join SecuritiesAccount sa on sa.id = d.account.id "
          + "where d.account.id = ?1 "
          + "and d.reference = ?2 "
          + "and d.sens = ?3 "
          + "and d.type = 'SEC' "
          + "and (d.reversed = false or d.reversed is null)")
  Optional<Movement> findSecMovementByAccountAndDirection(
      Long accountId, String ref, Direction direction);

  //   @Query(
  //       "select d from Movement d "
  //           + "join ClientCashAccount cca on cca.id = d.account.id "
  //           + "where cca.client.id = ?1 "
  //           + "and d.account.id = ?2 "
  //           + "and d.reference = ?3 "
  //           + "and d.sens = ?4 "
  //           + "and d.type = 'CASH' "
  //           + "and (d.reversed = false or d.reversed is null)")
  //   Optional<Movement> findCashMovementByClientAndAccountAndDirection(
  //       Long clientId, Long accountId, String ref, Direction direction);

  Optional<Movement> findByReference(String refMovement);

  //   Optional<Movement> findByRefMovementAndAccountAndDirection(
  //       String refMovement, String account, Direction direction);
}
