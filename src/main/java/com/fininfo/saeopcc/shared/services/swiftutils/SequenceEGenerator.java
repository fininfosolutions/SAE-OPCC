package com.fininfo.saeopcc.shared.services.swiftutils;

import com.fininfo.saeopcc.shared.domains.enumeration.SwiftBlocSequenceNumber;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagValueTypeList;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocFour;
import com.fininfo.saeopcc.shared.domains.swift.SwiftBlocSequence;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagField;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field22F;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceE;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SequenceEGenerator {

  private final SwiftUtilitairies swiftUtilitairies;

  private final SequenceE1Generator sequenceE1Generator;

  private final SequenceE3Generator sequenceE3Generator;

  public SequenceEGenerator(
      SwiftUtilitairies swiftUtilitairies,
      SequenceE1Generator sequenceE1Generator,
      SequenceE3Generator sequenceE3Generator) {
    super();
    this.swiftUtilitairies = swiftUtilitairies;
    this.sequenceE1Generator = sequenceE1Generator;
    this.sequenceE3Generator = sequenceE3Generator;
  }

  public SequenceE generateSequenceE(
      SwiftBlocFour swiftBlock4, Notification notification, Flow swiftFlowOut, String swiftype)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    Optional<SwiftBlocSequence> optionalSequence =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.E))
            .findFirst();

    if (optionalSequence.isPresent()) {
      SwiftBlocSequence swiftBlocSequenceE = optionalSequence.get();

      List<SwiftTagField> swiftSequenceETagFieldList =
          swiftBlocSequenceE.getSwiftTagFields().stream().collect(Collectors.toList());

      SwiftTagListBlock sequenceESwiftTagListBlock =
          buildSequenceESwiftTagListBlock(notification, swiftSequenceETagFieldList);

      sequenceE1Generator.generateSequenceE1(
          swiftBlock4, sequenceESwiftTagListBlock, notification, swiftFlowOut, swiftype);
      if (notification.getGrossAmount() != null) {
        sequenceE3Generator.generateSequenceE3(
            swiftBlock4, sequenceESwiftTagListBlock, notification, swiftFlowOut);
      }

      return MT540.SequenceE.newInstance(sequenceESwiftTagListBlock);
    } else {
      throw new NoSuchElementException("Sequence E not found in SwiftBlocFour");
    }
  }

  private SwiftTagListBlock buildSequenceESwiftTagListBlock(
      Notification notification, List<SwiftTagField> swiftSequenceETagFieldList)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftTagListBlock sequenceESwiftTagListBlock = new SwiftTagListBlock();
    Map<String, String> swiftTagFieldValuesMap;

    swiftSequenceETagFieldList =
        swiftSequenceETagFieldList.stream()
            .sorted((o1, o2) -> (o1.getId() > o2.getId()) ? 1 : -1)
            .collect(Collectors.toList());

    for (int i = 0; i < swiftSequenceETagFieldList.size(); i++) {

      SwiftTagField swiftSequenceETag = swiftSequenceETagFieldList.get(i);

      if (swiftSequenceETag.getTag().equals("22F")) {

        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceETag, notification);
        Field22F field22f = new Field22F();
        field22f.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
        field22f.setDataSourceScheme(
            swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));
        field22f.setIndicator(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

        sequenceESwiftTagListBlock.append(field22f);
      }
    }

    return sequenceESwiftTagListBlock;
  }
}
