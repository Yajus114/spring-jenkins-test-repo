package com.dawnbit.common.utils.excel;

public class ExcelColumnUtils {

    private int columnNumber;
    private String columnData;

    public ExcelColumnUtils(final int columnNumber, final String columnData) {
        this.columnNumber = columnNumber;
        this.columnData = columnData;
    }

    /**
     * @return the columnNumber
     */
    public int getColumnNumber() {
        return this.columnNumber;
    }

    /**
     * @param columnNumber the columnNumber to set
     */
    public void setColumnNumber(final int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * @return the columnData
     */
    public String getColumnData() {
        return this.columnData;
    }

    /**
     * @param columnData the columnData to set
     */
    public void setColumnData(final String columnData) {
        this.columnData = columnData;
    }
}
