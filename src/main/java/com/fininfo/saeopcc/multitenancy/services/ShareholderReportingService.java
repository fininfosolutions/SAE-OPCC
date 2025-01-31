package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.Client;
import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.repositories.ClientRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CompartementRepository;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.ShareholderRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.ShareholderReportingDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShareholderReportingService {
  @Autowired private ShareholderRepository shareholderRepository;

  @Autowired private CompartementRepository compartementRepository;
  @Autowired private IssueAccountRepository issueAccountRepository;
  @Autowired private IssueRepository issueRepository;
  @Autowired private SubscriptionRepository subscriptionRepository;
  @Autowired private CallEventRepository callEventRepository;
  @Autowired private CallRepository callRepository;
  @Autowired private GlobalLiberationRepository globalLiberationRepository;
  @Autowired private LiberationRepository liberationRepository;
  @Autowired private ClientRepository clientRepository;

  public Map<String, Map<String, Map<String, List<ShareholderReportingDTO>>>>
      generateShareholderReport() {
    Map<String, Map<String, Map<String, List<ShareholderReportingDTO>>>> result = new HashMap<>();

    List<Client> clients = clientRepository.findAll();
    for (Client client : clients) {
      String clientDescription = client.getDescription() != null ? client.getDescription() : "";

      Map<String, Map<String, List<ShareholderReportingDTO>>> compartementReport = new HashMap<>();
      List<Compartement> compartements = compartementRepository.findByClient_Id(client.getId());

      for (Compartement compartement : compartements) {
        String compartementFonds = compartement.getFonds() != null ? compartement.getFonds() : "";

        Map<String, List<ShareholderReportingDTO>> subscriberReport = new HashMap<>();
        Optional<IssueAccount> issueAccount =
            issueAccountRepository.findByCompartement_Id(compartement.getId());

        if (issueAccount.isPresent()) {
          List<Issue> issues = issueRepository.findByIssueAccount_Id(issueAccount.get().getId());
          BigDecimal totalSubscribedAmount =
              issues.stream()
                  .flatMap(
                      issue ->
                          subscriptionRepository
                              .findByIssue_IdAndStatus(issue.getId(), SubscriptionStatus.VALIDATED)
                              .stream())
                  .map(Subscription::getAmount)
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
          BigDecimal totalSubscribedQuantity =
              issues.stream()
                  .flatMap(
                      issue ->
                          subscriptionRepository
                              .findByIssue_IdAndStatus(issue.getId(), SubscriptionStatus.VALIDATED)
                              .stream())
                  .map(Subscription::getQuantity)
                  .reduce(BigDecimal.ZERO, BigDecimal::add);

          for (Issue issue : issues) {

            List<CallEvent> callEvents =
                callEventRepository.findByIssue_IdAndEventStatus(
                    issue.getId(), EventStatus.VALIDATED);

            for (CallEvent callEvent : callEvents) {
              List<Call> calls =
                  callRepository.findByCallEvent_IdAndStatus(
                      callEvent.getId(), CallStatus.VALIDATED);
              Map<Long, List<Call>> shareHolderCallList =
                  calls.stream()
                      .collect(
                          Collectors.groupingBy(
                              call -> call.getSubscription().getShareholder().getId()));
              shareHolderCallList.forEach(
                  (id, callsId) -> {
                    for (Call call : callsId) {
                      BigDecimal totalCalledAmount = call.getCalledAmount();
                      BigDecimal totalCalledQuantity = call.getCalledQuantity();
                      List<Liberation> liberations =
                          liberationRepository.findByCall_IdAndStatus(
                              call.getId(), LiberationStatus.VALIDATED);

                      BigDecimal totalReleasedAmount =
                          liberations.stream()
                              .map(Liberation::getReleasedAmount)
                              .reduce(BigDecimal.ZERO, BigDecimal::add);

                      BigDecimal totalReleasedQuantity =
                          liberations.stream()
                              .map(Liberation::getReleasedQuantity)
                              .reduce(BigDecimal.ZERO, BigDecimal::add);

                      BigDecimal notReleasedAmount =
                          liberations.stream()
                              .map(Liberation::getRemainingAmount)
                              .reduce(BigDecimal.ZERO, BigDecimal::add);

                      BigDecimal notReleasedQuantity =
                          liberations.stream()
                              .map(Liberation::getRemainingQuantity)
                              .reduce(BigDecimal.ZERO, BigDecimal::add);

                      String releaseStatus = "PENDING";
                      if (totalReleasedAmount.compareTo(BigDecimal.ZERO) > 0) {
                        releaseStatus = "";
                      }
                      if (totalReleasedAmount.compareTo(totalCalledAmount) == 0) {
                        releaseStatus = "";
                      }

                      String subscriberName =
                          shareholderRepository
                              .findById(id)
                              .map(Shareholder::getDescription)
                              .orElse("OTHER");

                      ShareholderReportingDTO dto = new ShareholderReportingDTO();
                      dto.setFundName(clientDescription);
                      dto.setCompartementName(compartementFonds);
                      dto.setSubscriberName(subscriberName);
                      dto.setTotalSubscribedAmount(totalSubscribedAmount);
                      dto.setTotalSubscribedQuantity(totalSubscribedQuantity);
                      dto.setTotalCalledAmount(totalCalledAmount);
                      dto.setTotalCalledQuantity(totalCalledQuantity);
                      dto.setTotalReleasedAmount(totalReleasedAmount);
                      dto.setTotalReleasedQuantity(totalReleasedQuantity);
                      dto.setNotReleasedAmount(notReleasedAmount);
                      dto.setNotReleasedQuantity(notReleasedQuantity);
                      dto.setReleaseStatus(releaseStatus);
                      dto.setCalledLiberation(call.getReference());
                      dto.setCalledDate(call.getCallDate());
                      dto.setClosedCalledDate(callEvent.getClosingDate());

                      subscriberReport
                          .computeIfAbsent(subscriberName, k -> new ArrayList<>())
                          .add(dto);
                    }
                  });
            }
          }
        }

        compartementReport.put(compartementFonds, subscriberReport);
      }

      result.put(clientDescription, compartementReport);
    }

    return result;
  }
}
