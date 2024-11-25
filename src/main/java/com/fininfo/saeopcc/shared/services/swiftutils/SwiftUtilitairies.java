package com.fininfo.saeopcc.shared.services.swiftutils;

import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagField;
import com.fininfo.saeopcc.shared.domains.swift.SwiftTagFieldValues;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SwiftUtilitairies {

  public Map<String, String> getSwiftTagFieldValues(
      Set<SwiftTagField> swiftSequenceATagFieldList, String tag, Notification notification)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    Map<String, String> Values = new HashMap<String, String>();
    SwiftTagField swiftTagField = new SwiftTagField();

    System.out.println("Playing with Tag :" + tag);
    Optional<SwiftTagField> swiftTagFieldOpt =
        swiftSequenceATagFieldList.stream()
            .filter(tagField -> tagField.getTag().equals(tag))
            .findFirst();

    if (swiftTagFieldOpt.isPresent()) {
      swiftTagField = swiftTagFieldOpt.get();
    } else return null;

    List<SwiftTagFieldValues> swiftFieldValueList =
        swiftTagField.getSwiftTagFieldValues().stream() // recupération
            // des
            // swifttagfieldvalues
            .collect(Collectors.toList());

    for (SwiftTagFieldValues fieldValues : swiftFieldValueList) {
      String swiftTagFieldGeneratedValue =
          getGeneratedValueFromSwiftTagValues(notification, fieldValues);
      Values.put(fieldValues.getSwiftTagFieldValueType().toString(), swiftTagFieldGeneratedValue);
    }

    return Values;
  }

  ////////////////////////
  public Map<String, String> getSwiftTagFieldValues(
      SwiftTagField swiftTagField, Notification notification)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    Map<String, String> Values = new HashMap<String, String>();

    System.out.println("Playing with Tag :" + swiftTagField.getTag());

    List<SwiftTagFieldValues> swiftFieldValueList =
        swiftTagField.getSwiftTagFieldValues().stream() // recupération
            // des
            // swifttagfieldvalues
            .collect(Collectors.toList());

    for (SwiftTagFieldValues fieldValues : swiftFieldValueList) {
      String swiftTagFieldGeneratedValue =
          getGeneratedValueFromSwiftTagValues(notification, fieldValues);
      Values.put(fieldValues.getSwiftTagFieldValueType().toString(), swiftTagFieldGeneratedValue);
    }

    return Values;
  }

  public List<Map<String, String>> getSwiftTagFieldValuesList(
      Set<SwiftTagField> swiftSequenceBTagFieldList, String tag, Notification notification)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    List<Map<String, String>> ValuesList = new ArrayList<Map<String, String>>();

    List<SwiftTagField> swiftTagFieldList =
        swiftSequenceBTagFieldList.stream()
            .filter(tagField -> tagField.getTag().equals(tag))
            .collect(Collectors.toList());

    for (SwiftTagField swiftTagField : swiftTagFieldList) {

      Map<String, String> Values = new HashMap<String, String>();
      List<SwiftTagFieldValues> swiftFieldValueList =
          swiftTagField.getSwiftTagFieldValues().stream().collect(Collectors.toList());

      for (SwiftTagFieldValues fieldValues : swiftFieldValueList) {
        String swiftTagFieldGeneratedValue =
            getGeneratedValueFromSwiftTagValues(notification, fieldValues);
        Values.put(fieldValues.getSwiftTagFieldValueType().toString(), swiftTagFieldGeneratedValue);
      }
      ValuesList.add(Values);
    }

    return ValuesList;
  }

  public String getGeneratedValueFromSwiftTagValues(
      Notification notification, SwiftTagFieldValues swiftTagFieldValues)
      throws NoSuchFieldException,
          SecurityException,
          IllegalArgumentException,
          IllegalAccessException {

    switch (swiftTagFieldValues.getSwiftTagFieldValueNature()) {
      case DEFAULTVALUE:
        return swiftTagFieldValues.getDefaultValue();
      case MAPPEDVALUE:
        Field notifFiled;
        Object mappedValueObject = null;
        notifFiled = Notification.class.getDeclaredField(swiftTagFieldValues.getMappedValue());
        notifFiled.setAccessible(true);
        if (notifFiled.getType().equals(BigDecimal.class)) {
          BigDecimal num = (BigDecimal) notifFiled.get(notification);
          if (num != null) {
            mappedValueObject =
                new BigDecimal(
                    num.setScale(2, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
            if (mappedValueObject.toString().contains("."))
              mappedValueObject = mappedValueObject.toString().replace('.', ',');
          }
        } else {
          mappedValueObject = notifFiled.get(notification);
        }
        if (mappedValueObject != null) return mappedValueObject.toString();
        else return "";
      default:
        return null;
    }
  }

  public String formatNumberFromString(String numberToFormat) {
    if (numberToFormat.contains(".") || numberToFormat.contains(",")) {
      return numberToFormat.replace('.', ',');
    } else {
      return numberToFormat.concat(",");
    }
  }

  public String formatDateFromString(String dateToFormate) {

    if (dateToFormate.contains("-")) {
      return dateToFormate.replace("-", "");
    }

    return dateToFormate;
  }
}
