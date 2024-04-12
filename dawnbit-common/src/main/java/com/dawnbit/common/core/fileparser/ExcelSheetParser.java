package com.dawnbit.common.core.fileparser;

import com.dawnbit.common.utils.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class ExcelSheetParser implements FileParser {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelSheetParser.class);

    private final File file;
    private boolean containsHeader = true;
    private String sheetName;
    private boolean xlsx;

    public ExcelSheetParser(String filePath, String sheetName, boolean containsHeader) {
        this(filePath, sheetName);
        this.containsHeader = containsHeader;
    }

    public ExcelSheetParser(String filePath, boolean containsHeader) {
        this(filePath);
        this.containsHeader = containsHeader;
    }

    public ExcelSheetParser(String filePath, String sheetName) {
        this(filePath);
        this.sheetName = sheetName;
    }

    public ExcelSheetParser(String filePath) {
        this.file = new File(filePath);
        if (!this.file.exists()) {
            throw new IllegalArgumentException("No such file exists at path :" + filePath);
        }
        if (filePath.endsWith(".xlsx")) {
            xlsx = true;
        }
    }

    @Override
    public Iterator<Row> parse() {
        if (xlsx) {
            try {
                return parseXLSX();
            } catch (Exception e) {
                LOG.error("error parsing XLSX:" + file.getAbsolutePath(), e);
                return parseXLS();
            }
        } else {
            try {
                return parseXLS();
            } catch (Exception e) {
                LOG.error("error parsing XLS:" + file.getAbsolutePath(), e);
                return parseXLSX();
            }
        }
    }

    private Iterator<Row> parseXLSX() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet s;
            if (StringUtils.isBlank(sheetName)) {
                s = workbook.getSheetAt(0);
            } else {
                s = workbook.getSheet(sheetName);
            }
            return new XLSXSheetIterator(s, containsHeader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Iterator<Row> parseXLS() {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet s;
            if (StringUtils.isBlank(sheetName)) {
                s = workbook.getSheetAt(0);
            } else {
                s = workbook.getSheet(sheetName);
            }
            return new XLSSheetIterator(s, containsHeader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class XLSXSheetIterator implements Iterator<Row> {

        private XSSFSheet xlsxSheet;
        private int lineNo;
        private Map<String, Integer> columnNamesToIndex;
        private String[] columnNames;

        public XLSXSheetIterator(XSSFSheet sheet, boolean containsHeader) {
            this.xlsxSheet = sheet;
            if (containsHeader) {
                // read the column names and store the position for headers
                columnNames = getColumnValues(sheet.getRow(lineNo++));
                columnNamesToIndex = new HashMap<String, Integer>(columnNames.length);
                for (int i = 0; i < columnNames.length; i++) {
                    columnNamesToIndex.put(StringUtils.removeNonWordChars(columnNames[i]).toLowerCase(), i);
                }
            }
        }

        private String[] getColumnValues(XSSFRow row) {
            if (row == null) {
                return new String[0];
            } else {
                String[] columnValues = new String[row.getLastCellNum()];
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    columnValues[i] = row.getCell(i) != null ? String.valueOf(row.getCell(i)) : null;
                }
                return columnValues;
            }
        }

        @Override
        public boolean hasNext() {
            return lineNo <= xlsxSheet.getLastRowNum();
        }

        public String getColumnNames() {
            StringBuilder builder = new StringBuilder();
            for (String s : columnNames) {
                builder.append(StringEscapeUtils.escapeCsv(s)).append(',');
            }
            return builder.deleteCharAt(builder.length() - 1).toString();
        }

        @Override
        public Row next() {
            if (hasNext()) {
                String[] columnValues = getColumnValues(xlsxSheet.getRow(lineNo++));
                if (columnNamesToIndex != null) {
                    return new Row(lineNo, columnValues, columnNamesToIndex);
                } else {
                    return new Row(lineNo, columnValues);
                }
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public void skip(int noOfLines) {
            while (noOfLines-- > 0 && hasNext()) {
                lineNo++;
            }
        }

    }

    public static class XLSSheetIterator implements Iterator<Row> {

        private HSSFSheet xlsSheet;
        private int lineNo;
        private Map<String, Integer> columnNamesToIndex;
        private String[] columnNames;

        public XLSSheetIterator(HSSFSheet sheet, boolean containsHeader) {
            this.xlsSheet = sheet;
            if (containsHeader) {
                // read the column names and store the position for headers
                columnNames = getColumnValues(sheet.getRow(lineNo++));
                columnNamesToIndex = new HashMap<String, Integer>(columnNames.length);
                for (int i = 0; i < columnNames.length; i++) {
                    columnNamesToIndex.put(StringUtils.removeNonWordChars(columnNames[i]).toLowerCase(), i);
                }
            }
        }

        private String[] getColumnValues(HSSFRow row) {
            if (row == null) {
                return new String[0];
            } else {
                String[] columnValues = new String[row.getLastCellNum()];
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    columnValues[i] = row.getCell(i) != null ? String.valueOf(row.getCell(i)) : null;
                }
                return columnValues;

            }
        }

        @Override
        public boolean hasNext() {
            return lineNo <= xlsSheet.getLastRowNum();
        }

        public String getColumnNames() {
            StringBuilder builder = new StringBuilder();
            for (String s : columnNames) {
                builder.append(StringEscapeUtils.escapeCsv(s)).append(',');
            }
            return builder.deleteCharAt(builder.length() - 1).toString();
        }

        @Override
        public Row next() {
            if (hasNext()) {
                String[] columnValues = getColumnValues(xlsSheet.getRow(lineNo++));
                if (columnNamesToIndex != null) {
                    return new Row(lineNo, columnValues, columnNamesToIndex);
                } else {
                    return new Row(lineNo, columnValues);
                }
            }
            throw new NoSuchElementException();
        }

        public void skip(int noOfLines) {
            while (noOfLines-- > 0 && hasNext()) {
                lineNo++;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
