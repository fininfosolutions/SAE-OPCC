package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
import com.fininfo.saeopcc.multitenancy.domains.Subscription;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.NotificationStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.Origin;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionDirection;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
import com.fininfo.saeopcc.multitenancy.domains.flow.Notification;
import com.fininfo.saeopcc.multitenancy.domains.flow.NotificationError;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CompartementRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
import com.fininfo.saeopcc.shared.domains.Custodian;
import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import com.fininfo.saeopcc.shared.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import com.fininfo.saeopcc.shared.repositories.SAEConfigRepository;
import com.fininfo.saeopcc.shared.services.AssetService;
import com.fininfo.saeopcc.shared.services.CounterpartService;
import com.fininfo.saeopcc.shared.services.CustodianService;
import com.fininfo.saeopcc.shared.services.FundService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SubscriptionService {
  @Autowired private SubscriptionRepository subscriptionRepository;
  @Autowired private ModelMapper modelMapper;
  @Autowired AssetService assetService;
  @Autowired AssetRepository assetRepository;
  @Autowired CustodianService custodianService;
  @Autowired CounterpartService counterpartService;
  @Autowired ShareholderService shareholderService;
  @Autowired SAEConfigRepository saeConfigRepository;
  @Autowired private FundService fundService;
  @Autowired private CompartementRepository compartementRepository;
  @Autowired private IssueRepository issueRepository;
  @Autowired private IssueAccountRepository issueAccountRepository;
  @Autowired private MovementService movementService;
  @Autowired private CallEventRepository callEventRepository;
  @Autowired private SecuritiesAccountRepository securitiesAccountRepository;
  @Autowired private CallRepository callRepository;

  private static final String SAE_PREFIX = "SAE";

  @Transactional(readOnly = true)
  public List<SubscriptionDTO> findAll() {
    List<Subscription> subscriptions = subscriptionRepository.findAll();
    return subscriptions.stream()
        .map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class))
        .collect(Collectors.toList());
  }

  public List<SubscriptionDTO> generateSubscriptionFromNotification(
      List<Notification> notifications) {

    List<SubscriptionDTO> subscriptionDTOs = new ArrayList<>();

    for (Notification notification : notifications) {
      Subscription subscription = new Subscription();

      StringBuilder errorMessage = new StringBuilder();
      boolean ecart = false;
      subscription.setOrigin(Origin.AUTOMATIC);
      subscription.setSettlementDate(notification.getSettlementDate());
      subscription.setQuantity(notification.getQuantity());
      subscription.setAmount(notification.getGrossAmount());
      if (notification.getIsinCode() != null) {
        Fund fund = fundService.findByIsin(notification.getIsinCode());
        if (fund == null) {
          subscription.setStatus(SubscriptionStatus.INCORRECT);
          ecart = true;
          errorMessage.append("ISIN inexistant");
          subscription.setIssue(null);
        } else {
          Optional<Compartement> optionalCompartememnt = compartementRepository.findByFund(fund);
          if (optionalCompartememnt.isEmpty()) {
            subscription.setStatus(SubscriptionStatus.INCORRECT);
            ecart = true;
            errorMessage
                .append("Aucun compartement trouvé avec le fonds : ")
                .append(notification.getIsinCode());
            subscription.setIssue(null);
          } else {
            Compartement compartememnt = optionalCompartememnt.get();
            Optional<IssueAccount> optionalIssueAccount =
                issueAccountRepository.findByCompartement(compartememnt);
            if (optionalIssueAccount.isEmpty()) {
              subscription.setStatus(SubscriptionStatus.INCORRECT);
              ecart = true;
              errorMessage
                  .append("Aucun compte d'émission trouvé pour le compartement du fonds : ")
                  .append(notification.getIsinCode());
              subscription.setIssue(null);
            } else {
              IssueAccount issueAccount = optionalIssueAccount.get();
              Optional<Issue> optionalIssue = issueRepository.findByIssueAccount(issueAccount);
              if (optionalIssue.isEmpty()) {
                subscription.setStatus(SubscriptionStatus.INCORRECT);
                ecart = true;
                errorMessage
                    .append("Aucune émission trouvée pour le compartement du fonds : ")
                    .append(notification.getIsinCode());
                subscription.setIssue(null);
              } else {
                subscription.setIssue(optionalIssue.get());
              }
            }
          }
        }
      }

      subscription.setTransactionType(TransactionType.valueOf(notification.getTransactionType()));
      subscription.setSubscriptionDirection(SubscriptionDirection.DF);

      if (notification.getCustodian() != null) {

        Custodian custodian = custodianService.findByExternalReference(notification.getCustodian());
        if (custodian == null) {
          subscription.setStatus(SubscriptionStatus.INCORRECT);
          ecart = true;
          if (errorMessage.length() > 0) errorMessage.append(" et ");
          errorMessage.append("Dépositaire inexistant");
          subscription.setCustodian(null);
        } else {
          subscription.setCustodian(custodian);
        }
      }
      if (notification.getClientCode() != null) {
        Shareholder shareholder = shareholderService.findByReference(notification.getClientCode());
        if (shareholder == null) {
          subscription.setStatus(SubscriptionStatus.INCORRECT);
          ecart = true;
          if (errorMessage.length() > 0) errorMessage.append(" et ");
          errorMessage.append("Souscripteur inexistant");
          subscription.setShareholder(null);
        } else {
          subscription.setShareholder(shareholder);
        }
      }

      if (notification.getNotificationStatus().equals(NotificationStatus.FAIL)) {
        if (!notification.getNotificationErrors().isEmpty()) {
          ecart = true;
          for (NotificationError error : notification.getNotificationErrors()) {
            if (errorMessage.length() > 0) {
              errorMessage.append(" et ");
            }
            errorMessage.append(error.getErrorDescription());
          }
        }
      }

      if (ecart) {
        subscription.setEcart(true);
        subscription.setMessage(errorMessage.toString());
        subscription.setStatus(SubscriptionStatus.INCORRECT);
      } else {
        subscription.setEcart(false);
      }

      if (subscription.getStatus() == null) subscription.setStatus(SubscriptionStatus.PREVALIDATED);

      subscriptionDTOs.add(modelMapper.map(subscription, SubscriptionDTO.class));
    }
    for (SubscriptionDTO subscriptionDTO : subscriptionDTOs) {
      Subscription subs = modelMapper.map(subscriptionDTO, Subscription.class);
      subs = subscriptionRepository.save(subs);
      if (subs.getId() != null) {
        subs.setReference(createGlobalReference(SAE_PREFIX + subs.getId()));
        subs = subscriptionRepository.save(subs);

        subscriptionDTO.setId(subs.getId());
        subscriptionDTO.setReference(subs.getReference());
      }
    }
    return subscriptionDTOs;
  }

  public SubscriptionDTO save(SubscriptionDTO subscriptionDTO) {
    Subscription subscription = modelMapper.map(subscriptionDTO, Subscription.class);
    subscription = subscriptionRepository.save(subscription);
    if (subscription.getId() != null) {
      subscription.setReference(createGlobalReference(SAE_PREFIX + subscription.getId()));
      subscription.setStatus(SubscriptionStatus.PREVALIDATED);
      subscription = subscriptionRepository.save(subscription);
    }
    SubscriptionDTO subDTO = modelMapper.map(subscription, SubscriptionDTO.class);
    subDTO.setShareholderReference(subscriptionDTO.getShareholderReference());

    return subDTO;
  }

  private String createGlobalReference(String reference) {
    String aditionaleZeros = "0".repeat(16 - reference.length());
    return reference.replace("SAE", "SAE".concat(aditionaleZeros));
  }

  public Page<SubscriptionDTO> getSubscriptionsByIssue(
      Long issueId, SubscriptionStatus status, Pageable pageable) {
    Optional<Issue> issueOpt = issueRepository.findById(issueId);

    if (issueOpt.isPresent()) {
      Page<Subscription> page;
      if (status != null) {
        page = subscriptionRepository.findByIssue_IdAndStatus(issueId, status, pageable);
      } else {
        page = subscriptionRepository.findByIssue_Id(issueId, pageable);
      }

      return page.map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class));
    } else {
      return new PageImpl<>(new ArrayList<>());
    }
  }

  public long countSubscriptionsByIssue(Long issueId, SubscriptionStatus status) {
    if (status != null) {
      return subscriptionRepository.countByIssue_IdAndStatus(issueId, status);
    } else {
      return subscriptionRepository.countByIssue_Id(issueId);
    }
  }

  @Transactional(readOnly = true)
  public Optional<SubscriptionDTO> findOne(Long id) {
    log.debug("Request to get Subscription : {}", id);
    return subscriptionRepository
        .findById(id)
        .map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class));
  }

  public SubscriptionDTO updateManuelSubscription(SubscriptionDTO subscriptionDTO) {
    Subscription subscription = modelMapper.map(subscriptionDTO, Subscription.class);
    return modelMapper.map(subscriptionRepository.save(subscription), SubscriptionDTO.class);
  }

  public SubscriptionDTO rejectSubscription(Long id) {
    return subscriptionRepository
        .findById(id)
        .map(
            subscription -> {
              if (subscription.getStatus().equals(SubscriptionStatus.PREVALIDATED)) {
                subscription.setStatus(SubscriptionStatus.REJECTED);
                return subscriptionRepository.save(subscription);
              } else {
                return subscription;
              }
            })
        .map(x -> modelMapper.map(x, SubscriptionDTO.class))
        .orElseThrow();
  }

  @Transactional
  public List<SubscriptionDTO> validateSubscription(List<SubscriptionDTO> subscriptionDtos) {
    List<SubscriptionDTO> subscriptionsDtoSaved =
        subscriptionDtos.stream()
            .map(
                dto -> {
                  Subscription subscription =
                      subscriptionRepository
                          .findById(dto.getId())
                          .orElseThrow(
                              () ->
                                  new EntityNotFoundException(
                                      "Subscription not found for id: " + dto.getId()));

                  subscription.setStatus(SubscriptionStatus.VALIDATED);
                  subscription = subscriptionRepository.save(subscription);

                  List<CallEvent> callevents =
                      callEventRepository.findByIssue_IdAndEventStatus(
                          dto.getIssueId(), EventStatus.VALIDATED);

                  if (!callevents.isEmpty()) {
                    for (CallEvent callevent : callevents) {

                      Call call = new Call();
                      call.setCallEvent(callevent);
                      call.setSubscription(subscription);

                      BigDecimal percentage =
                          callevent.getPercentage() != null
                              ? callevent.getPercentage()
                              : BigDecimal.ZERO;
                      call.setPercentage(percentage);

                      BigDecimal subAmount =
                          subscription.getAmount() != null
                              ? subscription.getAmount()
                              : BigDecimal.ZERO;
                      BigDecimal subQuantity =
                          subscription.getQuantity() != null
                              ? subscription.getQuantity()
                              : BigDecimal.ZERO;

                      BigDecimal calledAmount = percentage.multiply(subAmount);
                      BigDecimal calledQuantity = percentage.multiply(subQuantity);

                      call.setCalledAmount(calledAmount);
                      call.setCalledQuantity(calledQuantity);

                      BigDecimal remainingAmount = subAmount.subtract(calledAmount);
                      BigDecimal remainingQuantity = subQuantity.subtract(calledQuantity);

                      call.setRemainingAmount(remainingAmount);
                      call.setRemainingQuantity(remainingQuantity);

                      call.setDescription(callevent.getDescription());
                      call.setCallDate(callevent.getCallDate());

                      SecuritiesAccount secaccount = subscription.getSecuritiesAccount();

                      List<SecuritiesAccount> accounts =
                          securitiesAccountRepository
                              .findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountType(
                                  (secaccount.getAsset() != null)
                                      ? secaccount.getAsset().getId()
                                      : null,
                                  (subscription.getShareholder() != null)
                                      ? subscription.getShareholder().getId()
                                      : null,
                                  (secaccount.getIntermediary() != null)
                                      ? secaccount.getIntermediary().getId()
                                      : null,
                                  AccountType.APPELE);

                      if (accounts == null || accounts.isEmpty()) {
                        call.setSecuritiesAccount(null);
                        call.setMessage("Compte titre inexistant");
                        call.setStatus(CallStatus.INCOMPLETE);

                        callevent.setEventStatus(EventStatus.INCOMPLETE);
                        callEventRepository.save(callevent);

                      } else {
                        call.setSecuritiesAccount(accounts.get(0));
                        call.setStatus(CallStatus.PREVALIDATED);
                      }

                      call = callRepository.save(call);

                      if (call.getId() != null) {
                        call.setReference(callevent.getReference() + "-" + call.getId());
                        call = callRepository.save(call);
                      }
                    }
                  }

                  return modelMapper.map(subscription, SubscriptionDTO.class);
                })
            .collect(Collectors.toList());

    subscriptionsDtoSaved.sort(Comparator.comparing(dto -> dto.getSettlementDate()));

    subscriptionsDtoSaved.forEach(
        subsDto -> {
          movementService.handleMovementsAndPositionsfromsubscription(
              subsDto, SubscriptionStatus.VALIDATED);
        });

    return subscriptionsDtoSaved;
  }
}
