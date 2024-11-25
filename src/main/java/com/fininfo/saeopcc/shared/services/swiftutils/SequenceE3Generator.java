package com.fininfo.saeopcc.shared.services.swiftutils;

import com.fininfo.saeopcc.shared.domains.enumeration.SwiftBlocSequenceNumber;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagValueTypeList;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocFour;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocSequence;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagField;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field19A;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SequenceE3Generator {

  private final Logger log = LoggerFactory.getLogger(SequenceE3Generator.class);

  private final SwiftUtilitairies swiftUtilitairies;

  public SequenceE3Generator(SwiftUtilitairies swiftUtilitairies) {
    super();
    this.swiftUtilitairies = swiftUtilitairies;
  }

  public void generateSequenceE3(
      SwiftBlocFour swiftBlock4,
      SwiftTagListBlock sequenceESwiftTagListBlock,
      Notification notification,
      Flow swiftFlowOut)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    List<SwiftBlocSequence> swiftBlocSequenceE3List =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.E3))
            .sorted((o1, o2) -> (o1.getId() > o2.getId()) ? 1 : -1)
            .collect(Collectors.toList());

    for (SwiftBlocSequence swiftBlocSequenceE3 : swiftBlocSequenceE3List) {

      // SequenceE3 sequenceE3 = MT540.SequenceE3.newInstance();

      List<SwiftTagField> swiftSequenceE3TagFieldList =
          swiftBlocSequenceE3.getSwiftTagFields().stream().collect(Collectors.toList());

      SwiftTagListBlock sequenceE3SwiftTagListBlock =
          buildSequenceE3SwiftTagListBlock(swiftBlock4, notification, swiftSequenceE3TagFieldList);

      // sequenceE3.append(sequenceE3SwiftTagListBlock);

      sequenceESwiftTagListBlock.append(MT540.SequenceE3.newInstance(sequenceE3SwiftTagListBlock));
    }
  }

  private SwiftTagListBlock buildSequenceE3SwiftTagListBlock(
      SwiftBlocFour swiftBlock4,
      Notification notification,
      List<SwiftTagField> swiftSequenceE3TagFieldList)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftTagListBlock sequenceE3SwiftTagListBlock = new SwiftTagListBlock();
    Map<String, String> swiftTagFieldValuesMap = new HashMap<String, String>();

    swiftSequenceE3TagFieldList =
        swiftSequenceE3TagFieldList.stream()
            .sorted((o1, o2) -> (o1.getId() > o2.getId()) ? 1 : -1)
            .collect(Collectors.toList());
    for (int i = 0; i < swiftSequenceE3TagFieldList.size(); i++) {
      // Field 19A
      SwiftTagField swiftSequenceE3Tag = swiftSequenceE3TagFieldList.get(i);

      if (swiftSequenceE3Tag.getTag().equals("19A")) {
        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceE3Tag, notification);

        Field19A field19a = new Field19A();
        field19a.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
        field19a.setCurrency(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));
        field19a.setAmount(
            swiftUtilitairies.formatNumberFromString(
                swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString())));

        sequenceE3SwiftTagListBlock.append(field19a);
      }
    }

    // TODO: Handle Field 17B

    // TODO: Handle Field 92B

    return sequenceE3SwiftTagListBlock;
  }
}
