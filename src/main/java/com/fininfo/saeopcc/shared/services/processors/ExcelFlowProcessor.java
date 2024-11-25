package com.fininfo.saeopcc.shared.services.processors;

import com.fininfo.saeopcc.shared.domains.enumeration.NotificationStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.SwiftTagFieldValueNatureList;
import com.fininfo.saeopcc.shared.domains.flow.BrokerEntityMapping;
import com.fininfo.saeopcc.shared.domains.flow.ExcelFlow;
import com.fininfo.saeopcc.shared.domains.flow.ExcelMessageDescriptionDetails;
import com.fininfo.saeopcc.shared.domains.flow.Flow;
import com.fininfo.saeopcc.shared.domains.flow.FlowConfig;
import com.fininfo.saeopcc.shared.domains.flow.InputExcelFlow;
import com.fininfo.saeopcc.shared.domains.flow.Notification;
import com.fininfo.saeopcc.shared.domains.flow.NotificationError;
import com.fininfo.saeopcc.shared.services.excelutils.ExcelUtils;
import com.fininfo.saeopcc.shared.services.excelutils.FormatterUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.NumberEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ExcelFlowProcessor {

  private static final String PATTERNDATE = "dd/MM/yyyy";

  private static final String DECIMALFORMAT = "#,##0.00";

  private static final String TYPEMISMATCH2 = "Default Value Type Exception";

  public List<Notification> inputExcelProcessor(
      FlowConfig flowConfig, Flow flowIn, MultipartFile file)
      throws NoSuchFieldException, SecurityException {

    log.info("**** Entering Excel Processor Process ****");
    List<Notification> notifications = new ArrayList<>();

    ExcelFlow excelFlowConfig = (ExcelFlow) flowConfig;

    try (Workbook workbook = createWorkbook(file)) {

      if (excelFlowConfig != null
          && excelFlowConfig.getInputExcelFlows() != null
          && !excelFlowConfig.getInputExcelFlows().isEmpty()) {
        loopInputExcelFlow(excelFlowConfig, flowIn, file, notifications, workbook);
      }
      if (excelFlowConfig == null) {
        log.error("Pas de configuration de flux Excel trouvée.");
      } else {
        log.error(
            "Pas de structure de feuille excel trouvé pour l'excel : {}",
            excelFlowConfig.getCode());
      }
      workbook.close();
    } catch (IOException e) {
      log.error("Workbook init Exception: {}", e.getMessage());
    }
    return notifications;
  }

  private Workbook createWorkbook(MultipartFile file) throws IOException {
    if (file == null || file.getOriginalFilename() == null) {
      throw new IllegalArgumentException("Le fichier ou son nom est null.");
    }

    String originalFilename = file.getOriginalFilename();
    if (originalFilename != null && originalFilename.endsWith("xls")) {
      return new HSSFWorkbook(file.getInputStream());
    } else {
      return new XSSFWorkbook(file.getInputStream());
    }
  }

  public void loopInputExcelFlow(
      ExcelFlow excelFlowConfig,
      Flow flowIn,
      MultipartFile file,
      List<Notification> notifications,
      Workbook workbook)
      throws NoSuchFieldException, SecurityException {
    List<Notification> notifs = new ArrayList<>();
    for (InputExcelFlow inputExcelFlow : excelFlowConfig.getInputExcelFlows()) {
      Sheet sheet = workbook.getSheetAt(0);
      if (inputExcelFlow.getExcelMessageDescriptionDetails() != null
          && inputExcelFlow.getBrokerEntityMappings() != null) {
        List<ExcelMessageDescriptionDetails> sortedMessageDescriptionDetails =
            inputExcelFlow.getExcelMessageDescriptionDetails().stream()
                .sorted(Comparator.comparingInt(ExcelMessageDescriptionDetails::getFieldIndex))
                .collect(Collectors.toList());

        Set<BrokerEntityMapping> brokerMapprings = inputExcelFlow.getBrokerEntityMappings();
        if (!brokerMapprings.isEmpty()) {
          if (checkHeaders(
              sheet, inputExcelFlow.getHeaderPosition(), sortedMessageDescriptionDetails)) {
            notifs =
                generateNotificationsFromExcelFile(
                    excelFlowConfig,
                    sheet,
                    sortedMessageDescriptionDetails,
                    inputExcelFlow,
                    brokerMapprings,
                    flowIn);
            notifications.addAll(notifs);
          }
        } else {
          log.error(
              "les colonnes de la configuration de structure de message ne sont indentiques à ceux dans la feuille Excel");
        }
      }
    }
  }

  public boolean checkHeaders(
      Sheet sheet,
      Integer headerPosition,
      List<ExcelMessageDescriptionDetails> sorteDescriptionDetails) {

    List<String> sheetHeadersColumns = extractSheetHeaders(sheet, headerPosition);

    List<String> messageDescriptionsDetailsColumns =
        sorteDescriptionDetails.stream()
            .map(col -> col.getFieldName().trim())
            .collect(Collectors.toList());

    return sheetHeadersColumns.size() == messageDescriptionsDetailsColumns.size()
        && sheetHeadersColumns.containsAll(messageDescriptionsDetailsColumns);
  }

  private List<String> extractSheetHeaders(Sheet sheet, Integer headerStartLine) {
    List<String> headers = new ArrayList<>();
    Row row = sheet.getRow(headerStartLine - 1);
    int lastColumn = row.getLastCellNum();

    for (int columnNum = 0; columnNum < lastColumn; columnNum++) {
      if ((row.getCell(columnNum) != null
          && !row.getCell(columnNum).getStringCellValue().isBlank())) {
        headers.add(row.getCell(columnNum).getStringCellValue().trim());
      }
    }

    return headers;
  }

  private List<Notification> generateNotificationsFromExcelFile(
      ExcelFlow excelFlowConfig,
      Sheet sheet,
      List<ExcelMessageDescriptionDetails> sorteDescriptionDetails,
      InputExcelFlow inputExcelFlow,
      Set<BrokerEntityMapping> brokerEntityMappings,
      Flow flowIn)
      throws NoSuchFieldException, SecurityException {
    log.info("****** Entering Generationg Notification Mapping Process ******");
    List<Notification> notifs = new ArrayList<>();
    Integer contentBeginLine = inputExcelFlow.getContentStartPosition();
    Integer contentEndLine = sheet.getLastRowNum();
    if (contentBeginLine != null && contentEndLine != null) {
      for (int i = contentBeginLine - 1; i <= contentEndLine; i++) {
        String[] rowContents = new String[sorteDescriptionDetails.size()];
        Row row = sheet.getRow(i);
        if (!ExcelUtils.isRowEmpty(row)) {
          rowContents = getRowCellsContent(rowContents, row, inputExcelFlow, sheet);
          notifs.add(
              generateNotification(
                  rowContents,
                  inputExcelFlow,
                  sheet,
                  sorteDescriptionDetails,
                  excelFlowConfig,
                  brokerEntityMappings,
                  flowIn));
        }
      }
    }
    return notifs;
  }

  private String[] getRowCellsContent(
      String[] rowContents, Row row, InputExcelFlow inputExcelFlown, Sheet sheet) {

    Integer startColumn = inputExcelFlown.getFirstColumnPosition() - 1;
    if (rowContents.length < row.getLastCellNum()) {
      int differenceOfSize = row.getLastCellNum() - rowContents.length;
      rowContents = new String[rowContents.length + differenceOfSize];
    }
    int j = 0;

    for (int i = startColumn; i < row.getLastCellNum(); i++) {
      Cell cellValue = row.getCell(i);
      writeCellValue(cellValue, rowContents, j);
      j++;
    }

    return rowContents;
  }

  private String[] writeCellValue(Cell cellValue, String[] rowContents, int i) {

    if (cellValue != null) {
      switch (cellValue.getCellType()) {
        case STRING:
          rowContents[i] = cellValue.getRichStringCellValue().getString();
          break;
        case NUMERIC:
          if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cellValue)) {
            rowContents[i] = FormatterUtils.format_Date(cellValue.getDateCellValue(), PATTERNDATE);
          } else {

            if (cellValue.getCellStyle().getDataFormatString().contains("%")) {
              rowContents[i] = String.valueOf(cellValue.getNumericCellValue() * 100) + "%";
            } else {
              rowContents[i] =
                  ExcelFlowProcessor.decimalFormat().format(cellValue.getNumericCellValue());
            }
          }
          break;
        case BOOLEAN:
          rowContents[i] = String.valueOf(cellValue.getBooleanCellValue());
          break;
        case FORMULA:
          switch (cellValue.getCachedFormulaResultType()) {
            case STRING:
              rowContents[i] = cellValue.getRichStringCellValue().getString();
              break;
            case NUMERIC:
              if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cellValue)) {
                rowContents[i] =
                    FormatterUtils.format_Date(cellValue.getDateCellValue(), PATTERNDATE);
              } else {
                NumberEval numberEval =
                    new org.apache.poi.ss.formula.eval.NumberEval(cellValue.getNumericCellValue());
                rowContents[i] = numberEval.getStringValue();
              }
              break;
            case BOOLEAN:
              rowContents[i] = String.valueOf(cellValue.getBooleanCellValue());
              break;
            default:
              rowContents[i] = "";
          }
          break;
        default:
          rowContents[i] = "";
      }

    } else {
      rowContents[i] = "";
    }
    return rowContents;
  }

  private static DecimalFormat decimalFormat() {
    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
    decimalFormatSymbols.setDecimalSeparator(',');
    decimalFormatSymbols.setGroupingSeparator(' ');
    DecimalFormat decimalFormat = new DecimalFormat(DECIMALFORMAT, decimalFormatSymbols);
    decimalFormat.setParseBigDecimal(true);
    return decimalFormat;
  }

  private Notification generateNotification(
      String[] rowContents,
      InputExcelFlow inputExcelFlow,
      Sheet sheet,
      List<ExcelMessageDescriptionDetails> sorteDescriptionDetails,
      ExcelFlow excelFlowConfig,
      Set<BrokerEntityMapping> brokerEntityMappings,
      Flow flowIn)
      throws NoSuchFieldException, SecurityException {

    log.info("****** Entering Generating Notification Processor *******");
    Notification notification = new Notification();

    List<NotificationError> errors = new ArrayList<>();

    for (BrokerEntityMapping element : brokerEntityMappings) {
      log.debug("Dealing with Mapping Field: {}", element.toString());
      Field notifField;
      notifField = Notification.class.getDeclaredField(element.getTargetField().trim());
      notifField.setAccessible(true);

      if (element.getSourceField() != null) {
        Optional<ExcelMessageDescriptionDetails> messageDescriptionOpt =
            sorteDescriptionDetails.stream()
                .filter(
                    msg ->
                        msg.getFieldName().trim().equalsIgnoreCase(element.getSourceField().trim()))
                .findAny();

        if (messageDescriptionOpt.isPresent()) {
          ExcelMessageDescriptionDetails msd = messageDescriptionOpt.get();
          String contentValue = rowContents[msd.getFieldIndex() - 1];

          if (element.getFieldValueNature() != null
              && element.getFieldValueNature().equals(SwiftTagFieldValueNatureList.MAPPEDVALUE)) {
            log.debug("Dealing with Mapped Value");
            mappedValueTrigger(
                msd,
                contentValue,
                inputExcelFlow,
                excelFlowConfig,
                notifField,
                notification,
                element,
                errors);
          }
        }
      } else {

        if (element.getFieldValueNature() != null
            && element.getFieldValueNature().equals(SwiftTagFieldValueNatureList.DEFAULTVALUE)) {
          log.debug("Dealing with Default Value");
          defaultValueTrigger(notifField, notification, element, errors);
        }
      }
    }
    notification.setNotificationErrors(new HashSet<>(errors));
    notification.setSheetConfigId(inputExcelFlow.getId());

    return notification;
  }

  private void mappedValueTrigger(
      ExcelMessageDescriptionDetails msd,
      String rowContent,
      InputExcelFlow inputExcelFlow,
      ExcelFlow excelFlowConfig,
      Field notifField,
      Notification notification,
      BrokerEntityMapping element,
      List<NotificationError> errors) {

    if (msd.getIsNullable() != null
        && !msd.getIsNullable()
        && (rowContent == null || rowContent.isBlank())) {
      NotificationError notifError = new NotificationError();
      notifError.setError("Colonne Obligatoire");
      notifError.setErrorDescription(
          "Colonne: "
              + element.getSourceField()
              + " est obligatoire, veuillez vérifier la strucutre du fichier Excel");
      notifError.setSourceField(element.getSourceField().trim());
      notifError.setTargetField(element.getTargetField().trim());
      notifError.setFieldType(msd.getFieldType());

      errors.add(notifError);

      notification.setNotificationStatus(NotificationStatus.FAIL);
    } else {
      if (notifField.getType().equals(String.class)) {
        if (msd.getFieldType().equals("String")) {
          try {
            rowContent =
                (rowContent.indexOf(',', 0) > 0 && isNumeric(rowContent))
                    ? rowContent.substring(0, rowContent.indexOf(',', 0)).replaceAll("[^\\d.]", "")
                    : rowContent;
            rowContent =
                (rowContent.indexOf('.', 0) > 0 && isNumeric(rowContent))
                    ? rowContent.substring(0, rowContent.indexOf('.', 0)).replaceAll("[^\\d.]", "")
                    : rowContent;

            notifField.set(notification, rowContent);
          } catch (Exception e) {
            NotificationError error = new NotificationError();

            error.setError("Type De Données Incompatible");
            error.setErrorDescription(
                notifField.getName()
                    + " doit être du type "
                    + notifField.getType().getSimpleName());
            error.setSourceField(element.getSourceField().trim());
            error.setTargetField(element.getTargetField().trim());
            error.setFieldType(msd.getFieldType());
            errors.add(error);

            notification.setNotificationStatus(NotificationStatus.FAIL);
          }
        } else {

          NotificationError error = new NotificationError();

          error.setError("Type De Données Incompatible");
          error.setErrorDescription(
              notifField.getName() + " doit être du type " + notifField.getType().getSimpleName());
          error.setSourceField(element.getSourceField().trim());
          error.setTargetField(element.getTargetField().trim());
          error.setFieldType(msd.getFieldType());
          errors.add(error);

          notification.setNotificationStatus(NotificationStatus.FAIL);
        }
      } else if (notifField.getType().equals(Integer.class)) {
        if (msd.getFieldType().equals("Integer")) {
          try {
            Integer integerValue = Integer.valueOf(rowContent);
            notifField.set(notification, integerValue);
          } catch (Exception e) {
            NotificationError error = new NotificationError();

            error.setError("Type De Données Incompatible");
            error.setErrorDescription(
                notifField.getName()
                    + " doit être du type "
                    + notifField.getType().getSimpleName());
            error.setSourceField(element.getSourceField().trim());
            error.setTargetField(element.getTargetField().trim());
            error.setFieldType(msd.getFieldType());
            errors.add(error);

            notification.setNotificationStatus(NotificationStatus.FAIL);
          }
        } else {
          NotificationError error = new NotificationError();

          error.setError("Type De Données Incompatible");
          error.setErrorDescription(
              notifField.getName() + " doit être du type " + notifField.getType().getSimpleName());
          error.setSourceField(element.getSourceField().trim());
          error.setTargetField(element.getTargetField().trim());
          error.setFieldType(msd.getFieldType());
          errors.add(error);

          notification.setNotificationStatus(NotificationStatus.FAIL);
        }
      } else if (notifField.getType().equals(BigDecimal.class)) {
        if (msd.getFieldType().equals("BigDecimal")) {
          try {
            BigDecimal bigDecimalValue =
                (BigDecimal) ExcelFlowProcessor.decimalFormat().parse(rowContent);
            notifField.set(notification, bigDecimalValue.setScale(2));
          } catch (Exception e) {
            NotificationError error = new NotificationError();

            error.setError("Type De Données Incompatible");
            error.setErrorDescription(
                notifField.getName()
                    + " doit être du type "
                    + notifField.getType().getSimpleName());
            error.setSourceField(element.getSourceField().trim());
            error.setTargetField(element.getTargetField().trim());
            error.setFieldType(msd.getFieldType());
            errors.add(error);

            notification.setNotificationStatus(NotificationStatus.FAIL);
          }

        } else {
          NotificationError error = new NotificationError();

          error.setError("Type De Données Incompatible");
          error.setErrorDescription(
              notifField.getName() + " doit être du type " + notifField.getType().getSimpleName());
          error.setSourceField(element.getSourceField().trim());
          error.setTargetField(element.getTargetField().trim());
          error.setFieldType(msd.getFieldType());
          errors.add(error);

          notification.setNotificationStatus(NotificationStatus.FAIL);
        }
      } else if (notifField.getType().equals(LocalDate.class)) {
        if (msd.getFieldType().equals("LocalDate")) {
          try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERNDATE);
            LocalDate localDateValue = LocalDate.parse(rowContent, dtf);
            notifField.set(notification, localDateValue);
          } catch (Exception e) {
            NotificationError error = new NotificationError();

            error.setError("Type De Données Incompatible");
            error.setErrorDescription(
                notifField.getName()
                    + " doit être du type "
                    + notifField.getType().getSimpleName());
            error.setSourceField(element.getSourceField().trim());
            error.setTargetField(element.getTargetField().trim());
            error.setFieldType(msd.getFieldType());
            errors.add(error);

            notification.setNotificationStatus(NotificationStatus.FAIL);
          }

        } else {
          NotificationError error = new NotificationError();

          error.setError("Type De Données Incompatible");
          error.setErrorDescription(
              notifField.getName() + " doit être du type " + notifField.getType().getSimpleName());
          error.setSourceField(element.getSourceField().trim());
          error.setTargetField(element.getTargetField().trim());
          error.setFieldType(msd.getFieldType());
          errors.add(error);

          notification.setNotificationStatus(NotificationStatus.FAIL);
        }
      }
    }
  }

  private void defaultValueTrigger(
      Field notifField,
      Notification notification,
      BrokerEntityMapping element,
      List<NotificationError> errors) {
    if (element.getDefaultValue() != null && !element.getDefaultValue().equals("")) {
      if (notifField.getType().equals(String.class)) {
        try {
          notifField.set(notification, element.getDefaultValue());

        } catch (Exception e) {
          NotificationError error = new NotificationError();
          error.setError(TYPEMISMATCH2);
          error.setErrorDescription("Default value doit etre de type chaine de caractères");
          error.setTargetField(element.getTargetField().trim());
          errors.add(error);
          notification.setNotificationStatus(NotificationStatus.FAIL);
          log.error("An error occurred:", e);
        }

      } else if (notifField.getType().equals(Integer.class)) {

        try {
          Integer integerValue = Integer.valueOf(element.getDefaultValue());

          notifField.set(notification, integerValue);

        } catch (Exception e) {
          NotificationError error = new NotificationError();
          error.setError(TYPEMISMATCH2);
          error.setErrorDescription("Default value doit etre de type entier");
          error.setTargetField(element.getTargetField().trim());
          errors.add(error);
          notification.setNotificationStatus(NotificationStatus.FAIL);
          log.error("An error occurred:", e);
        }

      } else if (notifField.getType().equals(BigDecimal.class)) {

        try {

          BigDecimal bigDecimalValue =
              (BigDecimal) ExcelFlowProcessor.decimalFormat().parse(element.getDefaultValue());
          notifField.set(notification, bigDecimalValue);

        } catch (Exception e) {

          NotificationError error = new NotificationError();

          error.setError(TYPEMISMATCH2);
          error.setErrorDescription("Default value doit etre de type décimal");
          error.setTargetField(element.getTargetField().trim());
          errors.add(error);
          notification.setNotificationStatus(NotificationStatus.FAIL);
          log.error("An error occurred:", e);
        }
      } else if (notifField.getType().equals(LocalDate.class)) {

        try {

          DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERNDATE);
          LocalDate localDateValue = LocalDate.parse(element.getDefaultValue(), dtf);
          notifField.set(notification, localDateValue);

        } catch (Exception e) {

          NotificationError error = new NotificationError();

          error.setError(TYPEMISMATCH2);
          error.setErrorDescription(
              "Default value doit etre de type Date et suit la format dd/MM/yyyy");
          error.setTargetField(element.getTargetField().trim());
          errors.add(error);
          notification.setNotificationStatus(NotificationStatus.FAIL);
          log.error("An error occurred:", e);
        }
      }
    }
  }

  private boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    try {
      strNum = strNum.replace(",", ".").replace(" ", "");
      double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }
}
