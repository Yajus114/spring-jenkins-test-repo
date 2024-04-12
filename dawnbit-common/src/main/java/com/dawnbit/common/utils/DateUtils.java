/*
 *
 */
package com.dawnbit.common.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.dawnbit.common.utils.DateUtils.DateRange.TextRange;

public class DateUtils {
    public final static String PATTERN_DDMMYYYY = "ddMMyyyy";
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);
    private final static TimeZone IST = TimeZone.getTimeZone("IST");
    public static TimeZone DEFAULT_TZ = IST;

    public static Date stringToDate(final String date, final String pattern) {
        final DateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (final ParseException e) {
            LOG.debug(e.getMessage());
        }
        return null;
    }

    public static String dateToString(final Date date, final String pattern) {
        if ((date != null) && StringUtils.hasText(pattern)) {
            final DateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        } else {
            return null;
        }
    }

    public static String formatDateAsUTC(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a zzz");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    public static String formatDateAsPST(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy | hh:mm a zzz");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        return sdf.format(date);
    }

    public static String formatDateAsIST(final Date date, final String pattern) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        return sdf.format(date);
    }

    public static String formatOnlyTimeAsUTC(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a zzz");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    public static String formatOnlyTime(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    public static String formatOnlyTimeAsPST(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a zzz");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        return sdf.format(date);
    }

    public static String formatOnlyDateAsPST(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy zzz");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        return sdf.format(date);
    }

    public static String formatOnlyDateAsPSTWithoutTimeZone(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        return sdf.format(date);
    }

    /**
     * @param date
     * @return Date
     * @author DB-0051
     * @description get start of the day with 0 hours, 0 minutes ,0 seconds and 0
     * milliseconds
     */
    public static Date getStartOfDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getStartOfDayInUTC(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        System.out.println("hour**" + hour);
        System.out.println("minute**" + minute);
        System.out.println("houu+minute**" + ((hour * 100) + minute));
        if (((hour * 100) + minute) < 1830) {
            calendar.add(Calendar.DATE, -1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return
     * @author Dawnbit-0046
     * @description get minutes from date
     */
    public static int getMinutesFromDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * @param date
     * @return
     * @author Dawnbit-0046
     * @description get hours from date
     */
    public static int getHoursFromDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @param date
     * @return Date
     * @author DB-0051
     * @description get start of the day with 0 hours, 0 minutes ,0 seconds and 0
     * milliseconds
     */
    public static Date getStartOfNextDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return Date
     * @author DB-0051
     * @description get start of the day with 23 hours, 59 minutes ,59 seconds and
     * 999 milliseconds
     */
    public static Date getEndOfDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static boolean between(final Date dateTime, final DateRange dateRange) {
        final Calendar givenTime = Calendar.getInstance();
        givenTime.setTime(dateTime);
        final Calendar startTime = Calendar.getInstance();
        startTime.setTime(dateRange.getStart());
        if (givenTime.after(startTime)) {
            final Calendar endTime = Calendar.getInstance();
            endTime.setTime(dateRange.getEnd());
            return givenTime.before(endTime);
        }
        return false;
    }

    public static boolean isPastTime(final Date input) {
        final Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(getCurrentTime());
        final Calendar inputTime = Calendar.getInstance();
        inputTime.setTime(input);
        return inputTime.before(currentTime);
    }

    public static long getTimeInMinutesWithDate(final Date dateTime) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / (1000 * 60);
    }

    public static boolean isFutureTime(final Date input) {
        final Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(getCurrentTime());
        final Calendar inputTime = Calendar.getInstance();
        inputTime.setTime(input);
        return inputTime.after(currentTime);
    }

    public static Date clearTime(final Date dateTime) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date clearDate(final Date dateTime) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.clear(Calendar.YEAR);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getCurrentTime() {
        final TimeZone timeZone = TimeZone.getTimeZone("UTC");
        final Calendar cal = Calendar.getInstance();
        cal.setTimeZone(timeZone);
        return cal.getTime();
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @author DB-0046
     * @description takes first date, extracts hours and minutes and add into the
     * second date.
     */
    public static Date getHoursAndMinutes(final Date date1, final Date date2) {
        final Calendar calendar = Calendar.getInstance();
        if ((date1 != null) && (date2 != null)) {
            calendar.setTime(date1);
            final int hours = calendar.get(Calendar.HOUR_OF_DAY);
            final int minutes = calendar.get(Calendar.MINUTE);
            final Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);
            calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH),
                    calendar2.get(Calendar.DAY_OF_MONTH), hours, minutes);
            return calendar2.getTime();
        }
        calendar.set(0, 0, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return
     * @author DB-0051
     */
    public static Date getDateForTime(final Date date) {
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            final Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar2.get(Calendar.SECOND));
            return calendar.getTime();
        }
        return date;
    }

    /**
     * @param date
     * @return
     * @author DB-0046
     * @description get Time in minutes for a particular date.
     */
    public static int getTimeInMinutesFromDate(final Date date) {
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);
        }
        return 0;
    }

    public static Date getSystemTime() {
        final Calendar now = Calendar.getInstance();
        return now.getTime();
    }

    public static Date getCurrentDate() {
        final Calendar now = Calendar.getInstance();
        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    public static boolean isSameDay(final Date d1, final Date d2) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(d1, d2);
    }

    /**
     * Usage Example : For date 5 Feb 2011 enter year = 2011, month = 2, and day = 5
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date createDate(final int year, final int month, final int day) {
        final Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day, 0, 0, 0);
        return date.getTime();
    }

    public static Date addToDate(final Date date, final int type, final int noOfUnits) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, noOfUnits);
        return calendar.getTime();
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     * @author DB-0051
     * @description This method returns no. of days between two dates.
     * @info The startDate is Inclusive and endDate is Exclusive in the calculation
     * of noOfDaysBetween
     */
    public static long getDaysBetweenTwoDates(final Date startDate, final Date endDate) {
        final Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        final Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        System.out.println("start date***" + startCal.getTime());
        System.out.println("end date***" + endCal.getTime());
        return ChronoUnit.DAYS.between(startCal.toInstant(), endCal.toInstant());
    }

    /**
     * Limit a date's resolution. For example, the date
     * <code>2004-09-21 13:50:11</code> will be changed to
     * <code>2004-09-01 00:00:00</code> when using <code>Resolution.MONTH</code>.
     *
     * @param resolution The desired resolution of the date to be returned
     * @return the date with all values more precise than <code>resolution</code>
     * set to 0 or 1
     */
    public static Date round(final Date date, final Resolution resolution) {
        return new Date(round(date.getTime(), resolution));
    }

    public static long round(final long time, final Resolution resolution) {
        final Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(time));

        if (resolution == Resolution.YEAR) {
            cal.set(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (resolution == Resolution.MONTH) {
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (resolution == Resolution.WEEK) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (resolution == Resolution.DAY) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (resolution == Resolution.HOUR) {
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (resolution == Resolution.MINUTE) {
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (resolution == Resolution.SECOND) {
            cal.set(Calendar.MILLISECOND, 0);
        } else if (resolution == Resolution.MILLISECOND) {
            // don't cut off anything
        } else {
            throw new IllegalArgumentException("unknown resolution " + resolution);
        }
        return cal.getTime().getTime();
    }

    public static DateRange getFutureInterval(final Date startTime, final Interval interval) {
        final Date endTime = addToDate(startTime, Calendar.MINUTE, interval.toMinutes());
        return new DateRange(startTime, endTime);
    }

    public static DateRange getPastInterval(final Date endTime, final Interval interval) {
        final Date startTime = addToDate(endTime, Calendar.MINUTE, -interval.toMinutes());
        return new DateRange(startTime, endTime);
    }

    public static DateRange getDayRange(final Date anytime) {
        final Date startTime = round(anytime, Resolution.DAY);
        return new DateRange(startTime, DateUtils.addToDate(startTime, Calendar.DATE, 1));
    }

    public static DateRange getLastDayRange() {
        return getDayRange(DateUtils.addToDate(DateUtils.getCurrentTime(), Calendar.DATE, -1));
    }

    public static DateRange getDaysRange(final Date anytime, final int days) {
        final Date startTime = round(anytime, Resolution.DAY);
        return new DateRange(startTime, DateUtils.addToDate(startTime, Calendar.DATE, days));
    }

    public static DateRange getLastDaysRange(final int pastDays) {
        return getDaysRange(DateUtils.addToDate(DateUtils.getCurrentTime(), Calendar.DATE, -pastDays), pastDays);
    }

    /**
     * This method can find a time embedded in a string in the following formats :
     * hhmm, hh:mm, h:m, h:mm, hh:m (1 space after/before : is also accepted) This
     * method runs faster than the parse() method of Java
     *
     * @param token
     * @return the date object
     */
    public static Date parseTime(final String token) {
        if ((token == null) || "".equals(token)) {
            return null;
        }

        final Calendar cal = new GregorianCalendar();
        cal.clear();

        final char[] ctoken = token.toCharArray();
        final StringBuilder hours = new StringBuilder(2);
        final StringBuilder mins = new StringBuilder(2);

        if (token.indexOf(":") < 0) {
            for (int i = 0; i < ctoken.length; i++) {
                if (Character.isDigit(ctoken[i]) && ((i + 1) < ctoken.length) && Character.isDigit(ctoken[i + 1])) {
                    if (((i + 2) < ctoken.length) && Character.isDigit(ctoken[i + 2]) && ((i + 3) < ctoken.length)
                            && Character.isDigit(ctoken[i + 3])) {
                        hours.append(ctoken[i]).append(ctoken[i + 1]);
                        mins.append(ctoken[i + 2]).append(ctoken[i + 3]);
                    }
                }
            }
        } else {
            for (int i = 0; i < ctoken.length; i++) {
                if (ctoken[i] == ':') {
                    if (((i - 1) >= 0) && Character.isDigit(ctoken[i - 1])) {
                        if (((i - 2) >= 0) && Character.isDigit(ctoken[i - 2])) {
                            hours.append(ctoken[i - 2]).append(ctoken[i - 1]);
                        } else {
                            hours.append(ctoken[i - 1]);
                        }
                    } else {
                        if (((i - 2) >= 0) && Character.isDigit(ctoken[i - 2])) {
                            if (((i - 3) >= 0) && Character.isDigit(ctoken[i - 3])) {
                                hours.append(ctoken[i - 3]).append(ctoken[i - 2]);
                            } else {
                                hours.append(ctoken[i - 2]);
                            }
                        }
                    }

                    if (((i + 1) < ctoken.length) && Character.isDigit(ctoken[i + 1])) {
                        if (((i + 2) < ctoken.length) && Character.isDigit(ctoken[i + 2])) {
                            mins.append(ctoken[i + 1]).append(ctoken[i + 2]);
                        } else {
                            mins.append(ctoken[i + 1]);
                        }
                    } else {
                        if (((i + 2) < ctoken.length) && Character.isDigit(ctoken[i + 2])) {
                            if (((i + 3) < ctoken.length) && Character.isDigit(ctoken[i + 3])) {
                                mins.append(ctoken[i + 2]).append(ctoken[i + 3]);
                            } else {
                                mins.append(ctoken[i + 2]);
                            }
                        }
                    }
                    break;
                }
            }
        }
        try {
            int hrs = Integer.parseInt(hours.toString());
            final int minutes = Integer.parseInt(mins.toString());
            if ((token.contains("pm") || token.contains("PM")) && (hrs != 12)) {
                hrs += 12;
                hrs %= 24;
            }
            if ((token.contains("am") || token.contains("AM")) && (hrs == 12)) {
                hrs = 0;
            }

            cal.set(Calendar.HOUR_OF_DAY, hrs);
            cal.set(Calendar.MINUTE, minutes);
        } catch (final NumberFormatException e) {
            LOG.debug("Could not parse " + hours + ":" + mins + " as time. Initial token was " + token);
            return null;
        }

        return cal.getTime();
    }

    public static int diff(final Date date1, final Date date2, final Resolution resolution) {
        final long diff = Math.abs(date1.getTime() - date2.getTime());
        return Math.round(diff / resolution.milliseconds());
    }

    /**
     * @return
     */
    public static Date getCurrentDayTime() {
        final Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.MONTH);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.clear(Calendar.YEAR);
        return cal.getTime();
    }

    public static Date getWeekPriorDate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        return cal.getTime();
    }

    public static Date getWeekPriorDateWithPST() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date nextDayPST(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date incrementDateByOneDay(final Date date) {
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return calendar.getTime();
        }
        return date;
    }

    public static Date decrementDateByOneDay(final Date date) {
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            return calendar.getTime();
        }
        return date;
    }

    public static Date getOneDayPriorDateWithPST() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @param date
     * @param simpleDateformat
     * @return
     * @author DB-0046
     * @description The day of the week spelled out completely
     */
    public static String getDayOfweekFromDate(final Date date) {
        return new SimpleDateFormat("EEEE").format(date);
    }

    /**
     * @param date
     * @return Date
     * @author DB-0046
     * @description get start of next day with 0 hours, 0 minutes ,0 seconds and 0
     * milliseconds
     */
    public static Date getNextDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        calendar.set(0, 0, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return Date
     * @description get start of previous day with 0 hours, 0 minutes ,0 seconds and 0
     * milliseconds
     */
    public static Date getStartPreviosDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        calendar.set(0, 0, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return Date
     * @description get end of previous day with 0 hours, 0 minutes ,0 seconds and 0
     * milliseconds
     */
    public static Date getEndPreviosDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        return calendar.getTime();
    }

    /**
     * Get previous date with no change in time
     *
     * @param date
     * @return
     */
    public static Date getPreviosDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            return calendar.getTime();
        }
        return calendar.getTime();
    }

    public static void main(final String[] args) {
        System.out.println("Today:" + new DateRange(TextRange.TODAY));
        System.out.println("Yesterday:" + new DateRange(TextRange.YESTERDAY));
        System.out.println("Last Week:" + new DateRange(TextRange.LAST_WEEK));
        System.out.println("Last Month:" + new DateRange(TextRange.LAST_MONTH));
        System.out.println("This Month:" + new DateRange(TextRange.THIS_MONTH));
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     * @author DB-0070
     * @Description (startDate is greater than endDate)
     */
    public static long getDaysDifferenceBetweenTwoDates(final Date startDate, final Date endDate) {
        final Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        final Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        final Date start = DateUtils.clearTime(startDate);
        final Date end = DateUtils.clearTime(endDate);

        final long diff = start.getTime() - end.getTime();
        final long noOfDaysInMilli = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return TimeUnit.DAYS.toDays(noOfDaysInMilli);
    }

    /**
     * @param date
     * @param amount
     * @return
     * @author DB-0070
     */
    public static Date addDays(final Date date, final int amount) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, amount);

        return c.getTime();
    }

    public static Date decrementDateByNdays(final Date date, final int n) {
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, -n);
            return calendar.getTime();
        }
        return date;
    }

    /**
     * @param date
     * @param timeZone
     * @return
     * @author Dawnbit-0046
     */
    public static Date getTimeByMilliseconds(final long timeInMilliseconds) {

        final TimeZone timeZone = TimeZone.getTimeZone("PST");
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliseconds);
        calendar.setTimeZone(timeZone);
        return calendar.getTime();
    }

    public static Date incrementDateByNDay(final Date date, final int n) {
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, n);
            return calendar.getTime();
        }
        return date;
    }

    public static Date combineDateAndTime(final Date date, final String time) {
        if (time != null) {
            final String[] array = time.split(":");
            final int hour = array[0] != null ? Integer.valueOf(array[0]) : 0;
            final int minute = array[0] != null ? Integer.valueOf(array[1]) : 0;
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            return calendar.getTime();
        }
        return date;
    }

    /**
     * @param years
     * @return
     * @author DB-0070
     */
    public static Date getYearsPriorDate(final int years) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -years);
        return cal.getTime();
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @author DB-0051
     * @description returns Date of a particular time zone.
     */
    public static Date convertUserDateToUTC(final Date date, final int offSet) {
        if (date != null) {
            final Calendar c = Calendar.getInstance();
            c.setTimeInMillis(date.getTime() - offSet);
            return c.getTime();
        }
        return date;
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @author DB-0051
     * @description returns Date of a particular time zone.
     */
    public static Date convertUTCToUserDate(final Date date, final int offSet) {
        if (date != null) {
            final Calendar c = Calendar.getInstance();
            c.setTimeInMillis(date.getTime() + offSet);
            return c.getTime();
        }
        return date;
    }

    /**
     * @param date
     * @return
     * @author DB-0070
     * @description return start date of next month.
     */
    public static String getNextMonth(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        if (date != null) {
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            return sdf.format(calendar.getTime());
        }
        calendar.set(0, 0, 0, 0, 0);
        return sdf.format(calendar.getTime());
    }

    public static String geDateInFormatYYYYMMdd(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        if (date != null) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return sdf.format(calendar.getTime());
        }
        calendar.set(0, 0, 0, 0, 0);
        return sdf.format(calendar.getTime());
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param timeZone
     * @return
     * @author DB-0070 Usage Example : For date 5 Feb 2011 enter year = 2011, month
     * = 2, and day = 5 timeZone ex. UTC
     */
    public static Date createDateWithOffset(final int year, final int month, final int day, final String timeZone) {
        final Calendar date = Calendar.getInstance();
        final TimeZone timeZoneValue = TimeZone.getTimeZone(timeZone);
        date.set(day, month - 1, year);
        date.setTimeZone(timeZoneValue);
        return date.getTime();
    }

    /**
     * @return current dateTime in milliseconds
     * @author DB-0080
     */
    public static long getCurrentDateTimeInMilliseconds() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTimeInMillis();
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param timeZone
     * @return
     * @author DB-0070 Usage Example : For date 5 Feb 2011 enter year = 2011, month
     * = 2, and day = 5 timeZone ex. UTC and Set hour minute and second to 0
     */
    public static Date createUTCDate(final int day, final int month, final int year, final String timeZone) {
        final Calendar date = Calendar.getInstance();
        //		final TimeZone timeZoneValue = TimeZone.getTimeZone(timeZone);
        date.set(year, month - 1, day, 0, 0, 0);
        //		date.setTimeZone(timeZoneValue);
        return date.getTime();
    }

    /**
     * @return current dateTime in milliseconds
     * @author DB-0080
     */
    public static long getMilliseconds(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static boolean isValidDate(final String dateStr, final String pattern) {
        //		DateFormat sdf = new SimpleDateFormat(pattern);
        //		sdf.setLenient(false);
        //		try {
        //			sdf.parse(dateStr);
        //		} catch (ParseException e) {
        //			return false;
        //		}
        try {
            LocalDate.parse(dateStr, // "40:03:2010" is bad input, "27:03:2010" is good
                    // input.
                    DateTimeFormatter.ofPattern(pattern));
        } catch (final DateTimeParseException e) {
            return false; // Invalid input detected.
        }
        return true;
    }

    public static Date setToDate(final Date date, final int type, final int noOfUnits) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(type, noOfUnits);
        return calendar.getTime();
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     * @author DB-0080
     */
    public static String getYearAndMonthDifference(final Date startDate, final Date endDate) {
        final StringBuilder sb = new StringBuilder();
        final Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.setTime(startDate);
        final Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.setTime(endDate);
        int yearDiff = endDateCalendar.get(Calendar.YEAR) - startDateCalendar.get(Calendar.YEAR);
        if (startDateCalendar.get(Calendar.DAY_OF_YEAR) > endDateCalendar.get(Calendar.DAY_OF_YEAR)) {
            yearDiff--;
            sb.append(yearDiff + " year(s) ");
            final int monthDiff = 12 - (startDateCalendar.get(Calendar.MONTH) - endDateCalendar.get(Calendar.MONTH));
            sb.append(monthDiff + " month(s)");
        } else {
            sb.append(yearDiff + " year(s) ");
            final int monthDiff = endDateCalendar.get(Calendar.MONTH) - startDateCalendar.get(Calendar.MONTH);
            sb.append(monthDiff + " month(s)");
        }
        return sb.toString();
    }

    /**
     * get day number of date in String
     *
     * @param date
     * @return
     */
    public static String getDayOfDateInString(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public static Time getCurrentTimeInIst(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 5);
        cal.add(Calendar.MINUTE, 30);
        cal.add(Calendar.SECOND, 0);
        date = cal.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Time time = new Time(new Date().getTime());
        try {
            time = new Time(sdf.parse(sdf.format(date)).getTime());
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * Usage Example : For date 1 Mar 2011 enter year = 2011, month = 2
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date createStartDateOfMonth(final int year, final int month) {
        final Calendar date = Calendar.getInstance();
        date.set(year, month, 10, 0, 0, 0);
        date.set(Calendar.DAY_OF_MONTH, date.getActualMinimum(Calendar.DAY_OF_MONTH));
        return date.getTime();
    }

    public static Date createEndDatePreviousMonth(final int year, final int month) {
        final Calendar date = Calendar.getInstance();
        date.set(year, month, 10, 0, 0, 0);
        date.set(Calendar.DAY_OF_MONTH, date.getActualMinimum(Calendar.DAY_OF_MONTH));
        date.add(Calendar.DAY_OF_MONTH, -1);
        return date.getTime();
    }

    /**
     * Usage Example : For date 31 Mar 2011 enter year = 2011, month = 2
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date createEndDateOfMonth(final int year, final int month) {
        final Calendar date = Calendar.getInstance();
        date.set(year, month, 10, 0, 0, 0);
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        return date.getTime();
    }

    public static int getActualMaximum(final int year, final int month) {
        final Calendar date = Calendar.getInstance();
        date.set(year, month, 0, 0, 0, 0);
        return date.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthInDate(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getMonthInDateIST(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("IST"));
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static long getDaysBetweenTwoDatesInclusiveStartDate(final Date startDate, final Date endDate) {
        final Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        final Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        startCal.add(Calendar.DAY_OF_MONTH, -1);

        final Date start = DateUtils.clearTime(startDate);
        final Date end = DateUtils.clearTime(endDate);

        final long diff = start.getTime() - end.getTime();
        final long noOfDaysInMilli = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return TimeUnit.DAYS.toDays(noOfDaysInMilli);
    }

    public static Date convertUserDateToUTCWithZeroMilliSec(final Date date, final int offSet) {
        if (date != null) {
            final Calendar c = Calendar.getInstance();
            c.setTimeInMillis(date.getTime() - offSet);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }
        return date;
    }

    /**
     * Specifies the time granularity.
     */
    public enum Resolution {
        YEAR(365 * 30 * 7 * 24 * 60 * 60 * 1000), MONTH(30 * 7 * 24 * 60 * 60 * 1000), WEEK(7 * 24 * 60 * 60 * 1000),
        DAY(24 * 60 * 60 * 1000), HOUR(60 * 60 * 1000), MINUTE(60 * 1000), SECOND(1000), MILLISECOND(1);

        private final long milliseconds;

        private Resolution(final long milliseconds) {
            this.milliseconds = milliseconds;
        }

        public long milliseconds() {
            return this.milliseconds;
        }
    }

    public static final class Interval {
        private final TimeUnit timeUnit;
        private final long period;

        public Interval(final TimeUnit timeUnit, final long period) {
            this.timeUnit = timeUnit;
            this.period = period;
        }

        public TimeUnit getTimeUnit() {
            return this.timeUnit;
        }

        public long getPeriod() {
            return this.period;
        }

        public int toMinutes() {
            return (int) this.timeUnit.toMinutes(this.period);
        }

    }

    public static class DateRange {

        private Date start;
        private Date end;
        private TextRange textRange;

        public DateRange() {
        }

        public DateRange(final Date start, final Date end) {
            this.start = start;
            this.end = end;
        }

        public DateRange(final TextRange textRange) {
            this.setTextRange(textRange);
        }

        public Date getStart() {
            if (this.textRange != null) {
                this.setTextRange(this.textRange);
            }
            return this.start;
        }

        public void setStart(final Date start) {
            this.start = start;
        }

        public Date getEnd() {
            if (this.textRange != null) {
                this.setTextRange(this.textRange);
            }
            return this.end;
        }

        public void setEnd(final Date end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return this.getStart() + " - " + this.getEnd() + "-" + (this.textRange == null ? "" : this.textRange);
        }

        public TextRange getTextRange() {
            return this.textRange;
        }

        public void setTextRange(final TextRange textRange) {
            if (textRange != null) {
                switch (textRange) {
                    case TODAY:
                        this.start = round(getCurrentTime(), Resolution.DAY);
                        this.end = addToDate(this.start, Calendar.DATE, 1);
                        break;
                    case YESTERDAY:
                        this.end = round(getCurrentTime(), Resolution.DAY);
                        this.start = addToDate(this.end, Calendar.DATE, -1);
                        break;
                    case LAST_WEEK:
                        this.end = round(getCurrentTime(), Resolution.WEEK);
                        this.start = addToDate(this.end, Calendar.DATE, -7);
                        break;
                    case LAST_MONTH:
                        this.end = round(getCurrentTime(), Resolution.MONTH);
                        this.start = addToDate(this.end, Calendar.MONTH, -1);
                        break;
                    case THIS_MONTH:
                        this.start = round(getCurrentTime(), Resolution.MONTH);
                        this.end = addToDate(this.start, Calendar.MONTH, 1);
                        break;
                    case LAST_7_DAYS:
                        this.end = round(getCurrentTime(), Resolution.DAY);
                        this.start = addToDate(this.end, Calendar.DATE, -7);
                        break;
                    case LAST_30_DAYS:
                        this.end = round(getCurrentTime(), Resolution.DAY);
                        this.start = addToDate(this.end, Calendar.DATE, -30);
                        break;
                    case LAST_60_DAYS:
                        this.end = round(getCurrentTime(), Resolution.DAY);
                        this.start = addToDate(this.end, Calendar.DATE, -60);
                        break;
                    case LAST_90_DAYS:
                        this.end = round(getCurrentTime(), Resolution.DAY);
                        this.start = addToDate(this.end, Calendar.DATE, -90);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid value for textRange");
                }
            }
            this.textRange = textRange;
        }

        public enum TextRange {
            TODAY, YESTERDAY, LAST_WEEK, LAST_MONTH, THIS_MONTH, LAST_7_DAYS, LAST_30_DAYS, LAST_60_DAYS, LAST_90_DAYS
        }
    }
}
