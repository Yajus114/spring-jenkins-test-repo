package com.dawnbit.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateToWords {

    private static final String[] specialNamesMonthDay = {
            "",
            " First",
            " Second",
            " Third",
            " Fourth",
            " Fifth",
            " Sixth",
            " Seventh",
            " Eighth",
            " Nineth",
            " Tenth",
            " Eleventh",
            " Twelveth",
            " Thirteenth",
            " Fourteenth",
            " Fifteenth",
            " Sixteenth",
            " Seventeenth",
            " Eighteenth",
            " Nineteenth",
            " Twenth",
            " Twenty first",
            " Twenty second",
            " Twenty third",
            " Twenty fourth",
            " Twenty fifth",
            " Twenty sixth",
            " Twenty seventh",
            " Twenty eighth",
            " Twenty nineth",
            " Thirty",
            " Thirty first"

    };
    private static final String[] specialNames = {
            "",
            " Thousand"

    };

    private static final String[] tensNames = {
            "",
            " Ten",
            " Twenty",
            " Thirty",
            " Forty",
            " Fifty",
            " Sixty",
            " Seventy",
            " Eighty",
            " Ninety"
    };

    private static final String[] numNames = {
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
            " Nineteen"
    };

    public static String dateToWords(Date date) {
        String strDateToWords = null;
        String dateInString = DateUtils.dateToString(date, "dd-MM-yyyy");
        if (validateDate(dateInString)) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            ParsePosition parsePosition = new ParsePosition(0);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormatter.parse(dateInString, new ParsePosition(0)));
            DateFormat format2 = new SimpleDateFormat("MMMMM ");

            int day = cal.get(Calendar.DATE);
            strDateToWords = getMonthDay(day);
            strDateToWords += " Of " + format2.format(cal.getTime());

            int year = cal.get(Calendar.YEAR);
            strDateToWords += " " + convert(year);

            // System.out.println(strDateToWords.toLowerCase());
        }
        return strDateToWords;
    }

    public static String getMonthDay(int day) {
        return specialNamesMonthDay[day];
    }

    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20) {
            current = numNames[number % 100];
            number /= 100;
        } else {
            current = numNames[number % 10];
            number /= 10;

            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) {
            return current;
        }
        return numNames[number] + " Hundred" + current;
    }

    public static String convert(int number) {

        if (number == 0) {
            return "ero";
        }

        String prefix = "";

        String current = "";
        int place = 0;

        if (number >= 1 && number < 2000) {
            do {
                int n = number % 100;
                if (n != 0) {
                    String s = convertLessThanOneThousand(n);
                    current = s + current;
                }
                place++;
                number /= 100;
            } while (number > 0);
        } else {
            do {
                int n = number % 1000;
                if (n != 0) {
                    String s = convertLessThanOneThousand(n);
                    current = s + specialNames[place] + current;
                }
                place++;
                number /= 1000;
            } while (number > 0);
        }

        return (prefix + current).trim();
    }

    public static boolean validateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }


}
