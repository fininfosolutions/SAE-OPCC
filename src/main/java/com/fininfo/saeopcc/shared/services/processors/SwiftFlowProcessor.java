package com.fininfo.saeopcc.shared.services.processors;

import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.MessageDescriptionSwiftFlow;
import com.fininfo.saeopcc.shared.repositories.DocumentRepository;
import com.prowidesoftware.swift.model.LogicalTerminalAddress;
import com.prowidesoftware.swift.model.SwiftBlock1;
import com.prowidesoftware.swift.model.SwiftBlock2;
import com.prowidesoftware.swift.model.SwiftBlock2Output;
import com.prowidesoftware.swift.model.SwiftBlock3;
import com.prowidesoftware.swift.model.field.Field108;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwiftFlowProcessor {

  @Autowired private DocumentRepository documentRepository;

  @Autowired SwiftMT54XProcessor swiftMT54XProcessor;

  public void generateSwiftFromNotifications(
      MessageDescriptionSwiftFlow messageDescriptionSwiftFlow,
      Notification notification,
      Flow swiftFlowOut)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftBlock1 block1 =
        new SwiftBlock1(
            "F", "01", notification.getSettPartPlaceOfSettlementPropertyCode(), "0000", "000000");
    SwiftBlock2 block2 =
        generateSwiftBlocTwo(messageDescriptionSwiftFlow, notification, swiftFlowOut);
    SwiftBlock3 block3 =
        generateSwiftBlocThree(messageDescriptionSwiftFlow, notification, swiftFlowOut);
    Long murDocumentValue = this.murValue();
    swiftMT54XProcessor.generateMT54XFromNotifications(
        block1,
        block2,
        block3,
        messageDescriptionSwiftFlow,
        notification,
        swiftFlowOut,
        murDocumentValue);
  }

  private SwiftBlock2 generateSwiftBlocTwo(
      MessageDescriptionSwiftFlow messageDescriptionSwiftFlow,
      Notification notification,
      Flow swiftFlowOut) {

    SwiftBlock2Output b2Output = new SwiftBlock2Output();
    b2Output.setMessageType(messageDescriptionSwiftFlow.getCode().substring(2));
    b2Output.setSenderInputTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm")));
    b2Output.setMIRDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd")));
    LogicalTerminalAddress logicalTerminalAddress =
        new LogicalTerminalAddress(notification.getSettPartPlaceOfSettlementIdentifier());
    b2Output.setMIRLogicalTerminal(logicalTerminalAddress);
    b2Output.setMIRSequenceNumber("0000");
    b2Output.setMIRSessionNumber("000000");
    b2Output.setReceiverOutputDate(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd")));
    b2Output.setReceiverOutputTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm")));
    b2Output.setMessagePriority("N");
    return b2Output;
  }

  private SwiftBlock3 generateSwiftBlocThree(
      MessageDescriptionSwiftFlow messageDescriptionSwiftFlow,
      Notification notification,
      Flow swiftFlowOut) {

    SwiftBlock3 block3 = new SwiftBlock3();
    Field108 field108 = new Field108();
    Long newMur = this.murValue();
    String murString = "S" + String.format("%015d", newMur);
    field108.setMUR(murString);
    block3.builder().setField108(field108);

    return block3;
  }

  private Long murValue() {
    Optional<Long> maxMur = documentRepository.findMaxMur();
    Long mapping = (maxMur.isPresent() ? maxMur.get() : null);
    Long newMur = 000000000000000L;
    if (mapping != null) {
      newMur = ++mapping;
    }
    return newMur;
  }
}
