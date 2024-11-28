package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.ExcelContentType;
import com.fininfo.saeopcc.multitenancy.domains.flow.ExcelFlow;
import com.fininfo.saeopcc.multitenancy.domains.flow.Flow;
import com.fininfo.saeopcc.multitenancy.domains.flow.FlowConfig;
import com.fininfo.saeopcc.multitenancy.domains.flow.Notification;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
import com.fininfo.saeopcc.multitenancy.services.processors.ExcelFlowProcessor;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@Slf4j
public class FlowConfigService {

  @Autowired private ExcelFlowProcessor exceFlowProcessor;
  @Autowired private FlowService flowService;

  @Autowired private SubscriptionService subscriptionService;

  public void globalFlowIntegrationProcess(
      FlowConfig flowConfig, Flow flow, MultipartFile file, List<SubscriptionDTO> subscriptionsDTOs)
      throws NoSuchFieldException, SecurityException {
    log.info("***** Entering Global Broker Process Method *****");

    log.info("**** Entering Generating Notification Process ****");
    List<Notification> notifications = generateNotificationsFromFlowConfig(flowConfig, flow, file);

    log.debug("**** Generated Notifications : {}", notifications);

    if (!notifications.isEmpty() && flow.getFlowStatus() == null) {

      flowService.saveFlowWithNotification(notifications, flow);

      if (flowConfig instanceof ExcelFlow) {
        ExcelFlow excelFlowConfig = (ExcelFlow) flowConfig;
        if (excelFlowConfig.getExcelContentType().equals(ExcelContentType.SUBSCRIPTIONS)) {

          subscriptionsDTOs.addAll(
              subscriptionService.generateSubscriptionFromNotification(notifications));
        }
      }
    }
  }

  public List<Notification> generateNotificationsFromFlowConfig(
      FlowConfig flowConfig, Flow flow, MultipartFile file)
      throws NoSuchFieldException, SecurityException {
    List<Notification> notificationsIn;

    notificationsIn = exceFlowProcessor.inputExcelProcessor(flowConfig, flow, file);

    return notificationsIn;
  }
}
