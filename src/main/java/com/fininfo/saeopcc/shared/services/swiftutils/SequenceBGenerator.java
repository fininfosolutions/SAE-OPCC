package com.fininfo.saeopcc.shared.services.swiftutils;

import com.fininfo.saeopcc.shared.domains.enumeration.SwiftBlocSequenceNumber;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagValueTypeList;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocFour;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocSequence;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagField;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field35B;
import com.prowidesoftware.swift.model.field.Field98A;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceB;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SequenceBGenerator {

  private final Logger log = LoggerFactory.getLogger(SequenceBGenerator.class);

  private final SwiftUtilitairies swiftUtilitairies;

  public SequenceBGenerator(SwiftUtilitairies swiftUtilitairies) {
    super();
    this.swiftUtilitairies = swiftUtilitairies;
  }

  public SequenceB generateSequenceB(
      SwiftBlocFour swiftBlock4, Notification notification, Flow swiftFlowOut)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftBlocSequence swiftBlocSequenceB =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.B))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Sequence B not found"));

    // SequenceB sequenceB = MT540.SequenceB.newInstance();

    // Set<SwiftTagField> swiftSequenceBTagFieldList = swiftBlocSequenceB.getSwiftTagFields();

    List<SwiftTagField> swiftSequenceBTagFieldList =
        swiftBlocSequenceB.getSwiftTagFields().stream().collect(Collectors.toList());

    SwiftTagListBlock sequenceBSwiftTagListBlock =
        buildSequenceBSwiftTagListBlock(swiftBlock4, notification, swiftSequenceBTagFieldList);

    // sequenceB.append(sequenceBSwiftTagListBlock);

    // TODO: Handle subSequences B1

    return MT540.SequenceB.newInstance(sequenceBSwiftTagListBlock);
  }

  private SwiftTagListBlock buildSequenceBSwiftTagListBlock(
      SwiftBlocFour swiftBlock4,
      Notification notification,
      List<SwiftTagField> swiftSequenceBTagFieldList)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftTagListBlock sequenceBSwiftTagListBlock = new SwiftTagListBlock();
    // List<Map<String, String>> swiftTagFieldValuesMapList = new ArrayList<Map<String, String>>();
    Map<String, String> swiftTagFieldValuesMap = new HashMap<String, String>();

    swiftSequenceBTagFieldList =
        swiftSequenceBTagFieldList.stream()
            .sorted((o1, o2) -> (o1.getId() > o2.getId()) ? 1 : -1)
            .collect(Collectors.toList());

    for (int i = 0; i < swiftSequenceBTagFieldList.size(); i++) {

      SwiftTagField swiftSequenceBTag = swiftSequenceBTagFieldList.get(i);

      // Field 98A
      if (swiftSequenceBTag.getTag().equals("98A")) {
        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceBTag, notification);
        Field98A field98a = new Field98A();
        field98a.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
        field98a.setDate(
            swiftUtilitairies.formatDateFromString(
                swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString())));
        sequenceBSwiftTagListBlock.append(field98a);
      }

      // Field 35B
      if (swiftSequenceBTag.getTag().equals("35B")) {
        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceBTag, notification);
        Field35B field35b = new Field35B();
        field35b.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
        field35b.setISIN(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));
        sequenceBSwiftTagListBlock.append(field35b);
      }
    }
    return sequenceBSwiftTagListBlock;
  }
}
