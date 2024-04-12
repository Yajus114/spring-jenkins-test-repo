/*
 *  Copyright 2012 Unicommerce Technologies (P) Limited . All Rights Reserved.
 *  UNICOMMERCE TECHONOLOGIES PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Jan 20, 2012
 *  @author singla
 */
package com.dawnbit.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author singla
 */
public class NumberUtils {

    private static String[] TENS = {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"};
    private static String[] UNITS = new String[]{
            "",
            " One",
            " Two",
            " Three",
            " Four",
            " Five",
            " Six",
            " Seven",
            " Eight",
            " Nine",
            " Ten",
            " Eleven",
            " Twelve",
            " Thirteen",
            " Fourteen",
            " Fifteen",
            " Sixteen",
            " Seventeen",
            " Eighteen",
            " Nineteen"};
    private static String[] DIGIT = {"", " Hundred", " Thousand", " Lakh", " Crore"};
    private static String[] VIETNAMESE_TEN = {"MƯỜI", "MƯỜI MỘT", "MƯỜI HAI", "MƯỜI BA", "MƯỜI BỐN", "MƯỜI LĂM", "MƯỜI SÁU", "MƯỜI BẢY", "MƯỜI TÁM", "MƯỜI CHÍN"};
    private static String[] VIETNAMESE_TWENTY = {"HAI MƯƠI", "BA MƯƠI", "BỐN MƯƠI", "NĂM MƯƠI", "SÁU MƯƠI", "BẢY MƯƠI", "TÁM MƯƠI", "CHÍN MƯƠI"};
    private static String[] VIETNAMESE_DIGIT = {"KHÔNG", "MỘT", "HAI", "BA", "BỐN", "NĂM", "SÁU", "BẢY", "TÁM", "CHÍN"};
    private static String[] VIETNAMESE_THOUSAND = {"", "NGHÌN,", "TRIỆU,", "TỶ,"};

    public static double round2(double d) {
        long result = Math.round((d * 100));
        return (result / 100.0);
    }

    public static double roundToDecimals(double d, int c) {
        long result = Math.round(d * Math.pow(10, c));
        return (result / Math.pow(10, c));
    }

    public static BigDecimal getPercentageFromTotal(BigDecimal totalAmount, BigDecimal percentage) {
        return totalAmount.multiply(percentage).divide(new BigDecimal(100).add(percentage), 2, RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal getPercentageFromBase(BigDecimal baseAmount, BigDecimal percentage) {
        return baseAmount.multiply(percentage).divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal getPercentageFromBaseAndPercentageAmount(BigDecimal baseAmount, BigDecimal percetageAmount) {
        if (greaterThan(baseAmount, BigDecimal.ZERO)) {
            return percetageAmount.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_EVEN).divide(baseAmount, 2, RoundingMode.HALF_EVEN).setScale(2,
                    RoundingMode.HALF_EVEN);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal newBigDecimal(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal divide(BigDecimal input, BigDecimal divisor) {
        if (BigDecimal.ZERO.equals(divisor)) {
            return BigDecimal.ZERO;
        }
        return input.divide(divisor, 2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal divide(BigDecimal input, Integer divisor) {
        if (divisor == 0) {
            return BigDecimal.ZERO;
        }
        return input.divide(new BigDecimal(divisor), 2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal multiply(BigDecimal input, BigDecimal multiplicand) {
        return input.multiply(multiplicand).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal multiply(BigDecimal input, int multiplicand) {
        return input.multiply(new BigDecimal(multiplicand)).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal multiply(BigDecimal input, double multiplicand) {
        return input.multiply(new BigDecimal(multiplicand)).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static boolean greaterThan(BigDecimal lhs, BigDecimal rhs) {
        return lhs.compareTo(rhs) == 1;
    }

    public static boolean lessThan(BigDecimal lhs, BigDecimal rhs) {
        return lhs.compareTo(rhs) == -1;
    }

    private static int intValue(char c) {
        return c - 48;
    }

    private static int digitCount(int num) {
        int cnt = 0;
        while (num > 0) {
            num = num / 10;
            cnt++;
        }
        return cnt;
    }

    private static String toWordsTwoDigit(int number) {
        if (number > 19) {
            return TENS[number / 10] + UNITS[number % 10];
        } else {
            return UNITS[number];
        }
    }

    private static String toWordsThreeDigit(int numq) {
        int numr, nq;
        nq = numq / 100;
        numr = numq % 100;
        if (numr == 0) {
            return UNITS[nq] + DIGIT[1];
        } else {
            return UNITS[nq] + DIGIT[1] + " and" + toWordsTwoDigit(numr);
        }
    }

    public static String toWords(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Zero or Negative number not for conversion");
        } else if (number == 0) {
            return "Zero";
        }

        StringBuilder builder = new StringBuilder();
        WHILE:
        while (number > 0) {
            int len = digitCount(number);
            switch (len) {
                case 7:
                case 6:
                    builder.append(toWordsTwoDigit(number / 100000) + DIGIT[3]);
                    number = number % 100000;
                    break;
                case 5:
                case 4:
                    builder.append(toWordsTwoDigit(number / 1000) + DIGIT[2]);
                    number = number % 1000;
                    break;
                case 3:
                    builder.append(toWordsThreeDigit(number));
                    break WHILE;
                case 2:
                    builder.append(toWordsTwoDigit(number));
                    break WHILE;
                case 1:
                    builder.append(UNITS[number]);
                    break WHILE;
                default:
                    builder.append(" ").append(toWords(number / 10000000)).append(DIGIT[4]);
                    number = number % 10000000;
                    break;
            }
        }
        return builder.deleteCharAt(0).toString();
    }

    public static void main(String[] args) {
        System.out.println(toVietnameseWords(15552790));
    }

    public static String toVietnameseWords(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Zero or Negative number not for conversion");
        } else if (number == 0) {
            return "";
        }
        String strNumber = String.valueOf(number);
        int x = strNumber.length();
        StringBuilder builder = new StringBuilder();
        int sk = 0;
        for (int i = 0; i < x; i++) {
            if ((x - i) % 3 == 2) {
                if (strNumber.charAt(i) == '1') {
                    builder.append(VIETNAMESE_TEN[intValue(strNumber.charAt(i + 1))]).append(" ");
                    i++;
                    sk = 1;

                } else if (strNumber.charAt(i) != '0') {
                    builder.append(VIETNAMESE_TWENTY[intValue(strNumber.charAt(i)) - 2]).append(" ");
                    sk = 1;
                }

            } else if (strNumber.charAt(i) != '0') {
                builder.append(VIETNAMESE_DIGIT[intValue(strNumber.charAt(i))]).append(" ");
                if ((x - i) % 3 == 0) {
                    builder.append("TRĂM ");
                    if (strNumber.charAt(i + 1) == '0')
                        builder.append("LẺ ");
                }
                sk = 1;
            }
            if ((x - i) % 3 == 1) {
                if (sk != 0)
                    builder.append(VIETNAMESE_THOUSAND[(x - i - 1) / 3]).append(" ");
                sk = 0;
            }
        }
        String toWords = builder.toString().trim();
        if (toWords.charAt(toWords.length() - 1) == ',') {
            toWords = toWords.substring(0, toWords.length() - 1);
        }
        return toWords.replaceAll("\\s+", " ");
    }
}
