package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Appeal;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.AppealStatus;
import com.fininfo.saeopcc.multitenancy.repositories.AppealRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.AppealDTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppealService {
  @Autowired private ModelMapper modelMapper;

  @Autowired private AppealRepository appealRepository;
  @Autowired private SubscriptionRepository subscriptionRepository;

  public Boolean isAmountExceeding(Long subscriptionId, BigDecimal amount) {

    List<Appeal> appeals =
        appealRepository.findBySubscriptionIdAndAppealStatusOrderByIdDesc(
            subscriptionId, AppealStatus.VALIDATED);

    if (!appeals.isEmpty()) {
      Appeal appeal = appeals.get(0);
      return amount.compareTo(appeal.getUnfundedAmount()) > 0;
    }

    Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(subscriptionId);
    if (subscriptionOpt.isPresent()) {
      Subscription subscription = subscriptionOpt.get();
      return amount.compareTo(subscription.getAmount()) > 0;
    }

    return false;
  }

  public AppealDTO getAppealDTO(Long subscriptionId, BigDecimal percentage, BigDecimal amount) {

    List<Appeal> appeals =
        appealRepository.findBySubscriptionIdAndAppealStatusOrderByIdDesc(
            subscriptionId, AppealStatus.VALIDATED);

    AppealDTO appealDTO = new AppealDTO();

    if (!appeals.isEmpty()) {
      Appeal appeal = appeals.get(0);

      if (percentage != null) {
        fillAppealDTOFromAppeal(
            appealDTO, appeal, percentage, appeal.getUnfundedAmount().multiply(percentage));
      } else if (amount != null) {
        percentage =
            amount
                .multiply(BigDecimal.valueOf(100))
                .divide(appeal.getUnfundedAmount(), 4, RoundingMode.HALF_UP);
        fillAppealDTOFromAppeal(appealDTO, appeal, percentage, amount);
      }

    } else {
      Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(subscriptionId);
      if (subscriptionOpt.isPresent()) {
        Subscription subscription = subscriptionOpt.get();

        if (percentage != null) {
          fillAppealDTOFromSubscription(
              appealDTO, subscription, percentage, subscription.getAmount().multiply(percentage));
        } else if (amount != null) {
          percentage =
              amount
                  .multiply(BigDecimal.valueOf(100))
                  .divide(subscription.getAmount(), 4, RoundingMode.HALF_UP);
          fillAppealDTOFromSubscription(appealDTO, subscription, percentage, amount);
        }
      }
    }
    return appealDTO;
  }

  private void fillAppealDTOFromAppeal(
      AppealDTO dto, Appeal appeal, BigDecimal percentage, BigDecimal appealAmount) {
    dto.setSousAmount(appeal.getUnfundedAmount());
    dto.setSousQuantity(appeal.getUnfundedQuantity());
    dto.setPercentage(percentage);
    dto.setAppealAmount(appealAmount);
    dto.setAppealQuantity(appeal.getUnfundedQuantity().multiply(percentage));
    dto.setUnfundedAmount(appeal.getUnfundedAmount().subtract(appealAmount));
    dto.setUnfundedQuantity(appeal.getUnfundedQuantity().subtract(dto.getAppealQuantity()));
  }

  private void fillAppealDTOFromSubscription(
      AppealDTO dto, Subscription subscription, BigDecimal percentage, BigDecimal appealAmount) {
    dto.setSousAmount(subscription.getAmount());
    dto.setSousQuantity(subscription.getQuantity());
    dto.setPercentage(percentage);
    dto.setAppealAmount(appealAmount);
    dto.setAppealQuantity(subscription.getQuantity().multiply(percentage));
    dto.setUnfundedAmount(subscription.getAmount().subtract(appealAmount));
    dto.setUnfundedQuantity(subscription.getQuantity().subtract(dto.getAppealQuantity()));
  }

  public AppealDTO save(AppealDTO appealDTO) {
    Appeal appeal = modelMapper.map(appealDTO, Appeal.class);
    appeal = appealRepository.save(appeal);
    if (appeal.getId() != null) {
      appeal.setDescription(
          appeal.getId() + "-" + appeal.getSubscription().getShareholder().getDescription());
      appeal = appealRepository.save(appeal);
    }
    AppealDTO appDTO = modelMapper.map(appeal, AppealDTO.class);

    return appDTO;
  }
}
