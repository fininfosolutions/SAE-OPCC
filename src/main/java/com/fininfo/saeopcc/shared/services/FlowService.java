package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.enumeration.FlowStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.NotificationStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.Sens;
import com.fininfo.saeopcc.shared.domains.flow.Document;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.flow.NotificationError;
import com.fininfo.saeopcc.shared.repositories.DocumentRepository;
import com.fininfo.saeopcc.shared.repositories.FlowRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlowService {

  @Autowired FlowRepository flowRepository;

  @Autowired private DocumentRepository documentRepository;

  public Document findByReference(String reference) {
    Optional<Document> document = documentRepository.findByReferenceNotif(reference);
    if (document.isPresent()) return document.get();
    else return null;
  }

  public Flow saveFlowWithNotification(List<Notification> notifs, Flow flow) {

    loopOverNotification(notifs, flow);

    boolean isFlowWithError =
        notifs.stream()
            .anyMatch(
                obj ->
                    (obj.getNotificationErrors() != null && !obj.getNotificationErrors().isEmpty())
                        || obj.getNotificationStatus() != NotificationStatus.SUCCESS);

    flow.setTotalRecord(notifs.size());

    flow.setNbreOfFailedRecord(
        Math.toIntExact(
            notifs.stream()
                .filter(x -> x.getNotificationStatus().equals(NotificationStatus.FAIL))
                .count()));

    flow.setNbreOfValidRecord(
        Math.toIntExact(
            notifs.stream()
                .filter(x -> x.getNotificationStatus().equals(NotificationStatus.SUCCESS))
                .count()));

    if (isFlowWithError) {
      flow.setFlowStatus(FlowStatus.WITH_ERRORS);
    } else {
      flow.setFlowStatus(FlowStatus.SUCCESS);
    }

    for (Notification notification : notifs) {

      notification.setFlow(flow);

      if (notification.getNotificationErrors() != null
          && !notification.getNotificationErrors().isEmpty()) {
        for (NotificationError error : notification.getNotificationErrors()) {
          error.setNotification(notification);
        }
      }
    }

    flow.setNotifications(new HashSet<>(notifs));
    flow = flowRepository.save(flow);

    return flow;
  }

  private void loopOverNotification(List<Notification> notifs, Flow flow) {
    notifs.forEach(
        f -> {
          if (f.getNotificationStatus() == null) {

            f.setNotificationStatus(NotificationStatus.SUCCESS);
          }

          if (flow.getSens().equals(Sens.IN)) {
            f.setSens(Sens.IN);
          } else {

            f.setSens(Sens.OUT);
          }
        });
  }

  public Flow saveSwiftFlowOut(
      byte[] bytes,
      Flow flowOut,
      String transactionNumber,
      Long murValueDocument,
      String fileName) {
    Set<Document> documents = new HashSet<>();
    Document document = new Document();
    flowOut.setNotifications(null);
    flowOut.setFlowStatus(FlowStatus.SUCCESS);
    document.setTitle(fileName);
    document.setSize(0L);
    document.setMimeType("text/plain; charset=UTF-8");
    document.setFlow(flowOut);
    document.setReferenceNotif(transactionNumber);
    document.setContent(bytes);
    document.setMur(murValueDocument);
    documents.add(document);
    flowOut.setDocuments(documents);
    flowOut = flowRepository.save(flowOut);
    return flowOut;
  }
}
