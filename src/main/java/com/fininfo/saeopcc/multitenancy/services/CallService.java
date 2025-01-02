package com.fininfo.saeopcc.multitenancy.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.Event;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CallService {
  @Autowired private ModelMapper modelMapper;

  @Autowired private CallRepository appealRepository;
  @Autowired private SubscriptionRepository subscriptionRepository;

  public Boolean isAmountExceeding(Long subscriptionId, BigDecimal amount) {

    List<Call> appeals =
        appealRepository.findBySubscriptionIdAndCallStatusOrderByIdDesc(
            subscriptionId, CallStatus.VALIDATED);

    if (!appeals.isEmpty()) {
      Call appeal = appeals.get(0);
      return amount.compareTo(appeal.getUnfundedAmount()) > 0;
    }

    Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(subscriptionId);
    if (subscriptionOpt.isPresent()) {
      Subscription subscription = subscriptionOpt.get();
      return amount.compareTo(subscription.getAmount()) > 0;
    }

    return false;
  }

  public CallDTO getAppealDTO(Long subscriptionId, BigDecimal percentage, BigDecimal amount) {

    List<Call> appeals =
        appealRepository.findBySubscriptionIdAndCallStatusOrderByIdDesc(
            subscriptionId, CallStatus.VALIDATED);

    CallDTO appealDTO = new CallDTO();

    if (!appeals.isEmpty()) {
      Call appeal = appeals.get(0);

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
      CallDTO dto, Call appeal, BigDecimal percentage, BigDecimal appealAmount) {
    dto.setSousAmount(appeal.getUnfundedAmount());
    dto.setSousQuantity(appeal.getUnfundedQuantity());
    dto.setPercentage(percentage);
    dto.setAppealAmount(appealAmount);
    dto.setAppealQuantity(appeal.getUnfundedQuantity().multiply(percentage));
    dto.setUnfundedAmount(appeal.getUnfundedAmount().subtract(appealAmount));
    dto.setUnfundedQuantity(appeal.getUnfundedQuantity().subtract(dto.getAppealQuantity()));
  }

  private void fillAppealDTOFromSubscription(
      CallDTO dto, Subscription subscription, BigDecimal percentage, BigDecimal appealAmount) {
    dto.setSousAmount(subscription.getAmount());
    dto.setSousQuantity(subscription.getQuantity());
    dto.setPercentage(percentage);
    dto.setAppealAmount(appealAmount);
    dto.setAppealQuantity(subscription.getQuantity().multiply(percentage));
    dto.setUnfundedAmount(subscription.getAmount().subtract(appealAmount));
    dto.setUnfundedQuantity(subscription.getQuantity().subtract(dto.getAppealQuantity()));
  }

  public CallDTO save(CallDTO appealDTO) {
    Call appeal = modelMapper.map(appealDTO, Call.class);
    appeal = appealRepository.save(appeal);
    if (appeal.getId() != null) {
      appeal.setDescription(
          appeal.getId() + "-" + appeal.getSubscription().getShareholder().getDescription());
      appeal = appealRepository.save(appeal);
    }
    CallDTO appDTO = modelMapper.map(appeal, CallDTO.class);

    return appDTO;
  }

  // public void createFromEvent(Event event){

  //   List<Subscription> subscriptions=
  // subscriptionRepository.findByIssue_id(event.getIssue().getId()).stream().filter(sub->sub.getStatus().equals(SubscriptionStatus.VALIDATED)).forEach(sub->{

  //   Appeal appeal=new Appeal();

  //     appeal.setAppealAmount(sub.getAmount());
  //   appeal.setUnfundedAmount(sub.getAmount().subtract(appeal.getAppealAmount()));

  //   // // TODO: 25/12/2024  complite all champs
  //   appeal.setEvent(event);

  //   appealRepository.save(appeal);
  //   });
  // }

  public void createFromEvent(Event event) {

    Issue Issue= new Issue();
    List<Subscription> subscriptions =
        subscriptionRepository.findByIssue_id(Issue.getId()).stream()
            .filter(sub -> sub.getStatus().equals(SubscriptionStatus.VALIDATED))
            .collect(Collectors.toList());

    subscriptions.forEach(
        sub -> {
          Call appeal = new Call();

          appeal.setAppealAmount(sub.getAmount());
          appeal.setUnfundedAmount(sub.getAmount().subtract(appeal.getAppealAmount()));

         // appeal.setEvent(event);

          appealRepository.save(appeal);
        });
  }
}
