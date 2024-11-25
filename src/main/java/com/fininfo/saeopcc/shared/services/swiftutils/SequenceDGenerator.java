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
import com.prowidesoftware.swift.model.field.Field20C;
import com.prowidesoftware.swift.model.field.Field22F;
import com.prowidesoftware.swift.model.field.Field92A;
import com.prowidesoftware.swift.model.field.Field98A;
import com.prowidesoftware.swift.model.field.Field99B;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540.SequenceD;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SequenceDGenerator {

  private final Logger log = LoggerFactory.getLogger(SequenceDGenerator.class);

  private final SwiftUtilitairies swiftUtilitairies;

  public SequenceDGenerator(SwiftUtilitairies swiftUtilitairies) {
    super();
    this.swiftUtilitairies = swiftUtilitairies;
  }

  public SequenceD generateSequenceD(
      SwiftBlocFour swiftBlock4, Notification notification, Flow swiftFlowOut)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    Optional<SwiftBlocSequence> swiftBlocSequenceDOpt =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.D))
            .findAny();

    if (swiftBlocSequenceDOpt.isPresent()) {
      Set<SwiftTagField> swiftSequenceDTagFieldList =
          swiftBlocSequenceDOpt.get().getSwiftTagFields();
      SwiftTagListBlock sequenceDSwiftTagListBlock =
          buildsequenceDSwiftTagListBlock(swiftBlock4, notification, swiftSequenceDTagFieldList);
      return MT540.SequenceD.newInstance(sequenceDSwiftTagListBlock);
    } else return null;
  }

  private SwiftTagListBlock buildsequenceDSwiftTagListBlock(
      SwiftBlocFour swiftBlock4,
      Notification notification,
      Set<SwiftTagField> swiftSequenceDTagFieldList)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    SwiftTagListBlock sequenceDSwiftTagListBlock = new SwiftTagListBlock();
    Map<String, String> swiftTagFieldValuesMap = new HashMap<String, String>();

    // FIELD 98A
    List<Map<String, String>> swiftTagFieldValuesMapList = new ArrayList<Map<String, String>>();

    swiftTagFieldValuesMapList =
        swiftUtilitairies.getSwiftTagFieldValuesList(
            swiftSequenceDTagFieldList, "98A", notification);
    for (Map<String, String> swiftTagFieldValuesMapElement : swiftTagFieldValuesMapList) {
      Field98A field98a = new Field98A();
      field98a.setQualifier(
          swiftTagFieldValuesMapElement.get(SwiftTagValueTypeList.SUFFIX1.toString()));
      field98a.setDate(
          swiftUtilitairies.formatDateFromString(
              swiftTagFieldValuesMapElement.get(SwiftTagValueTypeList.CONTENT.toString())));
      sequenceDSwiftTagListBlock.append(field98a);
    }

    // FIELD22F
    swiftTagFieldValuesMapList =
        swiftUtilitairies.getSwiftTagFieldValuesList(
            swiftSequenceDTagFieldList, "22F", notification);

    for (Map<String, String> swiftTagFieldValuesMapElement : swiftTagFieldValuesMapList) {

      Field22F field22f = new Field22F();
      // field22f.setIndicator(swiftTagFieldValuesMapElement.get(SwiftTagValueTypeList.SUFFIX1.toString()));
      // field22f.setQualifier(swiftTagFieldValuesMapElement.get(SwiftTagValueTypeList.CONTENT.toString()));

      field22f.setQualifier(
          swiftTagFieldValuesMapElement.get(SwiftTagValueTypeList.SUFFIX1.toString()));
      field22f.setIndicator(
          swiftTagFieldValuesMapElement.get(SwiftTagValueTypeList.CONTENT.toString()));

      sequenceDSwiftTagListBlock.append(field22f);
    }

    // FIELD20C
    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceDTagFieldList, "20C", notification);
    Field20C field20c = new Field20C();

    field20c.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));

    field20c.setReference(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

    sequenceDSwiftTagListBlock.append(field20c);

    // FILED 92a
    // swiftTagFieldValuesMapList =
    // swiftUtilitairies.getSwiftTagFieldValuesList(swiftSequenceDTagFieldList, "92A",
    // notification);

    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceDTagFieldList, "92A", notification);

    Field92A field92a = new Field92A();
    field92a.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
    field92a.setRateAmount(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

    sequenceDSwiftTagListBlock.append(field92a);
    // FILED 99b
    swiftTagFieldValuesMap =
        swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceDTagFieldList, "99B", notification);
    Field99B field99b = new Field99B();
    field99b.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
    field99b.setNumber(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

    sequenceDSwiftTagListBlock.append(field99b);

    SwiftBlocSequence swiftBlocSequenceD =
        swiftBlock4.getSwiftBlocSequences().stream()
            .filter(sequence -> sequence.getSequenceNumber().equals(SwiftBlocSequenceNumber.D))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Sequence D not found"));

    List<SwiftTagField> swiftSequenceDTagFieldLists =
        swiftBlocSequenceD.getSwiftTagFields().stream().collect(Collectors.toList());

    swiftSequenceDTagFieldLists =
        swiftSequenceDTagFieldLists.stream()
            .sorted((d1, d2) -> (d1.getId() > d2.getId()) ? 1 : -1)
            .collect(Collectors.toList());

    for (int i = 0; i < swiftSequenceDTagFieldLists.size(); i++) {

      SwiftTagField swiftSequenceETag = swiftSequenceDTagFieldLists.get(i);

      if (swiftSequenceETag.getTag().equals("19A")) {
        swiftTagFieldValuesMap =
            swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceETag, notification);
        Field19A field19a = new Field19A();
        field19a.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
        field19a.setCurrency(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));
        field19a.setAmount(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));
        sequenceDSwiftTagListBlock.append(field19a);
      }
    }

    // Field 19A

    //   swiftTagFieldValuesMap =
    // swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceDTagFieldList, "19A",
    //   notification);

    //   Field19A field19a2 = new Field19A();

    //
    // field19a2.setQualifier(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
    //
    // field19a2.setCurrency(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX2.toString()));
    //   field19a2.setAmount(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

    //   sequenceDSwiftTagListBlock.append(field19a2);

    // Field 16S

    //     swiftTagFieldValuesMap =
    // swiftUtilitairies.getSwiftTagFieldValues(swiftSequenceDTagFieldList, "16S",
    //     notification);

    //     Field16S field16s = new Field16S();

    //
    // field16s.setBlockName(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.SUFFIX1.toString()));
    //
    // field16s.setComponent1(swiftTagFieldValuesMap.get(SwiftTagValueTypeList.CONTENT.toString()));

    //     sequenceDSwiftTagListBlock.append(field16s);
    // TODO: Handle Field 99B

    // TODO: Handle subSequences A1

    return sequenceDSwiftTagListBlock;
  }
}
