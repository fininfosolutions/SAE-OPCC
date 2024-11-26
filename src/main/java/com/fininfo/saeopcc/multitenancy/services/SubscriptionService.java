// package com.fininfo.saeopcc.multitenancy.services;

// import com.fininfo.saeopcc.multitenancy.domains.Shareholder;
// import com.fininfo.saeopcc.multitenancy.domains.Subscription;
// import com.fininfo.saeopcc.multitenancy.domains.enumeration.NotificationStatus;
// import com.fininfo.saeopcc.multitenancy.domains.enumeration.Origin;
// import com.fininfo.saeopcc.multitenancy.domains.enumeration.SubscriptionStatus;
// import com.fininfo.saeopcc.multitenancy.domains.flow.Notification;
// import com.fininfo.saeopcc.multitenancy.domains.flow.NotificationError;
// import com.fininfo.saeopcc.multitenancy.repositories.SubscriptionRepository;
// import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
// import com.fininfo.saeopcc.shared.domains.Asset;
// import com.fininfo.saeopcc.shared.domains.Counterpart;
// import com.fininfo.saeopcc.shared.domains.Custodian;
// import com.fininfo.saeopcc.shared.domains.SAEConfig;
// import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
// import com.fininfo.saeopcc.shared.domains.enumeration.TransactionType;
// import com.fininfo.saeopcc.shared.repositories.AssetRepository;
// import com.fininfo.saeopcc.shared.repositories.SAEConfigRepository;
// import com.fininfo.saeopcc.shared.services.AssetService;
// import com.fininfo.saeopcc.shared.services.CounterpartService;
// import com.fininfo.saeopcc.shared.services.CustodianService;
// import java.math.BigDecimal;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;
// import javax.persistence.EntityNotFoundException;
// import lombok.extern.slf4j.Slf4j;
// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// @Service
// @Slf4j
// public class SubscriptionService {
//   @Autowired private SubscriptionRepository subscriptionRepository;
//   @Autowired private ModelMapper modelMapper;
//   @Autowired AssetService assetService;
//   @Autowired AssetRepository assetRepository;
//   @Autowired CustodianService custodianService;
//   @Autowired CounterpartService counterpartService;
//   @Autowired ShareholderService shareholderService;
//   @Autowired SAEConfigRepository saeConfigRepository;

//   private static final String SAFE = "SAFE";
//   private static final String SAE_PREFIX = "SAE";

//   @Transactional(readOnly = true)
//   public List<SubscriptionDTO> findAll() {
//     List<Subscription> subscriptions = subscriptionRepository.findAll();
//     return subscriptions.stream()
//         .map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class))
//         .collect(Collectors.toList());
//   }

//   public List<SubscriptionDTO> generateSubscriptionFromNotification(
//       List<Notification> notifications, Boolean isSubscription) {

//     List<SubscriptionDTO> subscriptionDTOs = new ArrayList<>();
//     BigDecimal totalQuantity =
//         notifications.stream()
//             .filter(x -> x.getQuantity() != null)
//             .map(Notification::getQuantity)
//             .reduce(BigDecimal.ZERO, BigDecimal::add);

//     for (Notification notification : notifications) {
//       Subscription subscription = new Subscription();

//       StringBuilder errorMessage = new StringBuilder();
//       boolean ecart = false;
//       subscription.setOrigin(Origin.AUTOMATIC);
//       Optional<Asset> assetHOptional = assetRepository.getByIsin(notification.getIsinCode());
//       subscription.setSettlementDate(notification.getSettlementDate());
//       subscription.setQuantity(notification.getQuantity());
//       subscription.setAmount(notification.getGrossAmount());
//       if (assetHOptional.isPresent()) {
//         BigDecimal remainingQuantity =
//             BigDecimal.valueOf(assetHOptional.get().getSecuritiesInCirculationNumber())
//                 .subtract(totalQuantity.add(getTotalQuantityUsed(assetHOptional.get().getId())));

//         if (remainingQuantity.signum() == -1) {
//           subscription.setStatus(SubscriptionStatus.INCORRECT);
//           ecart = true;
//           errorMessage.append(
//               "La quantité totale doit être inférieure ou égale au nombre de titres en
// circulation");
//         }
//       }
//       if (notification.getIsinCode() != null) {
//         Asset asset = assetService.findByIsin(notification.getIsinCode());
//         if (asset == null) {
//           subscription.setStatus(SubscriptionStatus.INCORRECT);
//           ecart = true;
//           errorMessage.append("ISIN inexistant");
//           subscription.setAsset(null);
//         } else {
//           subscription.setAsset(asset);
//         }
//       }
//
// subscription.setTransactionType(TransactionType.valueOf(notification.getTransactionType()));

//       if (isSubscription) {
//         subscription.setMaturityDate(notification.getMaturityDate());
//         subscription.setRate(notification.getNegociationRate());
//       }
//       if (notification.getCustodian() != null) {

//         Custodian custodian =
// custodianService.findByExternalReference(notification.getCustodian());
//         if (custodian == null) {
//           subscription.setStatus(SubscriptionStatus.INCORRECT);
//           ecart = true;
//           if (errorMessage.length() > 0) errorMessage.append(" et ");
//           errorMessage.append("Dépositaire inexistant");
//           subscription.setCustodian(null);
//         } else {
//           subscription.setCustodian(custodian);
//         }
//       }
//       if (notification.getClientCode() != null) {
//         Shareholder shareholder =
// shareholderService.findByReference(notification.getClientCode());
//         if (shareholder == null) {
//           subscription.setStatus(SubscriptionStatus.INCORRECT);
//           ecart = true;
//           if (errorMessage.length() > 0) errorMessage.append(" et ");
//           errorMessage.append("Souscripteur inexistant");
//           subscription.setShareholder(null);
//         } else {
//           subscription.setShareholder(shareholder);
//         }
//       }

//       if (notification.getNotificationStatus().equals(NotificationStatus.FAIL)) {
//         if (!notification.getNotificationErrors().isEmpty()) {
//           ecart = true;
//           for (NotificationError error : notification.getNotificationErrors()) {
//             if (errorMessage.length() > 0) {
//               errorMessage.append(" et ");
//             }
//             errorMessage.append(error.getErrorDescription());
//           }
//         }
//       }

//       if (ecart) {
//         subscription.setEcart(true);
//         subscription.setMessage(errorMessage.toString());
//         subscription.setStatus(SubscriptionStatus.INCORRECT);
//       } else {
//         subscription.setEcart(false);
//       }

//       if (subscription.getStatus() == null) subscription.setStatus(SubscriptionStatus.PREVALID);

//       subscriptionDTOs.add(modelMapper.map(subscription, SubscriptionDTO.class));
//     }
//     for (SubscriptionDTO subscriptionDTO : subscriptionDTOs) {
//       Subscription subs = modelMapper.map(subscriptionDTO, Subscription.class);
//       subs = subscriptionRepository.save(subs);
//       if (subs.getId() != null) {
//         subs.setReference(createGlobalReference(SAE_PREFIX + subs.getId()));
//         subs = subscriptionRepository.save(subs);

//         subscriptionDTO.setId(subs.getId());
//         subscriptionDTO.setReference(subs.getReference());
//       }
//     }
//     return subscriptionDTOs;
//   }

//   @Transactional
//   public SubscriptionDTO save(SubscriptionDTO subscriptionDTO) {
//     Subscription subscription = modelMapper.map(subscriptionDTO, Subscription.class);
//     subscription = subscriptionRepository.save(subscription);
//     if (subscription.getId() != null) {
//       subscription.setReference(createGlobalReference(SAE_PREFIX + subscription.getId()));
//       subscription.setStatus(SubscriptionStatus.PREVALIDATED);
//       subscription = subscriptionRepository.save(subscription);
//     }
//     SubscriptionDTO subDTO = modelMapper.map(subscription, SubscriptionDTO.class);
//     subDTO.setAssetIsin(subscriptionDTO.getAssetIsin());
//     subDTO.setShareholderReference(subscriptionDTO.getShareholderReference());

//     return subDTO;
//   }

//   @Transactional(readOnly = true)
//   public Page<SubscriptionDTO> getSubscriptionsByAsset(Long assetId, Pageable pageable) {
//     Optional<Asset> assetOpt = assetRepository.findById(assetId);
//     if (assetOpt.isPresent()) {
//       Page<Subscription> page = subscriptionRepository.findByAsset(assetOpt.get(), pageable);
//       return page.map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class));
//     } else {
//       return new PageImpl<>(new ArrayList<>());
//     }
//   }

//   @Transactional(readOnly = true)
//   public long countSubscriptionsByAsset(Long assetId) {
//     return assetRepository
//         .findById(assetId)
//         .map(asset -> subscriptionRepository.countByAsset(asset))
//         .orElse(0L);
//   }

//   @Transactional(readOnly = true)
//   public Page<SubscriptionDTO> findAll(Pageable pageable) {
//     return subscriptionRepository
//         .findAll(pageable)
//         .map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class));
//   }

//   @Transactional(readOnly = true)
//   public Long countAll() {
//     return subscriptionRepository.count();
//   }

//   private String createGlobalReference(String reference) {
//     String aditionaleZeros = "0".repeat(16 - reference.length());
//     return reference.replace("SAE", "SAE".concat(aditionaleZeros));
//   }

//   @Transactional(readOnly = true)
//   public Optional<SubscriptionDTO> findOne(Long id) {
//     log.debug("Request to get Subscription : {}", id);
//     return subscriptionRepository
//         .findById(id)
//         .map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class));
//   }

//   public SubscriptionDTO rejectSubscription(Long id) {
//     return subscriptionRepository
//         .findById(id)
//         .map(
//             subscription -> {
//               if (subscription.getStatus().equals(SubscriptionStatus.PREVALIDATED)) {
//                 subscription.setStatus(SubscriptionStatus.REJECTED);
//                 return subscriptionRepository.save(subscription);
//               } else {
//                 return subscription;
//               }
//             })
//         .map(x -> modelMapper.map(x, SubscriptionDTO.class))
//         .orElseThrow();
//   }

//   public SubscriptionDTO updateManuelSubscription(SubscriptionDTO subscriptionDTO) {
//     Subscription subscription = modelMapper.map(subscriptionDTO, Subscription.class);
//     return modelMapper.map(subscriptionRepository.save(subscription), SubscriptionDTO.class);
//   }

//   private BigDecimal getTotalQuantityUsed(Long id) {
//     BigDecimal quantityUsed = BigDecimal.ZERO;
//     List<Subscription> subscriptions =
//         subscriptionRepository.findByAsset_id(id).stream()
//             .filter(
//                 subscription ->
//                     subscription.getStatus() != SubscriptionStatus.INCORRECT
//                         && subscription.getStatus() != SubscriptionStatus.REJECTED)
//             .collect(Collectors.toList());

//     for (Subscription payment : subscriptions) {
//       quantityUsed = quantityUsed.add(payment.getQuantity());
//     }

//     return quantityUsed;
//   }

//   public BigDecimal getCurrentQuantity(Long assetId) {

//     Optional<Asset> assetOptional = assetRepository.findById(assetId);
//     BigDecimal sommeQuantity = BigDecimal.ZERO;
//     BigDecimal availbleQuantity = BigDecimal.ZERO;
//     if (assetOptional.isPresent()) {
//       Asset asset = assetOptional.get();
//       availbleQuantity = BigDecimal.valueOf(asset.getSecuritiesInCirculationNumber());

//       List<Subscription> subscriptions =
//           subscriptionRepository.findByAsset_id(asset.getId()).stream()
//               .filter(
//                   subscription ->
//                       subscription.getStatus() != SubscriptionStatus.INCORRECT
//                           && subscription.getStatus() != SubscriptionStatus.REJECTED)
//               .collect(Collectors.toList());
//       for (Subscription payment : subscriptions) {
//         sommeQuantity = sommeQuantity.add(payment.getQuantity());
//       }
//       if (!subscriptions.isEmpty())
//         availbleQuantity =
//
// BigDecimal.valueOf(subscriptions.get(0).getAsset().getSecuritiesInCirculationNumber())
//                 .subtract(sommeQuantity);
//     }
//     return availbleQuantity;
//   }
// }
