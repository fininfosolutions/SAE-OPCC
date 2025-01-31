package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.flow.Document;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientReportingDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartmentsReportingDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.ShareholderReportingDTO;
import com.itextpdf.io.exceptions.IOException;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExcelReportingService {
  @Autowired private DocumentService documentService;

  public byte[] generateOpccReportingExcel(List<ClientReportingDTO> clientReportings)
      throws IOException, java.io.IOException {
    Workbook workbook = new XSSFWorkbook();
    DataFormat format = workbook.createDataFormat();

    Sheet sheet = workbook.createSheet("Fonds OPCC");

    int rowIndex = 0;

    rowIndex = createTitle(sheet, rowIndex, workbook, "Tableau Résumé Global des Fonds OPCC");
    rowIndex++;

    Row headerRow = sheet.createRow(rowIndex++);
    createHeaders(
        headerRow,
        workbook,
        "Fonds",
        "Montant Total Souscrit",
        "Montant Total Appelé",
        "Nombre de Parts Appelées",
        "Montant Total Libéré",
        "Nombre de Parts Libérées",
        "Solde Non Appelé",
        "Nombre de Parts Non Appelées",
        "Solde Non Libéré",
        "Nombre de Parts Non Libérées",
        "Statut de Libération",
        "Période d'Investissement",
        "Période de Désinvestissement");

    BigDecimal totalSubscribedAmount = BigDecimal.ZERO;
    BigDecimal totalCalledAmount = BigDecimal.ZERO;
    BigDecimal totalCalledQuantity = BigDecimal.ZERO;
    BigDecimal totalReleasedAmount = BigDecimal.ZERO;
    BigDecimal totalReleasedQuantity = BigDecimal.ZERO;
    BigDecimal totalNotCalledAmount = BigDecimal.ZERO;
    BigDecimal totalNotCalledQuantity = BigDecimal.ZERO;
    BigDecimal totalNotReleasedAmount = BigDecimal.ZERO;
    BigDecimal totalNotReleasedQuantity = BigDecimal.ZERO;

    for (ClientReportingDTO reporting : clientReportings) {
      Row dataRow = sheet.createRow(rowIndex++);

      createBoldCell(dataRow, 0, reporting.getFundName(), workbook, HorizontalAlignment.LEFT);
      createCell(
          dataRow,
          1,
          formatBigDecimal(reporting.getTotalSubscribedAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          2,
          formatBigDecimal(reporting.getTotalCalledAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          3,
          formatBigDecimal(reporting.getTotalCalledQuantity()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          4,
          formatBigDecimal(reporting.getTotalReleasedAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          5,
          formatBigDecimal(reporting.getTotalReleasedQuantity()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          6,
          formatBigDecimal(reporting.getNotCalledAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          7,
          formatBigDecimal(reporting.getNotCalledQuantity()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          8,
          formatBigDecimal(reporting.getNotReleasedAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          9,
          formatBigDecimal(reporting.getNotReleasedQuantity()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow, 10, reporting.getReleaseStatus().getLabel(), workbook, HorizontalAlignment.LEFT);
      createCell(dataRow, 11, reporting.getInvestmentPeriod(), workbook, HorizontalAlignment.LEFT);
      createCell(
          dataRow, 12, reporting.getDisinvestmentPeriod(), workbook, HorizontalAlignment.LEFT);

      totalSubscribedAmount = totalSubscribedAmount.add(reporting.getTotalSubscribedAmount());
      totalCalledAmount = totalCalledAmount.add(reporting.getTotalCalledAmount());
      totalCalledQuantity = totalCalledQuantity.add(reporting.getTotalCalledQuantity());
      totalReleasedAmount = totalReleasedAmount.add(reporting.getTotalReleasedAmount());
      totalReleasedQuantity = totalReleasedQuantity.add(reporting.getTotalReleasedQuantity());
      totalNotCalledAmount = totalNotCalledAmount.add(reporting.getNotCalledAmount());
      totalNotCalledQuantity = totalNotCalledQuantity.add(reporting.getNotCalledQuantity());
      totalNotReleasedAmount = totalNotReleasedAmount.add(reporting.getNotReleasedAmount());
      totalNotReleasedQuantity = totalNotReleasedQuantity.add(reporting.getNotReleasedQuantity());
    }

    Row totalRow = sheet.createRow(rowIndex++);
    createBoldCell(totalRow, 0, "Total", workbook, HorizontalAlignment.LEFT);
    createBoldCell(
        totalRow,
        1,
        formatBigDecimal(totalSubscribedAmount),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        totalRow,
        2,
        formatBigDecimal(totalCalledAmount),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        totalRow,
        3,
        formatBigDecimal(totalCalledQuantity),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        totalRow,
        4,
        formatBigDecimal(totalReleasedAmount),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        totalRow,
        5,
        formatBigDecimal(totalReleasedQuantity),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        totalRow,
        6,
        formatBigDecimal(totalNotCalledAmount),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        totalRow,
        7,
        formatBigDecimal(totalNotCalledQuantity),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        totalRow,
        8,
        formatBigDecimal(totalNotReleasedAmount),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        totalRow,
        9,
        formatBigDecimal(totalNotReleasedQuantity),
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(totalRow, 10, "", workbook, HorizontalAlignment.LEFT);
    createBoldCell(totalRow, 11, "", workbook, HorizontalAlignment.LEFT);
    createBoldCell(totalRow, 12, "", workbook, HorizontalAlignment.LEFT);

    for (int i = 0; i < 13; i++) {
      sheet.autoSizeColumn(i);
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.close();

    byte[] content = out.toByteArray();
    long size = content.length;

    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    String fileName = String.format("OPCC_BY_ClientReport_%s_%s.xlsx", currentDate, currentTime);

    Document savedDocument =
        documentService.saveDocument(
            fileName,
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            size,
            content,
            LocalDate.now());

    return content;
  }

  public Map<String, byte[]> generateOpccByCompartementExcelFiles(
      Map<String, List<CompartmentsReportingDTO>> clientData)
      throws IOException, java.io.IOException {
    Map<String, byte[]> clientExcelFiles = new HashMap<>();

    for (Map.Entry<String, List<CompartmentsReportingDTO>> clientEntry : clientData.entrySet()) {
      String clientName = clientEntry.getKey();
      List<CompartmentsReportingDTO> compartments = clientEntry.getValue();

      byte[] excelFile = generateExcelOpccByCompartement(clientName, compartments);
      clientExcelFiles.put(clientName, excelFile);
    }

    return clientExcelFiles;
  }

  private byte[] generateExcelOpccByCompartement(
      String clientName, List<CompartmentsReportingDTO> compartments)
      throws IOException, java.io.IOException {
    Workbook workbook = new XSSFWorkbook();
    DataFormat format = workbook.createDataFormat();

    Sheet sheet = workbook.createSheet(clientName);
    int rowIndex = 0;

    rowIndex = createTitle(sheet, rowIndex, workbook, "Résumé Global Fonds A par Compartiment ");
    rowIndex++;

    Row headerRow = sheet.createRow(rowIndex++);
    createHeaders(
        headerRow,
        workbook,
        "Fonds",
        "Compartiment",
        "Montant Total Souscrit",
        "Montant Total Appelé",
        "Nombre de Parts Appelées",
        "Montant Total Libéré",
        "Nombre de Parts Libérées",
        "Solde Non Appelé",
        "Nombre de Parts Non Appelées",
        "Solde Non Libéré",
        "Nombre de Parts Non Libérées",
        "Statut de Libération",
        "Période d'Investissement",
        "Période de Désinvestissement");
    String previousFundName = "";
    for (CompartmentsReportingDTO compartment : compartments) {
      Row dataRow = sheet.createRow(rowIndex++);

      if (!compartment.getFundName().equals(previousFundName)) {
        createBoldCell(dataRow, 0, compartment.getFundName(), workbook, HorizontalAlignment.LEFT);
        previousFundName = compartment.getFundName();
      } else {
        createCell(dataRow, 0, "", workbook, HorizontalAlignment.LEFT);
      }
      createCell(dataRow, 1, compartment.getCompartementName(), workbook, HorizontalAlignment.LEFT);

      createCell(
          dataRow,
          2,
          formatBigDecimal(compartment.getTotalSubscribedAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          3,
          formatBigDecimal(compartment.getTotalCalledAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          4,
          formatBigDecimal(compartment.getTotalCalledQuantity()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          5,
          formatBigDecimal(compartment.getTotalReleasedAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          6,
          formatBigDecimal(compartment.getTotalReleasedQuantity()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          7,
          formatBigDecimal(compartment.getNotCalledAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          8,
          formatBigDecimal(compartment.getNotCalledQuantity()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          9,
          formatBigDecimal(compartment.getNotReleasedAmount()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);
      createCell(
          dataRow,
          10,
          formatBigDecimal(compartment.getNotReleasedQuantity()),
          workbook,
          HorizontalAlignment.RIGHT,
          format);

      createCell(dataRow, 11, compartment.getReleaseStatus(), workbook, HorizontalAlignment.LEFT);
      createCell(
          dataRow, 12, compartment.getInvestmentPeriod(), workbook, HorizontalAlignment.LEFT);
      createCell(
          dataRow, 13, compartment.getDisinvestmentPeriod(), workbook, HorizontalAlignment.LEFT);
    }

    for (int i = 0; i < 14; i++) {
      sheet.autoSizeColumn(i);
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.close();

    byte[] content = out.toByteArray();

    long size = content.length;

    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    String fileName =
        String.format(
            "OPCC_BY_ClientReport_%s_%s_%s.xlsx",
            currentDate, currentTime, compartments.get(0).getFundName());

    Document savedDocument =
        documentService.saveDocument(
            fileName,
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            size,
            content,
            LocalDate.now());

    return content;
  }

  public byte[] generateShareholderReportingExcel(
      Map<String, Map<String, Map<String, ShareholderReportingDTO[]>>> shareholderData)
      throws IOException, java.io.IOException {
    Workbook workbook = new XSSFWorkbook();
    DataFormat format = workbook.createDataFormat();

    Sheet sheet =
        workbook.createSheet("Résumé Global Compartiment A - Souscripteurs et Appels à Libération");
    int rowIndex = 0;

    rowIndex = createTitle(sheet, rowIndex, workbook, "Résumé Global - Tous les compartiments");
    rowIndex++;
    Row headerRow = sheet.createRow(rowIndex++);
    createHeaders(
        headerRow,
        workbook,
        "Fonds OPCC",
        "Compartiment",
        "Souscripteur",
        "Montant Total Souscrit (Devise)",
        "Nombre de Parts Souscrites",
        "Appel à Libération",
        "Montant Appelé (Devise)",
        "Nombre de Parts Appelées",
        "Sous-Comptes",
        "Montant Libéré (Devise)",
        "Nombre de Parts Libérées",
        "Solde Non Libéré (Devise)",
        "Nombre de Parts Non Libérées",
        "Date d'Appel de Libération",
        "Date de Clôture de l'Appel",
        "Statut de Libération");

    BigDecimal grandTotalSubscribedAmount = BigDecimal.ZERO;
    BigDecimal grandTotalSubscribedQuantity = BigDecimal.ZERO;
    BigDecimal grandTotalCalledAmount = BigDecimal.ZERO;
    BigDecimal grandTotalCalledQuantity = BigDecimal.ZERO;
    BigDecimal grandTotalReleasedAmount = BigDecimal.ZERO;
    BigDecimal grandTotalReleasedQuantity = BigDecimal.ZERO;
    BigDecimal grandTotalNotReleasedAmount = BigDecimal.ZERO;
    BigDecimal grandTotalNotReleasedQuantity = BigDecimal.ZERO;

    for (Map.Entry<String, Map<String, Map<String, ShareholderReportingDTO[]>>> fundEntry :
        shareholderData.entrySet()) {
      String fundName = fundEntry.getKey();
      Map<String, Map<String, ShareholderReportingDTO[]>> compartments = fundEntry.getValue();
      boolean firstFundRow = true;
      for (Map.Entry<String, Map<String, ShareholderReportingDTO[]>> compartmentEntry :
          compartments.entrySet()) {
        String compartmentName = compartmentEntry.getKey();
        Map<String, ShareholderReportingDTO[]> shareholders = compartmentEntry.getValue();

        boolean firstCompartmentRow = true;
        BigDecimal totalSubscribedAmountCompartment = BigDecimal.ZERO;
        BigDecimal totalSubscribedQuantityCompartment = BigDecimal.ZERO;
        Optional<ShareholderReportingDTO[]> firstSubscriberReports =
            shareholders.values().stream().findFirst();
        if (firstSubscriberReports.isPresent() && firstSubscriberReports.get().length > 0) {
          ShareholderReportingDTO firstReport = firstSubscriberReports.get()[0];
          totalSubscribedAmountCompartment =
              firstReport.getTotalSubscribedAmount() != null
                  ? firstReport.getTotalSubscribedAmount()
                  : BigDecimal.ZERO;
          totalSubscribedQuantityCompartment =
              firstReport.getTotalSubscribedQuantity() != null
                  ? firstReport.getTotalSubscribedQuantity()
                  : BigDecimal.ZERO;
        }

        grandTotalSubscribedAmount =
            grandTotalSubscribedAmount.add(totalSubscribedAmountCompartment);
        grandTotalSubscribedQuantity =
            grandTotalSubscribedQuantity.add(totalSubscribedQuantityCompartment);

        for (Map.Entry<String, ShareholderReportingDTO[]> shareholderEntry :
            shareholders.entrySet()) {
          String subscriberName = shareholderEntry.getKey();
          ShareholderReportingDTO[] shareholderReports = shareholderEntry.getValue();

          boolean firstSubscriberRow = true;

          for (ShareholderReportingDTO report : shareholderReports) {
            Row dataRow = sheet.createRow(rowIndex++);

            if (firstFundRow) {
              createCell(dataRow, 0, fundName, workbook, HorizontalAlignment.LEFT);
              firstFundRow = false;
            } else {
              createCell(dataRow, 0, "", workbook, HorizontalAlignment.LEFT);
            }

            if (firstCompartmentRow) {
              createCell(dataRow, 1, compartmentName, workbook, HorizontalAlignment.LEFT);
              createCell(
                  dataRow,
                  3,
                  report.getTotalSubscribedAmount(),
                  workbook,
                  HorizontalAlignment.RIGHT,
                  format);
              createCell(
                  dataRow,
                  4,
                  report.getTotalSubscribedQuantity(),
                  workbook,
                  HorizontalAlignment.RIGHT,
                  format);
              firstCompartmentRow = false;
            } else {
              createCell(dataRow, 1, "", workbook, HorizontalAlignment.LEFT);
              createCell(dataRow, 3, "", workbook, HorizontalAlignment.RIGHT, format);
              createCell(dataRow, 4, "", workbook, HorizontalAlignment.RIGHT, format);
            }

            if (firstSubscriberRow) {
              createCell(dataRow, 2, subscriberName, workbook, HorizontalAlignment.LEFT);
              firstSubscriberRow = false;
            } else {
              createCell(dataRow, 2, "", workbook, HorizontalAlignment.LEFT);
            }

            createCell(
                dataRow, 5, report.getCalledLiberation(), workbook, HorizontalAlignment.LEFT);
            createCell(
                dataRow,
                6,
                report.getTotalCalledAmount(),
                workbook,
                HorizontalAlignment.RIGHT,
                format);
            createCell(
                dataRow,
                7,
                report.getTotalCalledQuantity(),
                workbook,
                HorizontalAlignment.RIGHT,
                format);
            createCell(
                dataRow,
                9,
                report.getTotalReleasedAmount(),
                workbook,
                HorizontalAlignment.RIGHT,
                format);
            createCell(
                dataRow,
                10,
                report.getTotalReleasedQuantity(),
                workbook,
                HorizontalAlignment.RIGHT,
                format);
            createCell(
                dataRow,
                11,
                report.getNotReleasedAmount(),
                workbook,
                HorizontalAlignment.RIGHT,
                format);
            createCell(
                dataRow,
                12,
                report.getNotReleasedQuantity(),
                workbook,
                HorizontalAlignment.RIGHT,
                format);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String calledDateStr =
                (report.getCalledDate() != null)
                    ? report.getCalledDate().format(dateFormatter)
                    : "";
            String closedCalledDateStr =
                (report.getClosedCalledDate() != null)
                    ? report.getClosedCalledDate().format(dateFormatter)
                    : "";
            createCell(dataRow, 13, calledDateStr, workbook, HorizontalAlignment.RIGHT);
            createCell(dataRow, 14, closedCalledDateStr, workbook, HorizontalAlignment.RIGHT);

            grandTotalCalledAmount =
                grandTotalCalledAmount.add(
                    report.getTotalCalledAmount() != null
                        ? report.getTotalCalledAmount()
                        : BigDecimal.ZERO);
            grandTotalCalledQuantity =
                grandTotalCalledQuantity.add(
                    report.getTotalCalledQuantity() != null
                        ? report.getTotalCalledQuantity()
                        : BigDecimal.ZERO);
            grandTotalReleasedAmount =
                grandTotalReleasedAmount.add(
                    report.getTotalReleasedAmount() != null
                        ? report.getTotalReleasedAmount()
                        : BigDecimal.ZERO);
            grandTotalReleasedQuantity =
                grandTotalReleasedQuantity.add(
                    report.getTotalReleasedQuantity() != null
                        ? report.getTotalReleasedQuantity()
                        : BigDecimal.ZERO);
            grandTotalNotReleasedAmount =
                grandTotalNotReleasedAmount.add(
                    report.getNotReleasedAmount() != null
                        ? report.getNotReleasedAmount()
                        : BigDecimal.ZERO);
            grandTotalNotReleasedQuantity =
                grandTotalNotReleasedQuantity.add(
                    report.getNotReleasedQuantity() != null
                        ? report.getNotReleasedQuantity()
                        : BigDecimal.ZERO);
          }
        }
      }
    }

    Row grandTotalRow = sheet.createRow(rowIndex++);
    createBoldCell(grandTotalRow, 0, "TOTAL ", workbook, HorizontalAlignment.LEFT);
    createBoldCell(
        grandTotalRow, 3, grandTotalSubscribedAmount, workbook, HorizontalAlignment.RIGHT, format);
    createBoldCell(
        grandTotalRow,
        4,
        grandTotalSubscribedQuantity,
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        grandTotalRow, 6, grandTotalCalledAmount, workbook, HorizontalAlignment.RIGHT, format);
    createBoldCell(
        grandTotalRow, 7, grandTotalCalledQuantity, workbook, HorizontalAlignment.RIGHT, format);
    createBoldCell(
        grandTotalRow, 9, grandTotalReleasedAmount, workbook, HorizontalAlignment.RIGHT, format);
    createBoldCell(
        grandTotalRow, 10, grandTotalReleasedQuantity, workbook, HorizontalAlignment.RIGHT, format);
    createBoldCell(
        grandTotalRow,
        11,
        grandTotalNotReleasedAmount,
        workbook,
        HorizontalAlignment.RIGHT,
        format);
    createBoldCell(
        grandTotalRow,
        12,
        grandTotalNotReleasedQuantity,
        workbook,
        HorizontalAlignment.RIGHT,
        format);

    for (int i = 0; i < 15; i++) {
      sheet.autoSizeColumn(i);
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.close();
    byte[] content = out.toByteArray();

    long size = content.length;

    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    String fileName =
        String.format("OPCC_BY_ShareholderReport__%s_%s_%s.xlsx", currentDate, currentTime, "hhh");

    Document savedDocument =
        documentService.saveDocument(
            fileName,
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            size,
            content,
            LocalDate.now());

    return content;
  }

  private int createTitle(Sheet sheet, int rowIndex, Workbook workbook, String title) {
    Row titleRow = sheet.createRow(rowIndex++);
    Cell titleCell = titleRow.createCell(0);
    titleCell.setCellValue(title);

    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);
    titleCell.setCellStyle(style);

    return rowIndex;
  }

  private void createHeaders(Row row, Workbook workbook, String... headers) {
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);

    for (int i = 0; i < headers.length; i++) {
      Cell cell = row.createCell(i);
      cell.setCellValue(headers[i]);
      cell.setCellStyle(style);
    }
  }

  private void createCell(
      Row row, int column, String value, Workbook workbook, HorizontalAlignment alignment) {
    Cell cell = row.createCell(column);
    cell.setCellValue(value);

    CellStyle style = workbook.createCellStyle();
    style.setAlignment(alignment);
    cell.setCellStyle(style);
  }

  private void createCell(
      Row row,
      int column,
      Object value,
      Workbook workbook,
      HorizontalAlignment alignment,
      DataFormat format) {
    Cell cell = row.createCell(column);

    CellStyle style = workbook.createCellStyle();
    style.setAlignment(alignment);

    if (value instanceof BigDecimal) {
      style.setDataFormat(format.getFormat("#,##0.00"));
      cell.setCellValue(((BigDecimal) value).doubleValue());
    } else if (value instanceof Number) {
      style.setDataFormat(format.getFormat("#,##0.00"));
      cell.setCellValue(((Number) value).doubleValue());
    } else if (value != null) {
      cell.setCellValue(value.toString());
    } else {
      cell.setCellValue("");
    }

    cell.setCellStyle(style);
  }

  private String formatBigDecimal(BigDecimal value) {
    if (value == null) {
      return "";
    }
    DecimalFormat df = new DecimalFormat("#,##0.00");
    return df.format(value);
  }

  private void createBoldCell(
      Row row,
      int column,
      Object value,
      Workbook workbook,
      HorizontalAlignment alignment,
      DataFormat format) {
    Cell cell = row.createCell(column);

    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);
    style.setAlignment(alignment);

    if (value instanceof BigDecimal) {
      style.setDataFormat(format.getFormat("#,##0.00"));
      cell.setCellValue(((BigDecimal) value).doubleValue());
    } else if (value instanceof Number) {
      style.setDataFormat(format.getFormat("#,##0.00"));
      cell.setCellValue(((Number) value).doubleValue());
    } else if (value != null) {
      cell.setCellValue(value.toString());
    } else {
      cell.setCellValue("");
    }

    cell.setCellStyle(style);
  }

  private void createBoldCell(
      Row row, int column, String value, Workbook workbook, HorizontalAlignment alignment) {
    Cell cell = row.createCell(column);
    cell.setCellValue(value);

    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);
    style.setAlignment(alignment);

    cell.setCellStyle(style);
  }
}
