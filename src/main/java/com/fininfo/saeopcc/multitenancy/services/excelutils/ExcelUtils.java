package com.fininfo.saeopcc.multitenancy.services.excelutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fininfo.saeopcc.shared.services.excelutils.EntityColumns;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {

  private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

  private ExcelUtils() {}

  public static <T> ByteArrayInputStream writeToExcelGeneric(List<T> data) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

      SXSSFSheet sheet = workbook.createSheet();
      setMarginAndColumnsWidth(sheet);
      CellStyle headerStyle = getHeaderStyle(workbook);
      CellStyle rowsStyle = getNormalStyle(workbook);

      List<String> fieldNames = getFieldNamesForClass(data.get(0).getClass());
      int rowCount = 0;
      int columnCount = 0;
      Row row = sheet.createRow(rowCount++);

      setColumnsCells(fieldNames, headerStyle, columnCount, row);

      writingData(data, fieldNames, data.get(0).getClass(), rowsStyle, sheet, rowCount);

      workbook.write(out);

    } catch (Exception e) {

      log.error("exception WriteToExcelGeneric :", e);
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  // retrieve field names from a POJO class
  public static List<String> getFieldNamesForClass(Class<?> clazz) {
    List<String> fieldNames = new ArrayList<>();
    Field[] fields = clazz.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      if (!fields[i].getName().equals("serialVersionUID")
          && !fields[i].getName().equals("scoringCriteria")) {
        fieldNames.add(fields[i].getName());
      }
    }
    return fieldNames;
  }

  public static List<String> getFieldNamesVFFromClomuns(List<EntityColumns> columns) {
    List<String> fieldNames = new ArrayList<>();

    columns.sort((EntityColumns s1, EntityColumns s2) -> s1.getColumnIndex() - s2.getColumnIndex());

    for (EntityColumns entry : columns) {

      fieldNames.add(entry.getFrontColumnName());
    }

    return fieldNames;
  }

  private static List<String> getFieldNamesFromColumns(List<EntityColumns> columns) {
    List<String> fieldNames = new ArrayList<>();

    for (EntityColumns entry : columns) {

      fieldNames.add(entry.getEntityColumnName());
    }

    return fieldNames;
  }

  // capitalize the first letter of the field name for retriving value of the
  // field later
  public static String capitalize(String s) {
    if (s.length() == 0) return s;
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  public static CellStyle getHeaderStyle(SXSSFWorkbook wb) {

    CellStyle style = wb.createCellStyle();

    style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());

    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    setBorderAndAlignement(style);

    org.apache.poi.ss.usermodel.Font font = wb.createFont();
    font.setBold(true);
    font.setColor(IndexedColors.WHITE.getIndex());
    style.setFont(font);
    return style;
  }

  public static CellStyle getNormalStyle(SXSSFWorkbook wb) {

    CellStyle borderStyle = wb.createCellStyle();

    return setBorderAndAlignement(borderStyle);
  }

  public static CellStyle setBorderAndAlignement(CellStyle borderStyle) {

    borderStyle.setBorderBottom(BorderStyle.THIN);

    borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

    borderStyle.setBorderLeft(BorderStyle.THIN);

    borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

    borderStyle.setBorderRight(BorderStyle.THIN);

    borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

    borderStyle.setBorderTop(BorderStyle.THIN);

    borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

    borderStyle.setAlignment(HorizontalAlignment.CENTER);

    return borderStyle;
  }

  public static <T> List<T> mapJsonToObjectList(Object json, Class<T> clazz)
      throws JsonMappingException, JsonProcessingException {
    List<T> list;
    ObjectMapper mapper = new ObjectMapper();
    TypeFactory t = TypeFactory.defaultInstance();
    list = mapper.convertValue(json, t.constructCollectionType(ArrayList.class, clazz));

    return list;
  }

  public static <T> Object getFieldValue(String fieldName, Class<? extends Object> classz, T t)
      throws NoSuchMethodException,
          SecurityException,
          IllegalAccessException,
          IllegalArgumentException,
          InvocationTargetException {

    Method method = null;
    Object value = null;
    try {
      method = classz.getMethod("get" + capitalize(fieldName));
    } catch (NoSuchMethodException nme) {
      method = classz.getMethod("get" + fieldName);
    }
    value = method.invoke(t, (Object[]) null);

    return value;
  }

  public static void setMarginAndColumnsWidth(SXSSFSheet sheet) {
    sheet.setMargin(Sheet.TopMargin, 2); // margins (top)
    sheet.setMargin(Sheet.BottomMargin, 2); // margins (bottom)
    sheet.setMargin(Sheet.LeftMargin, 2); // margins (left)
    sheet.setMargin(Sheet.RightMargin, 2); // margins (right)

    sheet.setDefaultColumnWidth(30);
  }

  public static void setColumnsCells(
      List<String> fieldNames, CellStyle headerStyle, int columnCount, Row row) {

    for (String fieldName : fieldNames) {
      Cell cell = row.createCell(columnCount++);
      cell.setCellValue(fieldName);
      cell.setCellStyle(headerStyle);
    }
  }

  public static void setDataCellsValue(Object value, CellStyle rowsStyle, Cell cell) {
    if (value != null) {
      if (value instanceof String) {
        cell.setCellValue((String) value);
        cell.setCellStyle(rowsStyle);

      } else if (value instanceof Long) {
        cell.setCellValue((Long) value);
        cell.setCellStyle(rowsStyle);

      } else if (value instanceof Integer) {
        cell.setCellValue((Integer) value);
        cell.setCellStyle(rowsStyle);

      } else if (value instanceof Double) {
        cell.setCellValue((Double) value);
        cell.setCellStyle(rowsStyle);

      } else if (value instanceof Boolean) {
        cell.setCellValue((Boolean) value);
        cell.setCellStyle(rowsStyle);

      } else {
        cell.setCellValue(String.valueOf(value));

        cell.setCellStyle(rowsStyle);
      }
    } else {
      cell.setBlank();

      cell.setCellStyle(rowsStyle);
    }
  }

  public static <T> ByteArrayInputStream writeToExcelCurrentPageOrWs(
      List<T> data, List<EntityColumns> columns) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {

      SXSSFSheet sheet = workbook.createSheet();
      setMarginAndColumnsWidth(sheet);
      CellStyle headerStyle = getHeaderStyle(workbook);
      CellStyle rowsStyle = getNormalStyle(workbook);

      List<String> headerVF = getFieldNamesVFFromClomuns(columns);

      List<String> fieldNames = getFieldNamesFromColumns(columns);
      int rowCount = 0;
      int columnCount = 0;
      Row row = sheet.createRow(rowCount++);

      setColumnsCells(headerVF, headerStyle, columnCount, row);

      writingData(data, fieldNames, data.get(0).getClass(), rowsStyle, sheet, rowCount);
      workbook.write(out);

    } catch (Exception e) {
      log.error("exception :", e);
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  public static <T> void writingData(
      List<T> data,
      List<String> fieldNames,
      Class<? extends Object> classz,
      CellStyle rowsStyle,
      SXSSFSheet sheet,
      int rowCount)
      throws NoSuchMethodException,
          SecurityException,
          IllegalAccessException,
          IllegalArgumentException,
          InvocationTargetException {
    for (T t : data) {
      Row row = sheet.createRow(rowCount++);
      int columnCount = 0;
      for (String fieldName : fieldNames) {
        Cell cell = row.createCell(columnCount);

        Object value = getFieldValue(fieldName, classz, t);
        setDataCellsValue(value, rowsStyle, cell);

        columnCount++;
      }
    }
  }

  public static boolean isRowEmpty(Row row) {
    boolean isEmpty = true;
    DataFormatter dataFormatter = new DataFormatter();

    if (row != null) {
      for (Cell cell : row) {
        if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
          isEmpty = false;
          break;
        }
      }
    }

    return isEmpty;
  }
}
