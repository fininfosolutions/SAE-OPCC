package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventDTO;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CallEventService {

  @Autowired private CallEventRepository eventRepository;
  @Autowired private SubscriptionRepository subscriptionRepository;
  @Autowired private ModelMapper modelMapper;

  @Autowired private IssueRepository issueRepository;
  @Autowired private CallRepository callRepository;

  @Autowired private SecuritiesAccountRepository securitiesAccountRepository;

  @Transactional
  public CallEventDTO save(CallEventDTO eventDTO) {
    CallEvent event = modelMapper.map(eventDTO, CallEvent.class);
    event.setEventStatus(EventStatus.PREVALIDATED);
    Optional<Issue> optionalIssue = issueRepository.findById(eventDTO.getIssueId());
    if (!optionalIssue.isPresent()) {
      throw new IllegalArgumentException("event without issue");
    }
    event = eventRepository.save(event);

    if (event.getId() != null) {
      event.setReference("APP" + event.getId());
      event = eventRepository.save(event);
    }

    CallEventDTO eventDTOResult = modelMapper.map(event, CallEventDTO.class);

    return eventDTOResult;
  }

  @Transactional(readOnly = true)
  public Optional<CallEventDTO> findOne(Long id) {
    log.debug("Request to get Event : {}", id);
    return eventRepository.findById(id).map(event -> modelMapper.map(event, CallEventDTO.class));
  }

  @Transactional
  public List<CallEventDTO> validateEvents(List<CallEventDTO> eventDTOs) {
    List<CallEventDTO> validatedEvents = new ArrayList<>();

    for (CallEventDTO eventDTO : eventDTOs) {
      CallEvent callEvent =
          eventRepository
              .findById(eventDTO.getId())
              .orElseThrow(
                  () -> new EntityNotFoundException("Event not found for id: " + eventDTO.getId()));

      callEvent.setEventStatus(EventStatus.VALIDATED);

      callEvent = eventRepository.save(callEvent);

      CallEventDTO updatedEventDTO =
          createcallsforevent(modelMapper.map(callEvent, CallEventDTO.class));

      validatedEvents.add(updatedEventDTO);
    }

    return validatedEvents;
  }

  public CallEventDTO createcallsforevent(CallEventDTO eventDto) {
    CallEvent callEvent =
        eventRepository
            .findById(eventDto.getId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Call event not found for id: " + eventDto.getId()));
    List<Subscription> subscriptions =
        subscriptionRepository.findByIssue_IdAndStatus(
            callEvent.getIssue().getId(), SubscriptionStatus.VALIDATED);

    boolean atLeastOneIncomplete = false;

    for (Subscription subscription : subscriptions) {
      Call call = new Call();

      call.setCallEvent(callEvent);
      call.setSubscription(subscription);

      BigDecimal percentage =
          callEvent.getPercentage() != null ? callEvent.getPercentage() : BigDecimal.ZERO;
      call.setPercentage(percentage);

      BigDecimal subAmount =
          subscription.getAmount() != null ? subscription.getAmount() : BigDecimal.ZERO;
      BigDecimal calledAmount = percentage.multiply(subAmount);
      call.setCalledAmount(calledAmount);
      BigDecimal subQuantity =
          subscription.getQuantity() != null ? subscription.getQuantity() : BigDecimal.ZERO;
      BigDecimal calledQuantity = percentage.multiply(subQuantity);
      call.setCalledQuantiy(calledQuantity);
      BigDecimal remainingAmount = subAmount.subtract(calledAmount);
      call.setRemainingAmount(remainingAmount);
      BigDecimal remainingQuantity = subQuantity.subtract(calledQuantity);
      call.setRemainingQuantiy(remainingQuantity);
      call.setReference(callEvent.getReference());
      call.setDescription(callEvent.getDescription());
      call.setCallDate(callEvent.getCallDate());
      SecuritiesAccount secaccount = subscription.getSecuritiesAccount();

      List<SecuritiesAccount> accounts =
          securitiesAccountRepository
              .findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountType(
                  secaccount.getAsset() != null ? secaccount.getAsset().getId() : null,
                  subscription.getShareholder() != null
                      ? subscription.getShareholder().getId()
                      : null,
                  secaccount.getIntermediary() != null
                      ? secaccount.getIntermediary().getId()
                      : null,
                  AccountType.APPELE);

      if (accounts == null || accounts.isEmpty()) {
        call.setSecuritiesAccount(null);
        call.setMessage("Compte titre inexistant");
        call.setStatus(CallStatus.INCOMPLETE);
        callEvent.setEventStatus(EventStatus.INCOMPLETE);
        atLeastOneIncomplete = true;
      } else {
        call.setSecuritiesAccount(accounts.get(0));
        call.setStatus(CallStatus.VALIDATED);
      }
      callRepository.save(call);
    }
    if (!atLeastOneIncomplete) {
      callEvent.setEventStatus(EventStatus.VALIDATED);
    }

    CallEvent savedcallevent = eventRepository.save(callEvent);
    return modelMapper.map(savedcallevent, CallEventDTO.class);
  }

  public CallEventDTO updateEvent(CallEventDTO callEventDTO) {
    CallEvent event = modelMapper.map(callEventDTO, CallEvent.class);
    return modelMapper.map(eventRepository.save(event), CallEventDTO.class);
  }

  public Boolean parametersexceeding(Long issueId, BigDecimal percentage) {
    List<CallEvent> callevents =
        eventRepository.findByIssue_IdAndEventStatus(issueId, EventStatus.VALIDATED);

    if (callevents.isEmpty()) {
      return false;
    }

    BigDecimal totalEventPercentage =
        callevents.stream()
            .map(CallEvent::getPercentage)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal availablePercentage = BigDecimal.ONE.subtract(totalEventPercentage);

    if (percentage != null) {
      return percentage.compareTo(availablePercentage) > 0;
    }

    return false;
  }

  public Page<CallEventDTO> getEventsByIssue(Long issueId, Pageable pageable) {
    Optional<Issue> issueOpt = issueRepository.findById(issueId);
    if (issueOpt.isPresent()) {
      Page<CallEvent> page = eventRepository.findByIssue_id(issueId, pageable);
      return page.map(event -> modelMapper.map(event, CallEventDTO.class));
    } else {
      return new PageImpl<>(new ArrayList<>());
    }
  }

  public long countEventsByIssue(Long issueId) {
    return eventRepository.countByIssue_Id(issueId);
  }
}
