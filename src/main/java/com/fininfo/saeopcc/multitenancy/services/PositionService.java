package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Movement;
import com.fininfo.saeopcc.multitenancy.domains.Position;
import com.fininfo.saeopcc.multitenancy.domains.SecPosition;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.MovementType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.PositionType;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.multitenancy.repositories.MovementRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SecPositionRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PositionService {

  private final LocalDate END_DATE = LocalDate.parse("9999-01-01");

  @Autowired private AssetRepository assetRepository;

  @Autowired private SecPositionRepository secPositionRepository;

  // @Autowired private CashPositionRepository cashPositionRepository;
  @Autowired private SubscriptionRepository subscriptionRepository;

  @Autowired private MovementRepository movementRepository;

  private Position mapperPosition(Movement movement, Position position) {

    Optional<Subscription> subscriptionOpt =
        subscriptionRepository.findById(movement.getInstructionId());
    if (movement.getTransactionType().equals(TransactionType.SUBSCRIPTION)) {
      if (subscriptionOpt.isPresent()) {
        Subscription subscription = subscriptionOpt.get();
        position.setValueDate(subscription.getSettlementDate());
      } else {
        position.setValueDate(null);
      }
    }
    position.setPositionDate(Instant.now());
    position.setPositionType(positionType(movement.getType()));
    return position;
  }

  private PositionType positionType(MovementType movementType) {
    return switch (movementType) {
      case SEC -> PositionType.SEC;
      case CASH -> PositionType.CASH;
    };
  }

  public List<Position> impacPositions(List<Movement> movements) {

    List<Position> positions = new ArrayList<>();

    movements.stream()
        .filter(mvt -> !mvt.getImpacted())
        .forEach(mvt -> positions.add(createPosition(mvt)));

    return positions;
  }

  private BigDecimal getLastPosition(Long accountId, Long assetId, PositionType type) {
    switch (type) {
      case SEC:
        SecPosition secPosition =
            secPositionRepository.findByAccount_IdAndAsset_IdAndEndDate(
                accountId, assetId, END_DATE);
        if (secPosition != null) {
          secPosition.setEndDate(LocalDate.now());
          secPosition = secPositionRepository.save(secPosition);
          return secPosition.getQuantity();
        }
        return BigDecimal.ZERO;

      case CASH:
        return BigDecimal.ZERO;
    }
    return BigDecimal.ZERO;
  }

  public Position createPosition(Movement mvt) {
    switch (mvt.getType()) {
      case SEC:
        return createSecPosition(mvt);
      case CASH:
        return null;
    }
    return null;
  }

  private Position createSecPosition(Movement mvt) {
    SecPosition secPosition = (SecPosition) mapperPosition(mvt, new SecPosition());
    secPosition.setEndDate(END_DATE);

    secPosition = secPositionRepository.save(secPosition);

    Asset asset = assetRepository.findById(mvt.getAsset().getId()).orElse(null);
    if (asset == null)
      throw new EntityNotFoundException("No Asset found with ID: " + mvt.getAsset().getId());
    else secPosition.setAsset(asset);
    secPosition.setPositionDate(Instant.now());
    secPosition.setQuantity(
        mvt.getQuantity()
            .multiply(BigDecimal.valueOf(mvt.getDirection()))
            .add(
                getLastPosition(
                    mvt.getAccount().getId(), mvt.getAsset().getId(), PositionType.SEC)));

    secPosition.setAccount(mvt.getAccount());
    secPosition.setReference(mvt.getReference());
    secPosition = secPositionRepository.save(secPosition);
    mvt.setImpacted(true);
    mvt.setStatus(MovementStatus.IMPACTED);
    mvt.setImpactedDate(LocalDate.now());
    movementRepository.save(mvt);

    return secPosition;
  }
}
