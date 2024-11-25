package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.enumeration.Sens;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.repositories.NotificationRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

  @Autowired private NotificationRepository notificationRepository;

  @Autowired private FlowService flowService;

  public Notification getById(Long id) {
    return notificationRepository.findById(id).orElse(null);
  }

  public Flow saveFlowWithNotif(List<Notification> notifications) {
    Flow flowOut = new Flow();
    flowOut.setFlowDate(LocalDate.now());
    flowOut.setSens(Sens.IN);
    flowOut.setTotalRecord(0);
    return flowService.saveFlowWithNotification(notifications, flowOut);
  }
}
