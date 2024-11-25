package com.fininfo.saeopcc.shared.services.swiftutils;

import com.fininfo.saeopcc.shared.domains.enumeration.SwiftBlocSequenceNumber;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagValueTypeList;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocFour;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocSequence;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagField;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field70D;
import com.prowidesoftware.swift.model.field.Field95P;
import com.prowidesoftware.swift.model.field.Field95R;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SequenceE1Generator {

  private final Logger log = LoggerFactory.getLogger(SequenceE1Generator.class);

  private final SwiftUtilitairies swiftUtilitairies;

  public SequenceE1Generator(SwiftUtilitairies swiftUtilitairies) {
    super();
    this.swiftUtilitairies = swiftUtilitairies;
  }

  public void generateSequenceE1(
      SwiftBlocFour swiftBlock4,
      SwiftTagListBlock sequenceESwiftTagListBlock,
      Notification notification,
      Flow swiftFlowOut,
      String swiftype)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    List<SwiftBlocSequence> swiftBlocSequenceE1List =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.E1))
            .sorted((o1, o2) -> (o1.getId() > o2.getId()) ? 1 : -1)
            .collect(Collectors.toList());

    for (SwiftBlocSequence swiftBlocSequenceE1 : swiftBlocSequenceE1List) {

      // SequenceE1 sequenceE1 = MT540.SequenceE1.newInstance();

      List<SwiftTagField> swiftSequenceE1TagFieldList =
          swiftBlocSequenceE1.getSwiftTagFields().stream().collect(Collectors.toList());

      SwiftTagListBlock sequenceE1SwiftTagListBlock =
          buildSequenceE1SwiftTagListBlock(
              swiftBlock4, notification, swiftSequenceE1TagFieldList, swiftype);

      // sequenceE1.append(sequenceE1SwiftTagListBlock);

      sequenceESwiftTagListBlock.append(MT540.SequenceE1.newInstance(sequenceE1SwiftTagListBlock));
    }
  }

  private SwiftTagListBlock buildSequenceE1SwiftTagListBlock(
      SwiftBlocFour swiftBlock4,
      Notification notification,
      List<SwiftTagField> swiftSequenceE1TagFieldList,
      String swiftype)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftTagListBlock sequenceE1SwiftTagListBlock = new SwiftTagListBlock();
    Map<String, String> swiftTagFieldValuesMap = new HashMap<String, String>();
    List<Map<String, String>> swiftTagFieldValuesMapList = new ArrayList<Map<String, String>>();

    swiftSequenceE1TagFieldList =
        swiftSequenceE1TagFieldList.stream()
            .sorted((o1, o2) -> (o1.getId() > o2.getId()) ? 1 : -1)
            .collect(Collectors.toList());
    for (int i = 0; i < swiftSequenceE1TagFieldList.size(); i++) {
      SwiftTagField swiftSequenceE1Tag = swiftSequenceE1TagFieldList.get(i);
      // 95R
      if (swiftSequenceE1Tag.getTag().equals("95R")) {
        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceE1Tag, notification);

        Field95R field95r = new Field95R();
        field95r.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
        field95r.setDataSourceScheme(
            swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));
        field95r.setProprietaryCode(
            swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

        sequenceE1SwiftTagListBlock.append(field95r);
      }

      // 95P
      if (swiftSequenceE1Tag.getTag().equals("95P")) {
        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceE1Tag, notification);

        if (swiftTagFieldValuesMap != null) {
          Field95P field95p = new Field95P();
          field95p.setQualifier(
              swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
          field95p.setBIC(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

          sequenceE1SwiftTagListBlock.append(field95p);
        }
      }

      // 70D
      if (swiftSequenceE1Tag.getTag().equals("70D")) {
        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceE1Tag, notification);
        if (swiftTagFieldValuesMap != null) {
          Field70D field70d = new Field70D();
          if (swiftype.equals("MT543")) {
            field70d.setComponent(1, "REGI");
            field70d.setComponent(2, "CLIENTTYPE/FORE");
            field70d.setComponent(3, "/OPERATIONTYPE/PRDB");
          }

          if (swiftype.equals("MT542")) {
            field70d.setComponent(1, "REGI");
            field70d.setComponent(2, "CLIENTTYPE/OTHR");
            field70d.setComponent(3, "/OPERATIONTYPE/PRDB");
          }

          sequenceE1SwiftTagListBlock.append(field70d);
        }
      }
    }

    return sequenceE1SwiftTagListBlock;
  }
}
