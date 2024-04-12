package com.dawnbit.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.dawnbit.common.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExcelFileGen extends AbstractXlsxView {

    private String base64Logo;

    private String fileName;

    private List<Map<String, String>> headerRecordList;

    public ExcelFileGen() {

    }

    public ExcelFileGen(final String base64Logo, final String fileName) {
        this.base64Logo = base64Logo;
        this.fileName = fileName;
    }

    public ExcelFileGen(final List<Map<String, String>> headerRecordList, final String fileName) {
        this.headerRecordList = headerRecordList;
        this.fileName = fileName;
    }

    public ExcelFileGen(final String base64Logo, final List<Map<String, String>> headerRecordList,
                        final String fileName) {
        this.base64Logo = base64Logo;
        this.headerRecordList = headerRecordList;
        this.fileName = fileName;
    }

    /**
     * @return the base64Logo
     */
    public String getBase64Logo() {
        return this.base64Logo;
    }

    /**
     * @param base64Logo the base64Logo to set
     */
    public void setBase64Logo(final String base64Logo) {
        this.base64Logo = base64Logo;
    }

    /**
     * @return the headerRecordList
     */
    public List<Map<String, String>> getHeaderRecordList() {
        return this.headerRecordList;
    }

    /**
     * @param headerRecordList the headerRecordList to set
     */
    public void setHeaderRecordList(final List<Map<String, String>> headerRecordList) {
        this.headerRecordList = headerRecordList;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String generateExcelFile(final String contextPath, final String rootPath,
                                    final List<Map<String, Object>> dataList, final Map<String, String> keyReplacement,
                                    final List<String> skipColumns) throws CustomException, IOException {
        final Workbook wb = new XSSFWorkbook();
        try {
            final int dataListSize = dataList.size();
            if (dataListSize > 0) {

                int rowIndex = 0;
                final Sheet sheet = wb.createSheet(this.fileName);
                if ((this.base64Logo != null) && !this.base64Logo.isEmpty()) {
                    try {
                        final String base64Image = this.base64Logo.split(",")[1];
//						final byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
                        final byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                        if (imageBytes != null) {
                            sheet.createRow(rowIndex);
                            sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 1));
                            final Drawing<?> drawing = sheet.createDrawingPatriarch();
                            final int pictureIdx = wb.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
                            final CreationHelper helper = wb.getCreationHelper();
                            final ClientAnchor anchor = helper.createClientAnchor();
                            anchor.setCol1(0); // Column B
                            anchor.setRow1(1); // Row 3
                            anchor.setCol2(2); // Column C
                            anchor.setRow2(3); // Row 4
                            drawing.createPicture(anchor, pictureIdx);
                            rowIndex = rowIndex + 4;
                        }
                    } catch (final Exception ex) {
                        log.info(ex.getMessage());
                    }
                }

                CellStyle style = wb.createCellStyle();
                style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                final CellStyle styleBorder = wb.createCellStyle();
                styleBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex());

                final Font font = wb.createFont();
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                font.setColor(IndexedColors.WHITE.getIndex());
                style.setWrapText(true);
                style.setFont(font);

                for (int i = 0; i < dataListSize; i++) {
                    final Map<String, Object> map = dataList.get(i);
                    final Row tableRowHeader = i == 0 ? sheet.createRow(rowIndex + i) : null;
                    final Row tableRow = sheet.createRow(rowIndex + 1 + i);
                    int k = 0;
                    final Set<String> keySet = map.keySet();
                    for (final String key : keySet) {
                        if (skipColumns.indexOf(key) < 0) {
                            if (i == 0) {
                                final Cell cellHeader = tableRowHeader.createCell(k);
                                cellHeader.setCellValue(String
                                        .valueOf(keyReplacement.containsKey(key) ? keyReplacement.get(key) : key));
                                sheet.autoSizeColumn(k);
                            }
                            this.setCellValue(tableRow.createCell(k), map.get(key));
                            k++;
                        }
                    }
                }
                final String filePath = new File(rootPath).getAbsolutePath();
                final File dir = new File(filePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = (this.fileName != null ? this.fileName : new Date().getTime()) + ".xls";
                if (wb instanceof XSSFWorkbook) {
                    fileName += "x";
                }
                FileOutputStream fileOut;
                fileOut = new FileOutputStream(rootPath + File.separatorChar + fileName);
                wb.write(fileOut);
                fileOut.close();
                return contextPath + fileName;
            }
            throw new CustomException("Data not available");
        } catch (final Exception e) {
            log.info(e.getMessage());
            throw new CustomException("Unable to create file error: " + e.getMessage());
        } finally {
            wb.close();
        }

    }

    private void setCellValue(final Cell cell, final Object obj) {
        if ((obj == null) || (obj == "")) {
            cell.setCellValue("");
        } else if (obj.getClass().isAssignableFrom(Integer.class)) {
            cell.setCellValue(Integer.valueOf(String.valueOf(obj)));
        } else if (obj.getClass().isAssignableFrom(Long.class)) {
            cell.setCellValue(Long.valueOf(String.valueOf(obj)));
        } else if (obj.getClass().isAssignableFrom(Double.class)) {
            cell.setCellValue(Double.valueOf(String.valueOf(obj)));
        } else if (obj.getClass().isAssignableFrom(Boolean.class)) {
            cell.setCellValue(Boolean.valueOf(String.valueOf(obj)));
        } else {
            cell.setCellValue(String.valueOf(obj));
        }
    }

    private int setLogoInExcel(final Sheet sheet, final Workbook workbook, int rowIndex) {
        try {
            final String base64Image = this.base64Logo.split(",")[1];
//			final byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
            final byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            if (imageBytes != null) {
                sheet.createRow(rowIndex);
                sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 1));
                final Drawing<?> drawing = sheet.createDrawingPatriarch();
                final int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
                final CreationHelper helper = workbook.getCreationHelper();
                final ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(0);
                anchor.setRow1(1);
                anchor.setCol2(2);
                anchor.setRow2(3);
                drawing.createPicture(anchor, pictureIdx);
                rowIndex = rowIndex + 4;
            }
        } catch (final Exception ex) {
            log.info(ex.getMessage());
        }
        return rowIndex;
    }

    @SuppressWarnings({"unused", "unchecked"})
    @Override
    protected void buildExcelDocument(final Map<String, Object> model, final Workbook workbook,
                                      final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String sheetName = (String) model.get("sheetname");
        final Map<String, String> headerReplacementKey = (Map<String, String>) model.get("headerReplacementKey");
        final Map<String, String> thirdHeaderReplacementKey = (Map<String, String>) model.get("thirdHeaderReplacementKey");
        final Map<String, String> tableHeaderGroupMap = (Map<String, String>) model.get("tableHeaderGroupMap");
        final Map<String, String> thirdTopHeaderGroupMap = (Map<String, String>) model.get("thirdTopHeaderGroupMap");
        final List<Map<String, Object>> dataList = (List<Map<String, Object>>) model.get("dataList");
        final List<String> skipColumnsKey = (List<String>) model.get("skipColumnsKey");
        final List<Map<String, Object>> headerDataList = (List<Map<String, Object>>) model.get("headerDataList");
        // end

        final int dataListSize = dataList.size();
        if (dataListSize > 0) {
            final Sheet sheet = workbook.createSheet(sheetName);
            int rowIndex = 0;
            if ((this.base64Logo != null) && !this.base64Logo.isEmpty()) {
                rowIndex = this.setLogoInExcel(sheet, workbook, rowIndex);
            }

            /** this cellstyle is used if table header is multi line */
            final CellStyle headerGroupStyle = workbook.createCellStyle();
            headerGroupStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerGroupStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerGroupStyle.setBorderBottom(BorderStyle.THIN);
            headerGroupStyle.setBorderLeft(BorderStyle.THIN);
            headerGroupStyle.setBorderRight(BorderStyle.THIN);
            headerGroupStyle.setBorderTop(BorderStyle.THIN);
            headerGroupStyle.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            headerGroupStyle.setTopBorderColor(IndexedColors.WHITE.getIndex());
            headerGroupStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());
            headerGroupStyle.setRightBorderColor(IndexedColors.WHITE.getIndex());
            headerGroupStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerGroupStyle.setAlignment(HorizontalAlignment.CENTER);
            headerGroupStyle.setWrapText(true);

            final CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            final Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            style.setWrapText(true);

            if (thirdTopHeaderGroupMap != null) {
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
                style.setRightBorderColor(IndexedColors.WHITE.getIndex());
                style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            }
            if (tableHeaderGroupMap != null) {
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
                style.setRightBorderColor(IndexedColors.WHITE.getIndex());
                style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            }
            style.setFont(font);
            headerGroupStyle.setFont(font);

            // @author DB-0059
            if (headerDataList != null) {
                for (int i = 0; i < headerDataList.size(); i++) {
                    final CellStyle headerStyle = workbook.createCellStyle();
                    headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                    headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                    headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                    headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                    headerStyle.setFont(font);
                    final Map<String, Object> map = headerDataList.get(i);
                    final Row tableRowHeader = i == 0 ? sheet.createRow(rowIndex + i) : null;
                    final Row tableRow = sheet.createRow(rowIndex + 1 + i);
                    int k = 0;
                    final Set<Entry<String, Object>> keySet = map.entrySet();
                    for (final Entry<String, Object> key : keySet) {
                        if (skipColumnsKey.indexOf(key.getKey()) < 0) {
                            if (i == 0) {
                                final Cell cellHeader = tableRowHeader.createCell(k);
                                cellHeader.setCellStyle(headerStyle);
                                cellHeader.setCellValue(String.valueOf(key.getKey()));
                                sheet.autoSizeColumn(k);
                            }
                            this.setCellValue(tableRow.createCell(k), map.get(key.getKey()));
                            k++;
                        }
                    }
                }
                if (!headerDataList.isEmpty()) {
                    rowIndex += headerDataList.size() + 2;
                }
            }
            // end

            for (int i = 0; i < dataListSize; i++) {

                final Map<String, Object> map = dataList.get(i);
                final Row tableRowHeader = i == 0 ? sheet.createRow(rowIndex + i) : null;
                Row tableRowHeader2 = null;
                Row tableRowHeader3 = null;
                Row tableRow = null;

                /** If table header is 3 lined, adding multiple header rows */

                if (thirdTopHeaderGroupMap != null) {
                    tableRowHeader3 = i == 0 ? sheet.createRow(rowIndex + 1 + i) : null;
                    tableRowHeader2 = i == 0 ? sheet.createRow(rowIndex + 2 + i) : null;
                    tableRow = sheet.createRow(rowIndex + 3 + i);
                } else {
                    /** If table header is multi line, adding multiple header rows */
                    if (tableHeaderGroupMap != null) {
                        tableRowHeader2 = i == 0 ? sheet.createRow(rowIndex + 1 + i) : null;
                        tableRow = sheet.createRow(rowIndex + 2 + i);
                    } else {
                        tableRow = sheet.createRow(rowIndex + 1 + i);
                    }
                }

                int k = 0;
                final Set<String> keySet = map.keySet();
                for (final String key : keySet) {
                    if (skipColumnsKey.indexOf(key) < 0) {
                        if (i == 0) {
                            /** If table header is 3 lined, creating cells for multiple header rows */
                            if (thirdTopHeaderGroupMap != null) {
                                final Cell cellHeader = tableRowHeader.createCell(k);
                                cellHeader.setCellStyle(headerGroupStyle);
                                cellHeader.setCellValue(String.valueOf(thirdTopHeaderGroupMap.get(key)));
                                sheet.autoSizeColumn(k);

                                final Cell cellHeader1 = tableRowHeader3.createCell(k);
                                cellHeader1.setCellStyle(style);
                                final long count = thirdTopHeaderGroupMap.values().stream()
                                        .filter(v -> v.equals(String.valueOf(key))).count();
                                if (count == 1) {
                                    cellHeader1.setCellValue("");
                                } else {
                                    cellHeader1.setCellValue(String.valueOf(key));
                                }
                                sheet.autoSizeColumn(k);
                            }
                            /** If table header is multi line, creating cells for multiple header rows */
                            if (tableHeaderGroupMap != null) {

                                final Cell cellHeader = tableRowHeader.createCell(k);
                                cellHeader.setCellStyle(headerGroupStyle);
                                cellHeader.setCellValue(String.valueOf(tableHeaderGroupMap.get(key)));
                                sheet.autoSizeColumn(k);

                                final Cell cellHeader1 = tableRowHeader2.createCell(k);
                                cellHeader1.setCellStyle(style);
                                final long count = tableHeaderGroupMap.values().stream()
                                        .filter(v -> v.equals(String.valueOf(key))).count();
                                if (count == 1) {
                                    cellHeader1.setCellValue("");
                                } else {
                                    cellHeader1.setCellValue(String.valueOf(key));
                                }

                                sheet.autoSizeColumn(k);

                            }
                            /** If table header is single line, creating cells for single row */
                            if (tableHeaderGroupMap == null) {

                                final Cell cellHeader = tableRowHeader.createCell(k);
                                cellHeader.setCellStyle(style);
                                cellHeader.setCellValue(String.valueOf(key));
                                sheet.autoSizeColumn(k);
                            }

                        }
                        this.setCellValue(tableRow.createCell(k), map.get(key));
                        k++;
                    }
                }

                /** Grouping headers cells if table header is multi line */
                if ((thirdTopHeaderGroupMap != null) && (i == 0)) {
                    final List<String> tableHeaderGroupList = new ArrayList<>();
                    thirdTopHeaderGroupMap.forEach((key, value) -> tableHeaderGroupList.add(key.split("_")[0]));
                    final Set<String> tableHeaderGroupSet = tableHeaderGroupList.stream().collect(Collectors.toSet());
                    log.info("---------------------");
                    log.info("# tableHeaderGroupList : " + tableHeaderGroupList);
                    log.info("# tableHeaderGroupSet : " + tableHeaderGroupSet);
                    for (final String col : tableHeaderGroupSet) {
                        if ((col != null) && !col.isBlank()
                                && (tableHeaderGroupList.indexOf(col) != tableHeaderGroupList.lastIndexOf(col))) {
                            log.info("# col : " + col + ", s=>" + tableHeaderGroupList.indexOf(col) + ", e=>" + tableHeaderGroupList.lastIndexOf(col));
                            sheet.addMergedRegion(
                                    new CellRangeAddress(tableRowHeader3.getRowNum(), tableRowHeader3.getRowNum(),
                                            tableHeaderGroupList.indexOf(col), tableHeaderGroupList.lastIndexOf(col)));
                        }

                    }
                }

                /** Grouping headers cells if table header is multi line */
                if ((tableHeaderGroupMap != null) && (i == 0)) {
                    final List<String> tableHeaderGroupList = new ArrayList<>();
                    tableHeaderGroupMap.forEach((key, value) -> tableHeaderGroupList.add(value));
                    final Set<String> tableHeaderGroupSet = tableHeaderGroupList.stream().collect(Collectors.toSet());

                    for (final String col : tableHeaderGroupSet) {
                        if ((col != null) && !col.isBlank()
                                && (tableHeaderGroupList.indexOf(col) != tableHeaderGroupList.lastIndexOf(col))) {
                            sheet.addMergedRegion(
                                    new CellRangeAddress(tableRowHeader.getRowNum(), tableRowHeader.getRowNum(),
                                            tableHeaderGroupList.indexOf(col), tableHeaderGroupList.lastIndexOf(col)));
                        }

                    }
                }

            }

            if ((tableHeaderGroupMap != null) && (headerReplacementKey != null)) {
                int index;
                if (headerDataList.isEmpty()) {
                    index = 2;
                } else {
                    index = 5;
                }
                sheet.getRow(index)
                        .forEach(cell -> this.setCellValue(cell, headerReplacementKey.get(cell.getStringCellValue())));
            }

            if ((thirdTopHeaderGroupMap != null) && (thirdHeaderReplacementKey != null)) {
                int index;
                if (headerDataList.isEmpty()) {
                    index = 2;
                } else {
                    index = 4;
                }
                sheet.getRow(index)
                        .forEach(cell -> this.setCellValue(cell, thirdHeaderReplacementKey.get(cell.getStringCellValue())));
            }

            response.setHeader("Content-Encoding", "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + sheetName + ".xlsx");
            response.setContentType("application/force-download");
        }
    }

}
