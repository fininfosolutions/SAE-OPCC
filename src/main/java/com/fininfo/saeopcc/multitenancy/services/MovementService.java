package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Movement;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.Direction;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.multitenancy.repositories.MovementRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.LiberationDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.MovementDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
import com.fininfo.saeopcc.shared.domains.AccountTypeSLA;
import com.fininfo.saeopcc.shared.repositories.AccountRepository;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import com.fininfo.saeopcc.shared.services.AccountTypeSLAService;
import java.time.LocalDate;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MovementService {

  @Autowired private MovementRepository movementRepository;
  @Autowired private AccountTypeSLAService accountTypeSLAService;

  @Autowired private PositionService positionService;

  @Autowired private AssetRepository assetRepository;

  @Autowired private AccountRepository accountRepository;

  @Autowired private ModelMapper modelMapper;

  public void handleMovementsAndPositionsfromsubscription(
      SubscriptionDTO subscriptionDTO, SubscriptionStatus status) {
    Movement secMovement = createMovementfromSubscription(subscriptionDTO, MovementType.SEC, false);
    positionService.createPosition(secMovement);
  }

  public void handleMovementsAndPositionsfromcall(CallDTO callDTO) {
    Movement secMovement = createMovementfromCall(callDTO, MovementType.SEC, false);
    positionService.createPosition(secMovement);
  }

  public void handleMovementsAndPositionsfromliberation(LiberationDTO liberationDTO) {
    log.debug("handleMovementsAndPositionsfromliberation : {}", liberationDTO);

    AccountTypeSLA accountTypesla =
        accountTypeSLAService.getAccountTypeSLAByOperationType(TransactionType.LIBERATION);

    Movement movement1 = new Movement();

    Optional<Movement> existingMovementOpt1 =
        movementRepository.findSecMovementByAccountAndDirection(
            liberationDTO.getSecuritiesAccountId(),
            liberationDTO.getReference(),
            accountTypesla.getSens());
    movement1 = existingMovementOpt1.orElse(new Movement());

    movement1.setAccount(
        accountRepository.findById(liberationDTO.getSecuritiesAccountId()).orElse(null));
    movement1.setType(MovementType.SEC);
    movement1.setMovementDate(LocalDate.now());
    movement1.setSens(accountTypesla.getSens());
    movement1.setDirection(accountTypesla.getTransDirection());
    movement1.setReference(liberationDTO.getReference());
    movement1.setTransactionType(TransactionType.LIBERATION);
    movement1.setInstructionId(liberationDTO.getId());
    movement1.setAsset(
        assetRepository.findById(liberationDTO.getSecuritiesAccountAssetId()).orElse(null));
    movement1.setQuantity(liberationDTO.getReleasedQuantity());

    movement1.setStatus(MovementStatus.CREATED);

    movement1 = movementRepository.save(movement1);

    positionService.createPosition(movement1);

    Movement movement2 = new Movement();

    Optional<Movement> existingMovementOpt2 =
        movementRepository.findSecMovementByAccountAndDirection(
            liberationDTO.getSecuritiesAccountId(),
            liberationDTO.getReference(),
            Direction.DELIVER);
    movement2 = existingMovementOpt2.orElse(new Movement());

    movement2.setAccount(
        accountRepository.findById(liberationDTO.getCallSecuritiesAccountId()).orElse(null));
    movement2.setType(MovementType.SEC);
    movement2.setMovementDate(LocalDate.now());
    movement2.setReference(liberationDTO.getReference());
    movement2.setTransactionType(TransactionType.LIBERATION);
    movement2.setSens(Direction.DELIVER);
    movement2.setDirection(-1);

    movement2.setInstructionId(liberationDTO.getId());
    movement2.setAsset(
        assetRepository.findById(liberationDTO.getCallSecuritiesAccountAssetId()).orElse(null));
    movement2.setQuantity(liberationDTO.getReleasedQuantity());
    movement2.setStatus(MovementStatus.CREATED);

    movement2 = movementRepository.save(movement2);
    positionService.createPosition(movement2);
  }

  public Movement createMovementfromSubscription(
      SubscriptionDTO subscriptionDTO, MovementType type, Boolean isValidated) {
    AccountTypeSLA accountTypesla =
        accountTypeSLAService.getAccountTypeSLAByOperationType(TransactionType.SUBSCRIPTION);

    Movement movement = new Movement();

    Optional<Movement> secMovementOpt =
        movementRepository.findSecMovementByAccountAndDirection(
            subscriptionDTO.getSecuritiesAccountId(),
            subscriptionDTO.getReference(),
            accountTypesla.getSens());
    movement = secMovementOpt.orElse(new Movement());
    movement.setAccount(
        accountRepository.findById(subscriptionDTO.getSecuritiesAccountId()).orElse(null));
    movement.setType(type);
    movement.setMovementDate(LocalDate.now());
    movement.setSens(accountTypesla.getSens());
    movement.setDirection(accountTypesla.getTransDirection());
    movement.setReference(subscriptionDTO.getReference());
    movement.setTransactionType(TransactionType.SUBSCRIPTION);
    movement.setInstructionId(subscriptionDTO.getId());
    movement.setAsset(
        assetRepository
            .findById(subscriptionDTO.getIssueIssueAccountIssueCompartementFundId())
            .orElse(null));
    movement.setQuantity(subscriptionDTO.getQuantity());

    if (isValidated) {
      movement.setStatus(MovementStatus.CANCELED);
    } else {
      movement.setStatus(MovementStatus.CREATED);
    }

    return movementRepository.save(movement);
  }

  public Movement createMovementfromCall(CallDTO callDTO, MovementType type, Boolean isValidated) {
    AccountTypeSLA accountTypesla =
        accountTypeSLAService.getAccountTypeSLAByOperationType(TransactionType.CALL);

    Movement movement = new Movement();

    Optional<Movement> secMovementOpt =
        movementRepository.findSecMovementByAccountAndDirection(
            callDTO.getSecuritiesAccountId(), callDTO.getReference(), accountTypesla.getSens());
    movement = secMovementOpt.orElse(new Movement());
    movement.setAccount(accountRepository.findById(callDTO.getSecuritiesAccountId()).orElse(null));
    movement.setType(type);
    movement.setMovementDate(LocalDate.now());
    movement.setSens(accountTypesla.getSens());
    movement.setDirection(accountTypesla.getTransDirection());
    movement.setReference(callDTO.getReference());
    movement.setTransactionType(TransactionType.CALL);
    movement.setInstructionId(callDTO.getId());
    movement.setAsset(assetRepository.findById(callDTO.getSecuritiesAccountAssetId()).orElse(null));
    movement.setQuantity(callDTO.getCalledQuantity());

    if (isValidated) {
      movement.setStatus(MovementStatus.CANCELED);
    } else {
      movement.setStatus(MovementStatus.CREATED);
    }

    return movementRepository.save(movement);
  }

  public Page<MovementDTO> findAll(Pageable pageable) {
    return movementRepository.findAll(pageable).map(x -> modelMapper.map(x, MovementDTO.class));
  }

  public Long countAll() {
    return movementRepository.count();
  }

  @Transactional(readOnly = true)
  public Optional<MovementDTO> findOne(Long id) {
    return movementRepository.findById(id).map(mvn -> modelMapper.map(mvn, MovementDTO.class));
  }
}
