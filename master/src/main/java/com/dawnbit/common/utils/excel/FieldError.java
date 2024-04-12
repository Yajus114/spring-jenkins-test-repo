package com.dawnbit.common.utils.excel;

public class FieldError {

    private String field;

    private String value;

    private String error;

    public FieldError() {

    }

    /**
     * @param field
     * @param value
     * @param error
     */
    public FieldError(final String field, final String value, final String error) {
        super();
        this.field = field;
        this.value = value;
        this.error = error;
    }

    /**
     * @return the field
     */
    public String getField() {
        return this.field;
    }

    /**
     * @param field the field to set
     */
    public void setField(final String field) {
        this.field = field;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * @return the error
     */
    public String getError() {
        return this.error;
    }

    /**
     * @param error the error to set
     */
    public void setError(final String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "FieldError [field=" + this.field + ", value=" + this.value + ", error=" + this.error + "]";
    }
}
