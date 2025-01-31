package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.Client;
import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.ReportingLiberationStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.repositories.ClientRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CompartementRepository;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartmentsReportingDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompartementReportingService {

  @Autowired private CompartementRepository compartementRepository;
  @Autowired private IssueAccountRepository issueAccountRepository;
  @Autowired private IssueRepository issueRepository;
  @Autowired private SubscriptionRepository subscriptionRepository;
  @Autowired private CallEventRepository callEventRepository;
  @Autowired private CallRepository callRepository;
  @Autowired private GlobalLiberationRepository globalLiberationRepository;
  @Autowired private LiberationRepository liberationRepository;
  @Autowired private ClientRepository clientRepository;

  public Map<String, List<CompartmentsReportingDTO>> getAllCompartmentsReporting() {
    List<Client> allClients = clientRepository.findAll();
    Map<String, List<CompartmentsReportingDTO>> groupedResult = new HashMap<>();

    for (Client client : allClients) {
      String clientDescription = client.getDescription();
      if (clientDescription == null) {
        clientDescription = "Inconnu Client";
      }

      List<Compartement> compartements = compartementRepository.findByClient_Id(client.getId());
      List<CompartmentsReportingDTO> clientCompartments = new ArrayList<>();

      for (Compartement compartement : compartements) {
        CompartmentsReportingDTO dto = computeReportingForCompartment(client, compartement);
        clientCompartments.add(dto);
      }

      groupedResult.put(clientDescription, clientCompartments);
    }

    return groupedResult;
  }

  private CompartmentsReportingDTO computeReportingForCompartment(
      Client client, Compartement compartement) {
    CompartmentsReportingDTO dto = new CompartmentsReportingDTO();
    dto.setFundName(client.getDescription());
    dto.setCompartementName(compartement.getFonds());

    BigDecimal totalSubscribedAmount = BigDecimal.ZERO;
    BigDecimal totalCalledAmount = BigDecimal.ZERO;
    BigDecimal totalCalledQuantity = BigDecimal.ZERO;
    BigDecimal notCalledAmount = BigDecimal.ZERO;
    BigDecimal notCalledQuantity = BigDecimal.ZERO;
    BigDecimal totalReleasedAmount = BigDecimal.ZERO;
    BigDecimal totalReleasedQuantity = BigDecimal.ZERO;
    BigDecimal notReleasedAmount = BigDecimal.ZERO;
    BigDecimal notReleasedQuantity = BigDecimal.ZERO;

    Optional<IssueAccount> optIssueAccount =
        issueAccountRepository.findByCompartement_Id(compartement.getId());
    if (optIssueAccount.isEmpty()) {
      return dto;
    }
    IssueAccount issueAccount = optIssueAccount.get();

    List<Issue> issues = issueRepository.findByIssueAccount_Id(issueAccount.getId());
    for (Issue issue : issues) {
      // Subscriptions
      List<Subscription> subscriptions =
          subscriptionRepository.findByIssue_IdAndStatus(
              issue.getId(), SubscriptionStatus.VALIDATED);
      BigDecimal sumSubscriptionAmounts =
          subscriptions.stream()
              .map(Subscription::getAmount)
              .filter(Objects::nonNull)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
      totalSubscribedAmount = totalSubscribedAmount.add(sumSubscriptionAmounts);

      // Call Events
      List<CallEvent> callEvents =
          callEventRepository.findByIssue_IdAndEventStatus(issue.getId(), EventStatus.VALIDATED);
      for (CallEvent callEvent : callEvents) {
        List<Call> calls =
            callRepository.findByCallEvent_IdAndStatus(callEvent.getId(), CallStatus.VALIDATED);

        BigDecimal sumCallAmounts =
            calls.stream()
                .map(Call::getCalledAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalCalledAmount = totalCalledAmount.add(sumCallAmounts);

        BigDecimal sumCallQuantities =
            calls.stream()
                .map(Call::getCalledQuantity)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalCalledQuantity = totalCalledQuantity.add(sumCallQuantities);

        BigDecimal sumNotCalledAmounts =
            calls.stream()
                .map(Call::getRemainingAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        notCalledAmount = notCalledAmount.add(sumNotCalledAmounts);

        BigDecimal sumNotCalledQuantities =
            calls.stream()
                .map(Call::getRemainingQuantity)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        notCalledQuantity = notCalledQuantity.add(sumNotCalledQuantities);

        // Global Liberations
        List<GlobalLiberation> globalLiberations =
            globalLiberationRepository.findByEventStatusAndCallEvent_Id(
                EventStatus.VALIDATED, callEvent.getId());

        for (GlobalLiberation global : globalLiberations) {
          List<Liberation> liberations =
              liberationRepository.findByStatusAndGlobalLiberation_Id(
                  LiberationStatus.VALIDATED, global.getId());

          BigDecimal sumReleasedAmount =
              liberations.stream()
                  .map(Liberation::getReleasedAmount)
                  .filter(Objects::nonNull)
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
          totalReleasedAmount = totalReleasedAmount.add(sumReleasedAmount);

          BigDecimal sumReleasedQuantity =
              liberations.stream()
                  .map(Liberation::getReleasedQuantity)
                  .filter(Objects::nonNull)
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
          totalReleasedQuantity = totalReleasedQuantity.add(sumReleasedQuantity);

          BigDecimal sumNotReleasedAmount =
              liberations.stream()
                  .map(Liberation::getRemainingAmount)
                  .filter(Objects::nonNull)
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
          notReleasedAmount = notReleasedAmount.add(sumNotReleasedAmount);

          BigDecimal sumNotReleasedQuantity =
              liberations.stream()
                  .map(Liberation::getRemainingQuantity)
                  .filter(Objects::nonNull)
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
          notReleasedQuantity = notReleasedQuantity.add(sumNotReleasedQuantity);
        }
      }
    }

    String releaseStatus =
        BigDecimal.ZERO.compareTo(totalSubscribedAmount) == 0
            ? ReportingLiberationStatus.UNLIBERATED.name()
            : BigDecimal.ZERO.compareTo(notReleasedQuantity) != 0
                ? ReportingLiberationStatus.PENDING.name()
                : ReportingLiberationStatus.SUCCEEDED.name();
    dto.setTotalSubscribedAmount(totalSubscribedAmount);
    dto.setTotalCalledAmount(totalCalledAmount);
    dto.setTotalCalledQuantity(totalCalledQuantity);
    dto.setNotCalledAmount(notCalledAmount);
    dto.setNotCalledQuantity(notCalledQuantity);
    dto.setTotalReleasedAmount(totalReleasedAmount);
    dto.setTotalReleasedQuantity(totalReleasedQuantity);
    dto.setNotReleasedAmount(notReleasedAmount);
    dto.setNotReleasedQuantity(notReleasedQuantity);
    dto.setReleaseStatus(releaseStatus);

    dto.setInvestmentPeriod(compartement.getInvestmentPeriod());
    dto.setDisinvestmentPeriod(compartement.getDinvestmentPeriod());

    return dto;
  }
}
