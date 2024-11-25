package com.fininfo.saeopcc.shared.services.swiftutils;

import com.fininfo.saeopcc.shared.domains.enumeration.SwiftBlocSequenceNumber;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagValueTypeList;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocFour;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocSequence;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagField;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field20C;
import com.prowidesoftware.swift.model.field.Field23G;
import com.prowidesoftware.swift.model.field.Field98C;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceA;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class SequenceAGenerator {

  private final SwiftUtilitairies swiftUtilitairies;

  public SequenceAGenerator(SwiftUtilitairies swiftUtilitairies) {
    super();
    this.swiftUtilitairies = swiftUtilitairies;
  }

  public SequenceA generateSequenceA(
      SwiftBlocFour swiftBlock4, Notification notification, Flow swiftFlowOut)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftBlocSequence swiftBlocSequenceA =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.A))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Sequence A not found"));

    Set<SwiftTagField> swiftSequenceATagFieldList = swiftBlocSequenceA.getSwiftTagFields();

    SwiftTagListBlock sequenceASwiftTagListBlock =
        buildSequenceASwiftTagListBlock(swiftBlock4, notification, swiftSequenceATagFieldList);

    return MT540.SequenceA.newInstance(sequenceASwiftTagListBlock);
  }

  private SwiftTagListBlock buildSequenceASwiftTagListBlock(
      SwiftBlocFour swiftBlock4,
      Notification notification,
      Set<SwiftTagField> swiftSequenceATagFieldList)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftTagListBlock sequenceASwiftTagListBlock = new SwiftTagListBlock();
    Map<String, String> swiftTagFieldValuesMap = new HashMap<String, String>();

    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceATagFieldList, "20C", notification);
    Field20C field20c = new Field20C();

    field20c.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));

    field20c.setReference(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

    sequenceASwiftTagListBlock.append(field20c);

    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceATagFieldList, "23G", notification);

    Field23G field23g = new Field23G();
    field23g.setFunction(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
    field23g.setSubfunction(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));

    sequenceASwiftTagListBlock.append(field23g);

    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceATagFieldList, "98C", notification);

    Field98C field98c = new Field98C();
    field98c.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
    field98c.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    field98c.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHMMSS")));

    sequenceASwiftTagListBlock.append(field98c);

    return sequenceASwiftTagListBlock;
  }
}
