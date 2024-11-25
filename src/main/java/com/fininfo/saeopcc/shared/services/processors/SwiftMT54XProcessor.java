package com.fininfo.saeopcc.shared.services.processors;

import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.MessageDescriptionSwiftFlow;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocFour;
import com.fininfo.saeopcc.shared.services.FlowService;
import com.fininfo.saeopcc.shared.services.swiftutils.SequenceAGenerator;
import com.fininfo.saeopcc.shared.services.swiftutils.SequenceBGenerator;
import com.fininfo.saeopcc.shared.services.swiftutils.SequenceCGenerator;
import com.fininfo.saeopcc.shared.services.swiftutils.SequenceDGenerator;
import com.fininfo.saeopcc.shared.services.swiftutils.SequenceEGenerator;
import com.fininfo.saeopcc.shared.services.swiftutils.SequenceFGenerator;
import com.prowidesoftware.swift.model.SwiftBlock1;
import com.prowidesoftware.swift.model.SwiftBlock2;
import com.prowidesoftware.swift.model.SwiftBlock3;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceA;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceB;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceC;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceD;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceE;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class SwiftMT54XProcessor {

  private final FlowService flowService;

  private final SequenceAGenerator sequenceAGenerator;

  private final SequenceBGenerator sequenceBGenerator;

  private final SequenceCGenerator sequenceCGenerator;

  private final SequenceDGenerator sequenceDGenerator;

  private final SequenceEGenerator sequenceEGenerator;

  private final SequenceFGenerator sequenceFGenerator;

  public SwiftMT54XProcessor(
      FlowService flowService,
      SequenceAGenerator sequenceAGenerator,
      SequenceBGenerator sequenceBGenerator,
      SequenceCGenerator sequenceCGenerator,
      SequenceDGenerator sequenceDGenerator,
      SequenceEGenerator sequenceEGenerator,
      SequenceFGenerator sequenceFGenerator) {

    super();
    this.flowService = flowService;
    this.sequenceAGenerator = sequenceAGenerator;
    this.sequenceBGenerator = sequenceBGenerator;
    this.sequenceCGenerator = sequenceCGenerator;
    this.sequenceDGenerator = sequenceDGenerator;
    this.sequenceEGenerator = sequenceEGenerator;
    this.sequenceFGenerator = sequenceFGenerator;
  }

  public void generateMT54XFromNotifications(
      SwiftBlock1 block1,
      SwiftBlock2 block2,
      SwiftBlock3 block3,
      MessageDescriptionSwiftFlow messageDescriptionSwiftFlow,
      Notification notification,
      Flow swiftFlowOut,
      Long murValueDocument)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    MT540 mt540 = new MT540();

    mt540.getSwiftMessage().setBlock1(block1);
    mt540.getSwiftMessage().setBlock2(block2);
    mt540.getSwiftMessage().setBlock3(block3);

    SwiftBlocFour swiftBlock4 = messageDescriptionSwiftFlow.getSwiftBlocFour();

    String swiftype = messageDescriptionSwiftFlow.getCode();

    if (swiftBlock4 != null) {

      SequenceA sequenceA =
          sequenceAGenerator.generateSequenceA(swiftBlock4, notification, swiftFlowOut);

      SequenceB sequenceB =
          sequenceBGenerator.generateSequenceB(swiftBlock4, notification, swiftFlowOut);

      SequenceC sequenceC =
          sequenceCGenerator.generateSequenceC(swiftBlock4, notification, swiftFlowOut);

      SequenceD sequenceD =
          sequenceDGenerator.generateSequenceD(swiftBlock4, notification, swiftFlowOut);

      SequenceE sequenceE =
          sequenceEGenerator.generateSequenceE(swiftBlock4, notification, swiftFlowOut, swiftype);
      if (sequenceD != null) {
        mt540
            .append(sequenceA)
            .append(sequenceB)
            .append(sequenceC)
            .append(sequenceD)
            .append(sequenceE);
      } else {
        mt540.append(sequenceA).append(sequenceB).append(sequenceC).append(sequenceE);
      }
      sequenceFGenerator.generateSequenceF(swiftBlock4, mt540, notification, swiftFlowOut);

      if (mt540.message() != null && !mt540.message().isEmpty()) {

        String murString = String.format("%07d", murValueDocument);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String fileName =
            "SWIFT_"
                + messageDescriptionSwiftFlow.getCode()
                + "_0141_0001_"
                + formatter.format(LocalDate.now())
                + "_"
                + murString
                + ".txt";

        flowService.saveSwiftFlowOut(
            mt540.message().getBytes(StandardCharsets.UTF_8),
            swiftFlowOut,
            notification.getTransactionNumber(),
            murValueDocument,
            fileName);
      }
    }
  }
}
