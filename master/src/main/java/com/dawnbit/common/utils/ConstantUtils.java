package com.dawnbit.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DB
 */
public class ConstantUtils {

    //    public static final String SYSTEM = "SYSTEM";
    public static final String QUERY = "QUERY";
    public static final String SORTING_ALIAS = "SORTING_ALIAS";
    public static final String DATA_LIST = "dataList";
    public static final String TOTAL_ROWS = "TOTAL_ROWS";
    public static final String TOTAL_PAGES = "TOTAL_PAGES";
    //    public static final String DESC = "DESC";
//    public static final String DATE_FORMAT = "dd MMM, YYYY";
    public static final String EMAIL_APPLICATION_ID = "EMAIL_APPLICATION_ID";
    public static final String EMAIL_USER_ID = "EMAIL_USER_ID";
    public static final String EMAIL_APPLICATION_AUTHORIZATION = "EMAIL_APPLICATION_AUTHORIZATION";
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final Map<Boolean, String> BOOLEAN_MAP = new HashMap<>();

    static {
        BOOLEAN_MAP.put(true, "Yes");
        BOOLEAN_MAP.put(false, "No");
    }

//    public static final String LOGIN_DETAILS = "LOGIN_DETAILS";
//    public static final String DATA_NOT_EXIST = "Data not exist";
//    public static final String SKIP_COLUMNS_KEY = "skipColumnsKey";
//    public static final String SHEETNAME = "sheetname";
//    public static final String INDIAN_TIMEZONE = "Asia/Kolkata";

}
