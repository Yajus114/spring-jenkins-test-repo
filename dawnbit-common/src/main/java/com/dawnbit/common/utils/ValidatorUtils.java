package com.dawnbit.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {

    private static final String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";
    private static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)+)";
    private static final Pattern PATTERN_EMAIL = java.util.regex.Pattern.compile("^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + "$",
            java.util.regex.Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_USERNAME = Pattern.compile("^" + ATOM + "+(\\." + ATOM + "+)*(@" + DOMAIN + ")?$",
            java.util.regex.Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_MOBILE = Pattern.compile("^(91|0)?([7-9][0-9]{9})$");
    private static final Pattern PATTERN_PINCODE = Pattern.compile("^[1-9][0-9]{5}$");
    private static final Pattern PATTERN_SQL_COLUMN_NAME = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*$");

    public static String getValidMobileOrNull(String value) {
        if (value == null) {
            return null;
        }
        value = value.replaceAll("[^\\d]", "");
        Matcher m = PATTERN_MOBILE.matcher(value);
        if (m.matches()) {
            return m.group(2);
        } else {
            return null;
        }
    }

    public static boolean isEmailValid(String value) {
        if (StringUtils.isNotEmpty(value)) {
            Matcher m = PATTERN_EMAIL.matcher(value);
            return m.matches();
        } else {
            return false;
        }
    }

    public static String validateAndNormalizeEmail(String strEmails) {
        if (strEmails == null) {
            return null;
        }
        strEmails = strEmails.replaceAll("\\s", "");
        List<String> emails = new ArrayList<String>();
        for (String email : strEmails.split(",")) {
            if (isEmailValid(email)) {
                emails.add(email);
            } else {
                throw new IllegalArgumentException("invalid email:[" + email + "]");
            }
        }
        return StringUtils.join(',', emails);
    }

    public static boolean isPincodeValid(String value) {
        if (StringUtils.isNotEmpty(value)) {
            Matcher m = PATTERN_PINCODE.matcher(value);
            return m.matches();
        } else {
            return false;
        }
    }

    public static boolean isUsernameValid(String value) {
        if (StringUtils.isNotEmpty(value)) {
            Matcher m = PATTERN_USERNAME.matcher(value);
            return m.matches();
        } else {
            return false;
        }
    }

    public static boolean isSqlColumnNameValid(String value) {
        if (StringUtils.isNotEmpty(value)) {
            Matcher m = PATTERN_SQL_COLUMN_NAME.matcher(value);
            return m.matches();
        } else {
            return false;
        }
    }

    public static <T> T getOrDefaultValue(T newValue, T defaultValue) {
        return newValue != null ? newValue : defaultValue;
    }
}
