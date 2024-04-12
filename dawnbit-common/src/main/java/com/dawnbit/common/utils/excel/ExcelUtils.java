package com.dawnbit.common.utils.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtils {

    /**
     * @param xSSFWorkbook
     * @return
     * @description get list of all sheets available in workbook
     */
    public static List<Sheet> getExcelSheets(final XSSFWorkbook xSSFWorkbook) {
        final List<Sheet> Sheets = new ArrayList<>();
        for (int i = 0; i < xSSFWorkbook.getNumberOfSheets(); i++) {
            Sheets.add(xSSFWorkbook.getSheetAt(i));
        }
        return Sheets;
    }

    /**
     * @param file
     * @return
     * @throws IOException
     * @description get list of all sheets available in file
     */
    public static List<Sheet> getExcelSheets(final File file) throws IOException {
        List<Sheet> Sheets = new ArrayList<>();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            final XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(inputStream);
            Sheets = getExcelSheets(xSSFWorkbook);
            inputStream.close();
            xSSFWorkbook.close();
        } catch (final IOException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            inputStream.close();
        }
        return Sheets;
    }

    /**
     * @param multipartFile
     * @return
     * @description get list of all sheets available in file
     */
    public static List<Sheet> getExcelSheets(final MultipartFile multipartFile) {
        List<Sheet> Sheets = new ArrayList<>();
        try {
            final InputStream inputStream = multipartFile.getInputStream();
            XSSFWorkbook xSSFWorkbook = null;
            HSSFWorkbook hSSFWorkbook = null;
            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            log.info("extension is------------" + extension);
            if (extension.equalsIgnoreCase("XLSX")) {

                xSSFWorkbook = new XSSFWorkbook(inputStream);
                Sheets = getExcelSheets(xSSFWorkbook);
                inputStream.close();
                xSSFWorkbook.close();
                log.info("inside if" + Sheets.size());
            } else {
                hSSFWorkbook = new HSSFWorkbook(inputStream);
                Sheets = getExcelSheets(hSSFWorkbook);
                inputStream.close();
                hSSFWorkbook.close();
                log.info("inside else" + Sheets.size());
            }
//			inputStream.close();
//			if(extension.equalsIgnoreCase("XLSX")) {
//				xSSFWorkbook.close();
//			}else {
//				hSSFWorkbook.close();
//			}
        } catch (final IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return Sheets;
    }

    private static List<Sheet> getExcelSheets(HSSFWorkbook hSSFWorkbook) {
        final List<Sheet> Sheets = new ArrayList<>();
        for (int i = 0; i < hSSFWorkbook.getNumberOfSheets(); i++) {
            Sheets.add(hSSFWorkbook.getSheetAt(i));
        }
        return Sheets;
    }

    /**
     * @param xSSFWorkbook
     * @return
     * @description resize all columns of all sheets available in workbook
     */
    public static List<Sheet> autoResizeAllColumns(final XSSFWorkbook xSSFWorkbook) {
        final List<Sheet> Sheets = getExcelSheets(xSSFWorkbook);
        for (int i = 0; i < Sheets.size(); i++) {
            Sheets.set(i, autoResizeAllColumns(Sheets.get(i)));
        }
        return Sheets;
    }

    /**
     * @param Sheet
     * @return
     * @description resize all columns of sheet
     */
    public static Sheet autoResizeAllColumns(final Sheet Sheet) {
        for (int i = 0; i <= Sheet.getRow(0).getLastCellNum(); i++) {
            Sheet.autoSizeColumn(i);
        }
        return Sheet;
    }

    /**
     * @param Sheet
     * @return
     * @description get header row column list with number and name of sheets
     */
    public static List<ExcelColumnUtils> getHeaderColumnNames(final Sheet Sheet) {
        return getSheetRowData(Sheet.getRow(0));
    }

    /**
     * @param cell
     * @return
     * @description get value of cell
     */
    @SuppressWarnings("deprecation")
	/*
	public static String getColumnData(final Cell cell) {
		if (cell != null) {
			switch (cell.getCellTypeEnum()) {
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
				break;
			case BLANK:
				break;
			default:
				break;
			}
		}
		return null;
	}
*/

    /**
     * @description get value of cell
     * @param cell
     * @return
     */
    public static String getColumnData(final Cell cell) {
        if (cell != null) {
            CellType cellType = cell.getCellType();
            switch (cellType) {
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        return dateFormat.format(cell.getDateCellValue());
                    }
                    return String.valueOf(cell.getNumericCellValue());
                case STRING:
                    return cell.getStringCellValue();
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case ERROR:
                    return Byte.toString(cell.getErrorCellValue());
                case FORMULA:
                case BLANK:
                    return ""; // Handle FORMULA and BLANK cells as needed
                default:
                    return ""; // Handle other cell types if necessary
            }
        }
        return null;
    }

    /**
     * @param row
     * @return
     * @description get row column list with number and name of sheets
     */
    public static List<ExcelColumnUtils> getSheetRowData(final Row row) {
        final List<ExcelColumnUtils> excelColumns = new ArrayList<>();
        for (int i = 0; i <= row.getLastCellNum(); i++) {
            final ExcelColumnUtils excelColumn = new ExcelColumnUtils(i, getColumnData(row.getCell(i)));
            excelColumns.add(excelColumn);
        }
        return excelColumns;
    }

    /**
     * @param row
     * @return
     * @description get row Data from column [first with index 0 to last column
     * having data, it include middle rows with no data and set it as
     * empty]
     */
    public static Map<Integer, String> getRow(final Row row) {
        final Map<Integer, String> rowData = new HashMap<>();
        try {
            for (int i = 0; i < row.getLastCellNum(); i++) {
                rowData.put(i, getColumnData(row.getCell(i)));
            }
        } catch (final NullPointerException ex) {
            log.error(ex.getMessage());
        }
        return rowData;
    }

    /**
     * @param Sheet
     * @return
     * @description get record list of sheets
     */
    public static List<Map<Integer, String>> getSheetData(final Sheet Sheet) {
        final List<Map<Integer, String>> rowList = new ArrayList<>();
        for (int i = 0; i <= Sheet.getLastRowNum(); i++) {
            rowList.add(getRow(Sheet.getRow(i)));
        }
        return rowList;
    }

    /**
     * @param Sheet
     * @param skipRows
     * @return
     * @description get record list of sheets excel with skip rows
     */
    public static List<Map<Integer, String>> getSheetData(final Sheet Sheet, final int skipRows) {
        final List<Map<Integer, String>> rowList = new ArrayList<>();
        final int lastRowNumber = Sheet.getLastRowNum();
        if (lastRowNumber >= skipRows) {
            for (int i = 1; i <= Sheet.getLastRowNum(); i++) {
                rowList.add(getRow(Sheet.getRow(i)));
            }
        }
        return rowList;
    }

    /**
     * @param Sheet
     * @return
     * @description get record list of sheets with assume first row as a header and
     * it's value as a key
     */
    public static List<Map<Integer, String>> getSheetDataWithHeaderAsKey(final Sheet Sheet) {
        final List<Map<Integer, String>> rowList = new ArrayList<>();
        final List<ExcelColumnUtils> columnList = getHeaderColumnNames(Sheet);
        final int totalColumns = columnList.size();
        final int lastRowNumber = Sheet.getLastRowNum();
        if (totalColumns > 0 && lastRowNumber > 0) {
            for (int i = 1; i <= Sheet.getLastRowNum(); i++) {
                rowList.add(getRow(Sheet.getRow(i)));
            }
        }
        return rowList;
    }

    /**
     * @param headerRow
     * @param row
     * @return
     * @description get record with header value as a key
     */
    public static Map<String, String> getRowWithHeaderAsKey(final Map<Integer, String> headerRow, final Row row) {
        final Map<String, String> rowData = new HashMap<>();
        for (int i = 0; i <= row.getLastCellNum(); i++) {
            rowData.put(headerRow.getOrDefault(i, "col" + i), getColumnData(row.getCell(i)));
        }
        return rowData;
    }

    /**
     * @param Sheet
     * @return
     * @description get header of sheets with assume first row as header
     */
    public static Map<Integer, String> getHeaderRow(final Sheet Sheet) {
        return getRow(Sheet.getRow(0));
    }
}
