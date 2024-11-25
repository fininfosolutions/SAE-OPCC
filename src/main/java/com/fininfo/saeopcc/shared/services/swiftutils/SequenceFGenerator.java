package com.fininfo.saeopcc.shared.services.swiftutils;

import com.fininfo.saeopcc.shared.domains.enumeration.SwiftBlocSequenceNumber;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagValueTypeList;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocFour;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocSequence;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagField;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field95R;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SequenceFGenerator {

  private final SwiftUtilitairies swiftUtilitairies;

  public SequenceFGenerator(SwiftUtilitairies swiftUtilitairies) {
    super();
    this.swiftUtilitairies = swiftUtilitairies;
  }

  public void generateSequenceF(
      SwiftBlocFour swiftBlock4, MT540 mt540, Notification notification, Flow swiftFlowOut)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    List<SwiftBlocSequence> swiftBlocSequenceFList =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.F))
            .sorted((o1, o2) -> (o1.getId() > o2.getId()) ? 1 : -1)
            .collect(Collectors.toList());

    for (SwiftBlocSequence swiftBlocSequenceF : swiftBlocSequenceFList) {

      // SequenceF sequenceF = MT540.SequenceF.newInstance();

      List<SwiftTagField> swiftSequenceFTagFieldList =
          swiftBlocSequenceF.getSwiftTagFields().stream().collect(Collectors.toList());

      SwiftTagListBlock sequenceFSwiftTagListBlock =
          buildSequenceFSwiftTagListBlock(swiftBlock4, notification, swiftSequenceFTagFieldList);

      // sequenceF.append(sequenceFSwiftTagListBlock);

      mt540.append(MT540.SequenceF.newInstance(sequenceFSwiftTagListBlock));
    }
  }

  private SwiftTagListBlock buildSequenceFSwiftTagListBlock(
      SwiftBlocFour swiftBlock4,
      Notification notification,
      List<SwiftTagField> swiftSequenceFTagFieldList)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftTagListBlock sequenceFSwiftTagListBlock = new SwiftTagListBlock();
    Map<String, String> swiftTagFieldValuesMap = new HashMap<String, String>();

    /// Field 95A
    swiftSequenceFTagFieldList =
        swiftSequenceFTagFieldList.stream()
            .sorted((f1, f2) -> (f1.getId() > f2.getId()) ? 1 : -1)
            .collect(Collectors.toList());

    for (int i = 0; i < swiftSequenceFTagFieldList.size(); i++) {

      SwiftTagField swiftSequenceFTag = swiftSequenceFTagFieldList.get(i);

      if (swiftSequenceFTag.getTag().equals("95R")) {

        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceFTag, notification);
        Field95R field95r = new Field95R();
        field95r.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
        field95r.setDataSourceScheme(
            swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));
        field95r.setProprietaryCode(
            swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));
        sequenceFSwiftTagListBlock.append(field95r);
      }
    }

    // Field 95a
    // TODO: Handle Field95a Options C, L, P, Q, and S

    // TODO: Handle Field97A

    // TODO: Handle Field70a Options C, D, E

    // TODO: Handle Field20C

    return sequenceFSwiftTagListBlock;
  }
}
