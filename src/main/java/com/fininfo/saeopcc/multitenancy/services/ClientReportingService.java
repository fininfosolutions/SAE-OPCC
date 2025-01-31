package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.Client;
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
import com.fininfo.saeopcc.multitenancy.services.dto.ClientReportingDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientReportingService {

  @Autowired CompartementRepository compartementRepository;
  @Autowired IssueAccountRepository issueAccountRepository;
  @Autowired IssueRepository issueRepository;
  @Autowired SubscriptionRepository subscriptionRepository;
  @Autowired CallEventRepository callEventRepository;
  @Autowired CallRepository callRepository;
  @Autowired GlobalLiberationRepository globalLiberationRepository;
  @Autowired LiberationRepository liberationRepository;
  @Autowired ClientRepository clientRepository;

  public List<ClientReportingDTO> getAllClientsReporting() {
    return clientRepository.findAll().stream().map(this::computeReportingForOneClient).toList();
  }

  private ClientReportingDTO computeReportingForOneClient(Client client) {
    ClientReportingDTO dto = new ClientReportingDTO();
    dto.setClientId(client.getId());
    dto.setFundName(client.getDescription());
    List<Long> issueAccoutIds =
        compartementRepository.findByClient_Id(client.getId()).stream()
            .map(c -> issueAccountRepository.findByCompartement_Id(c.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(IssueAccount::getId)
            .toList();
    List<Issue> issueList = issueRepository.findByIssueAccount_IdIn(issueAccoutIds);

    BigDecimal totalSubscribedAmount =
        issueList.stream()
            .map(
                issue ->
                    subscriptionRepository
                        .findByIssue_IdAndStatus(issue.getId(), SubscriptionStatus.VALIDATED)
                        .stream()
                        .map(Subscription::getAmount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    List<CallEvent> callEvents =
        callEventRepository.findByIssue_IdInAndEventStatus(
            issueList.stream().map(Issue::getId).toList(), EventStatus.VALIDATED);

    List<Call> calls =
        callRepository.findByCallEvent_IdInAndStatus(
            callEvents.stream().map(CallEvent::getId).toList(), CallStatus.VALIDATED);

    BigDecimal totalCalledAmount =
        calls.stream()
            .map(Call::getCalledAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalCalledQuantity =
        calls.stream()
            .map(Call::getCalledQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal notCalledAmount =
        calls.stream()
            .map(Call::getRemainingAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal notCalledQuantity =
        calls.stream()
            .map(Call::getRemainingQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    List<Long> globalLiberationIds =
        globalLiberationRepository
            .findByEventStatusAndCallEvent_IdIn(
                EventStatus.VALIDATED, callEvents.stream().map(CallEvent::getId).toList())
            .stream()
            .map(GlobalLiberation::getId)
            .toList();

    List<Liberation> liberations =
        liberationRepository.findByStatusAndGlobalLiberation_IdIn(
            LiberationStatus.VALIDATED, globalLiberationIds);
    BigDecimal totalReleasedAmount =
        liberations.stream()
            .map(Liberation::getReleasedAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalReleasedQuantity =
        liberations.stream()
            .map(Liberation::getReleasedQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal notReleasedAmount =
        liberations.stream()
            .map(Liberation::getRemainingAmount)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal notReleasedQuantity =
        liberations.stream()
            .map(Liberation::getRemainingQuantity)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    ReportingLiberationStatus releaseStatus =
        BigDecimal.ZERO.compareTo(totalSubscribedAmount) == 0
            ? ReportingLiberationStatus.NOT_SUBSCRIBED
            : BigDecimal.ZERO.compareTo(notReleasedQuantity) != 0
                ? ReportingLiberationStatus.PENDING
                : ReportingLiberationStatus.SUCCEEDED;

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

    dto.setInvestmentPeriod("");
    dto.setDisinvestmentPeriod("");

    return dto;
  }
}
