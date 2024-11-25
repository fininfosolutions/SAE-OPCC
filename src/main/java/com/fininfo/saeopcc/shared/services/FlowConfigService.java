// package com.fininfo.saeopcc.shared.services;

// import com.fininfo.saeopcc.multitenancy.services.IRLService;
// import com.fininfo.saeopcc.multitenancy.services.dto.IRLDTO;
// import com.fininfo.saeopcc.shared.domains.enumeration.ExcelContentType;
// import com.fininfo.saeopcc.shared.domains.flow.ExcelFlow;
// import com.fininfo.saeopcc.shared.domains.flow.Flow;
// import com.fininfo.saeopcc.shared.domains.flow.FlowConfig;
// import com.fininfo.saeopcc.shared.domains.flow.Notification;
// import com.fininfo.saeopcc.shared.repositories.MessageDescriptionSwiftFlowRepository;
// import com.fininfo.saeopcc.shared.services.processors.ExcelFlowProcessor;
// import com.fininfo.saeopcc.shared.services.processors.SwiftFlowProcessor;
// import java.util.ArrayList;
// import java.util.List;
// import javax.transaction.Transactional;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// @Service
// @Transactional
// @Slf4j
// public class FlowConfigService {

//   @Autowired private ExcelFlowProcessor exceFlowProcessor;
//   @Autowired private FlowService flowService;

//   @Autowired private NotificationService notificationService;

//   @Autowired MessageDescriptionSwiftFlowRepository messageDescriptionSwiftFlowRepository;

//   @Autowired private SwiftFlowProcessor swiftFlowProcessor;

//   private final String SWIFT_MT542 = "MT542";

//   private final String SWIFT_MT543 = "MT543";
//   @Autowired IRLService irlservice;

//   public void globalFlowIntegrationProcess(
//       FlowConfig flowConfig, Flow flow, MultipartFile file, List<IRLDTO> irldtos)
//       throws NoSuchFieldException, SecurityException {
//     log.info("***** Entering Global Broker Process Method *****");

//     log.info("**** Entering Generating Notification Process ****");
//     List<Notification> notifications = generateNotificationsFromFlowConfig(flowConfig, flow,
// file);

//     log.debug("**** Generated Notifications : {}", notifications);

//     if (!notifications.isEmpty() && flow.getFlowStatus() == null) {

//       flow = flowService.saveFlowWithNotification(notifications, flow);

//       if (flowConfig instanceof ExcelFlow) {
//         ExcelFlow excelFlowConfig = (ExcelFlow) flowConfig;
//         if (excelFlowConfig.getExcelContentType().equals(ExcelContentType.OPERATIONS)) {
//           // if (!notifications.stream()
//           //     .anyMatch(notif ->
// notif.getNotificationStatus().equals(NotificationStatus.FAIL)))
//           irldtos.addAll(irlservice.generateOperationFromNotification(notifications));
//           // else
//           //   throw new BadRequestAlertException(
//           //       "Errors detected when Parsing, please Check Latest Flow with ID ["
//           //           + flow.getId()
//           //           + "]",
//           //       "Notification",
//           //       null);
//         }
//       }
//     }
//   }

//   public List<Notification> generateNotificationsFromFlowConfig(
//       FlowConfig flowConfig, Flow flow, MultipartFile file)
//       throws NoSuchFieldException, SecurityException {
//     List<Notification> notificationsIn = new ArrayList<>();

//     notificationsIn = exceFlowProcessor.inputExcelProcessor(flowConfig, flow, file);

//     return notificationsIn;
//   }

//   /*public void generateSwift(List<IRL> validatedIRLs)
//       throws NoSuchFieldException, SecurityException, IllegalArgumentException,
// IllegalAccessException {

//     List<Notification> swiftNotifications = notificationService.irlsToNotif(validatedIRLs, -1);

//     if (!swiftNotifications.isEmpty()) {

//       for (Notification notification : swiftNotifications) {

//         Flow swiftFlowOut = new Flow();
//         swiftFlowOut.setFlowDate(LocalDate.now());
//         swiftFlowOut.setSens(Sens.OUT);
//         swiftFlowOut.setTotalRecord(0);
//         swiftFlowOut.setFileType("txt");

//         MessageDescriptionSwiftFlow messageDescriptionSwiftFlow = null;

//         if (notification.getCounterPartCode().equals("2245"))
//           messageDescriptionSwiftFlow =
// messageDescriptionSwiftFlowRepository.findByCode(SWIFT_MT542).get(0);
//         else
//           messageDescriptionSwiftFlow =
// messageDescriptionSwiftFlowRepository.findByCode(SWIFT_MT543).get(0);

//         if (messageDescriptionSwiftFlow != null)
//           swiftFlowProcessor.generateSwiftFromNotifications(messageDescriptionSwiftFlow,
// notification, swiftFlowOut);

//       }
//       // if (!swiftFlowOut.getFlowStatus().equals(FlowStatus.SUCCESS))
//       // throw new BadRequestAlertException(
//       // "Errors when Generationg Notifications For Swift, please Check Latest Flow
//       // with ID ["
//       // + flowOut.getId()
//       // + "]",
//       // "FlowConfigService",
//       // null);

//     }

//   }*/
// }
