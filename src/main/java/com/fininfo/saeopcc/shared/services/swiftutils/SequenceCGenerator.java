package com.fininfo.saeopcc.shared.services.swiftutils;

import com.fininfo.saeopcc.shared.domains.enumeration.SwiftBlocSequenceNumber;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagValueTypeList;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocFour;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocSequence;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagField;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field36B;
import com.prowidesoftware.swift.model.field.Field97A;
import com.prowidesoftware.swift.model.field.Field97B;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceC;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class SequenceCGenerator {

  private final SwiftUtilitairies swiftUtilitairies;

  public SequenceCGenerator(SwiftUtilitairies swiftUtilitairies) {
    super();
    this.swiftUtilitairies = swiftUtilitairies;
  }

  public SequenceC generateSequenceC(
      SwiftBlocFour swiftBlock4, Notification notification, Flow swiftFlowOut)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftBlocSequence swiftBlocSequenceC =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.C))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Sequence C not found"));

    Set<SwiftTagField> swiftSequenceCTagFieldList = swiftBlocSequenceC.getSwiftTagFields();

    SwiftTagListBlock sequenceCSwiftTagListBlock =
        buildSequenceCSwiftTagListBlock(swiftBlock4, notification, swiftSequenceCTagFieldList);

    return MT540.SequenceC.newInstance(sequenceCSwiftTagListBlock);
  }

  private SwiftTagListBlock buildSequenceCSwiftTagListBlock(
      SwiftBlocFour swiftBlock4,
      Notification notification,
      Set<SwiftTagField> swiftSequenceCTagFieldList)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftTagListBlock sequenceCSwiftTagListBlock = new SwiftTagListBlock();
    Map<String, String> swiftTagFieldValuesMap = new HashMap<String, String>();

    // Field 36B

    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceCTagFieldList, "36B", notification);

    Field36B field36b = new Field36B();
    field36b.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
    field36b.setQuantityTypeCode(
        swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));
    field36b.setQuantity(
        swiftUtilitairies.formatNumberFromString(
            swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString())));

    sequenceCSwiftTagListBlock.append(field36b);

    // TODO: Handle Field 70D

    // TODO: Handle Field 13B

    // TODO: Handle Field 95 a with options L, P and R

    // Field 97a with options A and B
    // TODO: Handle 97a with options A, B and E
    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceCTagFieldList, "97A", notification);

    if (swiftTagFieldValuesMap != null) {
      Field97A field97a = new Field97A();
      field97a.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
      field97a.setAccount(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

      sequenceCSwiftTagListBlock.append(field97a);
    }
    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceCTagFieldList, "97B", notification);
    if (swiftTagFieldValuesMap != null) {
      Field97B field97b = new Field97B();
      field97b.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
      // TODO: add Field97B data source schema
      // field97b.setAccountTypeCode(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));
      field97b.setAccountNumber(
          swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

      sequenceCSwiftTagListBlock.append(field97b);
    }

    // TODO: Handle 94a with options B, C, F, L

    return sequenceCSwiftTagListBlock;
  }
}
