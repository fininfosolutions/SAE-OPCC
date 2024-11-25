package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.flow.Flow;
import com.fininfo.saeopcc.multitenancy.domains.flow.Notification;
import com.fininfo.saeopcc.multitenancy.repositories.NotificationRepository;
import com.fininfo.saeopcc.shared.domains.enumeration.Sens;
import java.time.LocalDate;
import java.time.LocalTime;
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

  private boolean isValidTimeRange(String value) {
    String[] timeRange = value.split("-");
    if (timeRange.length == 2) {

      String startTimeString = timeRange[0].trim();

      String endTimeString = timeRange[1].trim();

      LocalTime startTime = LocalTime.parse(startTimeString);
      LocalTime endTime = LocalTime.parse(endTimeString);

      LocalTime currentTime = LocalTime.now();

      return currentTime.isAfter(startTime) && currentTime.isBefore(endTime);
    } else {
      return false;
    }
  }

  private String adjustValue(String value, int length) {
    String aditionaleZeros = "0".repeat(length - value.length());
    return value.replace(value, aditionaleZeros.concat(value));
  }

  private String adjustBIC(String value, int length) {
    String aditionaleX = "X".repeat(length - value.length());
    return value.replace(value, value.concat(aditionaleX)).toUpperCase();
  }
}
