package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.SummaryDTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SummaryService {
  @Autowired private IssueRepository issueRepository;
  @Autowired private CallEventRepository callEventRepository;
  @Autowired private CallRepository callRepository;
  @Autowired private SubscriptionRepository subscriptionRepository;
  @Autowired private GlobalLiberationRepository globalLiberationRepository;
  @Autowired private LiberationRepository liberationRepository;

  public SummaryDTO getSummary(Long issueId) {

    Issue issue =
        issueRepository
            .findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found with id " + issueId));

    List<CallEvent> validatedCallEvents =
        callEventRepository.findByIssue_IdAndEventStatus(issueId, EventStatus.VALIDATED);

    List<Long> callEventIds =
        validatedCallEvents.stream().map(CallEvent::getId).collect(Collectors.toList());

    List<Call> validatedCalls = new ArrayList<>();
    if (!callEventIds.isEmpty()) {
      validatedCalls =
          callRepository.findByCallEventIdInAndStatus(callEventIds, CallStatus.VALIDATED);
    }

    BigDecimal calledAmount =
        validatedCalls.stream()
            .map(Call::getCalledAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal calledQuantity =
        validatedCalls.stream()
            .map(Call::getCalledQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal notCalledAmount =
        validatedCalls.stream()
            .map(Call::getRemainingAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal notCalledQuantity =
        validatedCalls.stream()
            .map(Call::getRemainingQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal sumCallEventPercentages =
        validatedCallEvents.stream()
            .map(CallEvent::getPercentage)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .multiply(BigDecimal.valueOf(100));

    BigDecimal notCalledPercentage = BigDecimal.valueOf(100).subtract(sumCallEventPercentages);

    BigDecimal sumCallEventCalledAmount =
        validatedCallEvents.stream()
            .map(CallEvent::getCalledAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal sumCallEventCalledQuantity =
        validatedCallEvents.stream()
            .map(CallEvent::getCalledQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal issueAmount = issue.getAmount() != null ? issue.getAmount() : BigDecimal.ZERO;
    BigDecimal issueQuantity = issue.getQuantity() != null ? issue.getQuantity() : BigDecimal.ZERO;

    BigDecimal remainingCapital = issueAmount.subtract(sumCallEventCalledAmount);
    BigDecimal remainingQuantity = issueQuantity.subtract(sumCallEventCalledQuantity);

    List<Subscription> validatedSubscriptions =
        subscriptionRepository.findByIssue_IdAndStatus(issueId, SubscriptionStatus.VALIDATED);

    BigDecimal totalSubscribed =
        validatedSubscriptions.stream()
            .map(Subscription::getAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal price = issue.getPrice() != null ? issue.getPrice() : BigDecimal.ONE;

    BigDecimal quantitySubscribed = totalSubscribed.divide(price, 4, RoundingMode.HALF_UP);

    BigDecimal totalUnsubscribed = issueAmount.subtract(totalSubscribed);

    BigDecimal quantityUnsubscribed = totalUnsubscribed.divide(price, 4, RoundingMode.HALF_UP);

    List<GlobalLiberation> validatedGlobalLiberations = new ArrayList<>();
    if (!callEventIds.isEmpty()) {
      validatedGlobalLiberations =
          globalLiberationRepository.findByCallEventIdInAndEventStatus(
              callEventIds, EventStatus.VALIDATED);
    }

    List<Long> globalLiberationIds =
        validatedGlobalLiberations.stream()
            .map(GlobalLiberation::getId)
            .collect(Collectors.toList());

    List<Liberation> validatedLiberations = new ArrayList<>();
    if (!globalLiberationIds.isEmpty()) {
      validatedLiberations =
          liberationRepository.findByGlobalLiberationIdInAndStatus(
              globalLiberationIds, LiberationStatus.VALIDATED);
    }

    BigDecimal releasedAmount =
        validatedLiberations.stream()
            .map(Liberation::getReleasedAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal releasedQuantity =
        validatedLiberations.stream()
            .map(Liberation::getReleasedQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal notReleasedAmount =
        validatedLiberations.stream()
            .map(Liberation::getRemainingAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal notReleasedQuantity =
        validatedLiberations.stream()
            .map(Liberation::getRemainingQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    SummaryDTO summaryDTO = new SummaryDTO();

    summaryDTO.setCalledAmount(calledAmount.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setCalledQuantity(calledQuantity.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setNotCalledAmount(notCalledAmount.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setNotCalledQuantity(notCalledQuantity.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setNotCalledPercentage(notCalledPercentage.setScale(4, RoundingMode.HALF_UP));

    summaryDTO.setRemainingCapital(remainingCapital.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setRemainingQuantity(remainingQuantity.setScale(4, RoundingMode.HALF_UP));

    summaryDTO.setTotalSubscribed(totalSubscribed.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setQuantitySubscribed(quantitySubscribed.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setTotalUnsubscribed(totalUnsubscribed.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setQuantityUnsubscribed(quantityUnsubscribed.setScale(4, RoundingMode.HALF_UP));

    summaryDTO.setReleasedAmount(releasedAmount.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setReleasedQuantity(releasedQuantity.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setNotReleasedAmount(notReleasedAmount.setScale(4, RoundingMode.HALF_UP));
    summaryDTO.setNotReleasedQuantity(notReleasedQuantity.setScale(4, RoundingMode.HALF_UP));

    return summaryDTO;
  }
}
