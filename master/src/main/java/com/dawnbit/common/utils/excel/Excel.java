package com.dawnbit.common.utils.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Excel {
    public static List<Map<Integer, String>> getSheetData(final XSSFSheet xSSFSheet, final int skipRows) {
        final List<Map<Integer, String>> rowList = new ArrayList<>();
        final int lastRowNumber = xSSFSheet.getLastRowNum();
        if (lastRowNumber >= skipRows) {
            for (int i = 1; i <= xSSFSheet.getLastRowNum(); i++) {
                rowList.add(getRow(xSSFSheet.getRow(i)));
            }
        }
        return rowList;
    }

    public static Map<Integer, String> getRow(final Row row) {
        final Map<Integer, String> rowData = new HashMap<>();
        if (row != null) {
            final int lastDataInRow = row.getLastCellNum();
            for (int i = 0; i <= row.getLastCellNum(); i++) {
                rowData.put(i, getColumnData(row.getCell(i)));
            }
        }
        return rowData;
    }

    public static String getColumnData(final Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        return dateFormat.format(cell.getDateCellValue());
                    }
                    return NumberToTextConverter.toText(cell.getNumericCellValue());
                case STRING:
                    return String.valueOf(cell.getRichStringCellValue());
                case BOOLEAN:
                    return Boolean.toString(cell.getBooleanCellValue());
                case ERROR:
                    return Byte.toString(cell.getErrorCellValue());
                case FORMULA:
                    // Handle formulas if needed
                    break;
                case BLANK:
                    // Handle blank cells if needed
                    break;
                default:
                    // Handle other types if needed
                    break;
            }
        }
        return "";
    }

    public static Map<Integer, String> getHeaderRow(final XSSFSheet xSSFSheet) {
        return getRow(xSSFSheet.getRow(0));
    }

}
