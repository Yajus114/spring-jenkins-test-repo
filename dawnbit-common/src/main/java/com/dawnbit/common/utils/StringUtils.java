package com.dawnbit.common.utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.text.StringEscapeUtils;

public class StringUtils {
    public static final String EMPTY_STRING = "";
    public static final char CHAR_NEW_LINE = '\n';
    private static final String GMAIL_EMAIL_SUFFIX = "@gmail.com";
    private static final int GMAIL_EMAIL_SUFFIX_LENGTH = GMAIL_EMAIL_SUFFIX.length();
    /**
     * Parse a number from a string. Finds the first recognizable base-10 number
     * (integer or floating point) in the string and returns it as a Number. Uses
     * American English conventions (i.e., '.' as decimal point and ',' as thousands
     * separator).
     *
     * @param string String to parse
     * @return first recognizable number
     * @exception NumberFormatException if no recognizable number is found
     */
    private static final int INT = 0;
    private static final int FRAC = 1;
    private static final int EXP = 2;

    public static String getRandom(final int length) {
        final String randomString = getRandom();
        return randomString.substring(randomString.length() - length);
    }

    public static String getRandomAlphaNumeric(final int length) {
        final String aplhaNumberic = getRandom().toLowerCase().replaceAll("[^\\da-z]", "");
        return aplhaNumberic.substring(aplhaNumberic.length() - length);
    }

    public static String getRandomAlphaNumeric() {
        return getRandom().toLowerCase().replaceAll("[^\\da-z]", "");
    }

    public static String getRandomNumeric(final int length) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append((int) (Math.random() * 10));
        }
        return builder.toString();
    }

    public static String getRandom() {
        return UUID.randomUUID().toString();
    }

    public static boolean isEmpty(final String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str.trim());
    }

    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    public static String getAccessorNameForField(final String name) {
        return new StringBuilder("get").append(name.substring(0, 1).toUpperCase()).append(name.substring(1)).toString();
    }

    public static String getModifierNameForField(final String name) {
        return new StringBuilder("set").append(name.substring(0, 1).toUpperCase()).append(name.substring(1)).toString();
    }

    public static String getNotNullValue(final String value) {
        return value != null ? value : "";
    }

    public static String join(final char sep, final Collection<String> strings) {
        if (strings == null || strings.size() == 0) {
            return EMPTY_STRING;
        }
        final StringBuilder builder = new StringBuilder();
        for (final String s : strings) {
            builder.append(s).append(sep);
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    public static String extractBetweenDelimiters(final String input, final String startDelimiter,
                                                  final String endDelimiter) {
        final int startIndex = input.indexOf(startDelimiter);
        if (startIndex != -1) {
            final int endIndex = input.indexOf(endDelimiter, startIndex + startDelimiter.length());
            if (endIndex != -1) {
                return input.substring(startIndex + startDelimiter.length(), endIndex);
            }
        }
        return null;
    }

    public static String normalizeEmail(final String email) {
        if (email.endsWith(GMAIL_EMAIL_SUFFIX)) {
            return email.substring(0, email.length() - GMAIL_EMAIL_SUFFIX_LENGTH).replaceAll("\\.", "")
                    + GMAIL_EMAIL_SUFFIX;
        } else {
            return email;
        }
    }

    public static String getEmailDomain(final String email) {
        return email.substring(email.lastIndexOf('@') + 1);
    }

    public static String getLocalPartFromEmail(final String emailAdd) {
        return emailAdd.substring(0, emailAdd.lastIndexOf('@'));
    }

    public static List<String> split(final String input, final String regex) {
        return Arrays.asList(input.split(regex));
    }

    public static List<String> split(final String input) {
        return split(input, ",");
    }

    public static String capitalizeString(final String string) {
        final char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add
                // other
                // chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static Double parsePrice(String number) {
        number = number.replaceAll("[^\\.\\d,-]", "");
        final DecimalFormat df = new DecimalFormat("#,##,###.##");
        Number n;
        try {
            n = df.parse(number);
        } catch (final ParseException e) {
            return 0.0;
        }
        return n.doubleValue();
    }

    public static boolean equalsAny(final String input, final String... strings) {
        for (final String string : strings) {
            if (input.equals(string)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsIngoreCaseAny(final String input, final String... strings) {
        for (final String string : strings) {
            if (input.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    public static void main(final String[] args) throws IOException {
        System.out.println(escapeSql(FileUtils.getFileAsString(args[0])));
    }

    public static Number parseNumber(final String s) throws NumberFormatException {
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int start = i;
                int end = ++i;
                int state = INT;

                if (start > 0 && s.charAt(start - 1) == '.') {
                    --start;
                    state = FRAC;
                }
                if (start > 0 && s.charAt(start - 1) == '-') {
                    --start;
                }

                foundEnd:
                while (i < s.length()) {
                    switch (s.charAt(i)) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            end = ++i;
                            break;
                        case '.':
                            if (state != INT) {
                                break foundEnd;
                            }
                            state = FRAC;
                            ++i;
                            break;
                        case ',': // ignore commas
                            ++i;
                            break;
                        case 'e':
                        case 'E':
                            state = EXP;
                            ++i;
                            if (i < s.length() && ((c = s.charAt(i)) == '+' || c == '-')) {
                                ++i;
                            }
                            break;
                        default:
                            break foundEnd;
                    }
                }

                String num = s.substring(start, end);
                num = replace(num, ",", "");
                try {
                    if (state == INT) {
                        return new Integer(num);
                    } else {
                        return new Float(num);
                    }
                } catch (final NumberFormatException e) {
                    throw new RuntimeException("internal error: " + e);
                }
            }
        }
        throw new NumberFormatException(s);
    }

    /**
     * Replace all occurences of a string.
     *
     * @param subject     String in which to search
     * @param original    String to search for in subject
     * @param replacement String to substitute
     * @return subject with all occurences of original replaced by replacement
     */
    public static String replace(final String subject, final String original, final String replacement) {
        final StringBuilder output = new StringBuilder();

        int p = 0;
        int i;
        while ((i = subject.indexOf(original, p)) != -1) {
            output.append(subject.substring(p, i));
            output.append(replacement);
            p = i + original.length();
        }
        if (p < subject.length()) {
            output.append(subject.substring(p));
        }
        return output.toString();
    }

    /**
     * Escapes metacharacters in a string.
     *
     * @param subject    String in which metacharacters are to be escaped
     * @param escapeChar the escape character (e.g., \)
     * @param metachars  the metacharacters that should be escaped
     * @return subject with escapeChar inserted before every character found in
     * metachars
     */
    public static String escape(final String subject, final char escapeChar, final String metachars) {
        return escape(subject, metachars, escapeChar, metachars);
    }

    /**
     * Escapes characters in a string.
     *
     * @param subject    String in which metacharacters are to be escaped
     * @param chars      Characters that need to be escaped (e.g. "\b\t\r\n\\")
     * @param escapeChar the escape character (e.g., '\\')
     * @param metachars  escape code letters corresponding to each letter in chars
     *                   (e.g. "btrn\\") <B>Must have metachars.length () ==
     *                   chars.length().</B>
     * @return subject where every occurence of c in chars is replaced by escapeChar
     * followed the character corresponding to c in metachars.
     */
    public static String escape(final String subject, final String chars, final char escapeChar,
                                final String metachars) {
        final StringBuffer output = new StringBuffer();

        int p = 0;
        int i;
        while ((i = indexOfAnyChar(subject, chars, p)) != -1) {
            output.append(subject.substring(p, i));

            final char c = subject.charAt(i); // character that needs escaping
            final int k = chars.indexOf(c);
            final char metac = metachars.charAt(k); // its corresponding metachar
            output.append(escapeChar);
            output.append(metac);

            p = i + 1;
        }
        if (p < subject.length()) {
            output.append(subject.substring(p));
        }
        return output.toString();
    }

    /**
     * Translate escape sequences (e.g. \r, \n) to characters.
     *
     * @param subject                 String in which metacharacters are to be
     *                                escaped
     * @param escapeChar              the escape character (e.g., \)
     * @param metachars               letters representing escape codes (typically
     *                                "btrn\\")
     * @param chars                   characters corresponding to metachars
     *                                (typically "\b\t\r\n\\"). <B>Must have
     *                                chars.length () == metachars.length().</B>
     * @param keepUntranslatedEscapes Controls behavior on unknown escape sequences
     *                                (see below).
     * @return subject where every escapeChar followed by c in metachars is replaced
     * by the character corresponding to c in chars. If an escape sequence
     * is untranslatable (because escapeChar is followed by some character c
     * not in metachars), then the escapeChar is kept if
     * keepUntranslatedEscapes is true, otherwise the escapeChar is deleted.
     * (The character c is always kept.)
     */
    public static String unescape(final String subject, final char escapeChar, final String metachars,
                                  final String chars, final boolean keepUntranslatedEscapes) {
        final StringBuffer output = new StringBuffer();

        int p = 0;
        int i;
        final int len = subject.length();
        while ((i = subject.indexOf(escapeChar, p)) != -1) {
            output.append(subject.substring(p, i));
            if (i + 1 == len) {
                break;
            }

            final char metac = subject.charAt(i + 1); // metachar to replace
            final int k = metachars.indexOf(metac);
            if (k == -1) {
                // untranslatable sequence
                if (keepUntranslatedEscapes) {
                    output.append(escapeChar);
                }
                output.append(metac);
            } else {
                output.append(chars.charAt(k)); // its corresponding true char
            }

            p = i + 2; // skip over both escapeChar & metac
        }

        if (p < len) {
            output.append(subject.substring(p));
        }
        return output.toString();
    }

    /**
     * Find first occurrence of any of a set of characters.
     *
     * @param subject String in which to search
     * @param chars   Characters to search for
     * @return index of first occurrence in subject of a character from chars, or -1
     * if no match.
     */
    public static int indexOfAnyChar(final String subject, final String chars) {
        return indexOfAnyChar(subject, chars, 0);
    }

    /**
     * Find first occurrence of any of a set of characters, starting at a specified
     * index.
     *
     * @param subject String in which to search
     * @param chars   Characters to search for
     * @param start   Starting offset to search from
     * @return index of first occurrence (after start) in subject of a character
     * from chars, or -1 if no match.
     */
    public static int indexOfAnyChar(final String subject, final String chars, final int start) {
        for (int i = start; i < subject.length(); ++i) {
            if (chars.indexOf(subject.charAt(i)) != -1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * removes all characters which are not letter or digit (removes whitespaces as
     * well!)
     *
     * @param input
     * @return string with all non-word chars removed
     */
    public static String removeNonWordChars(final String input) {
        final StringBuffer output = new StringBuffer();
        final char[] cinput = input.toCharArray();
        for (final char element : cinput) {
            if (Character.isLetterOrDigit(element)) {
                output.append(element);
            }
        }
        return output.toString();
    }

    /**
     * @param input
     * @return
     */
    public static boolean parseBoolean(String input) {
        input = input == null ? null : input.trim();
        return input != null
                && (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("1"));
    }

    public static String pad(String input, final int length, final char c) {
        input = getNotNullValue(input);
        if (input.length() >= length) {
            return input;
        } else {
            final StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length - input.length(); i++) {
                builder.append(c);
            }
            builder.append(input);
            return builder.toString();
        }
    }

    public static String escapeCsv(final String input) {
        if (input == null) {
            return EMPTY_STRING;
        }
        return StringEscapeUtils.escapeCsv(input);
    }

    public static String underscorify(final String input) {
        return input.replaceAll("[\\s\\W]", "_").replaceAll("[_]+", "_");
    }

    public static String escapeSql(final String input) {
        if (input == null) {
            return "NULL";
        } else {
            return new StringBuilder().append('\'')
                    .append(input.replaceAll("\\\\", "\\\\\\\\").replaceAll("\t", "\\\\t").replaceAll("\n", "\\\\n")
                            .replaceAll("\r", "\\\\r").replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\'"))
                    .append('\'').toString();
        }
    }

    public static String getPartialNumber(final String key, final String value) {
        String number;
        if (value == null) {
            number = EMPTY_STRING;
        } else {
            final String[] stringArr = value.split(key);
            number = key + stringArr[stringArr.length - 1];
        }
        return number;

    }

    public static String getDateTimeFromOrderNumber(final String value) {
        return value.substring(4, 12);
    }

    public static String convertIntegerSequenceToStringSequence(final String integerSequence) {
        final List<String> list = new ArrayList<>();
        final List<Integer> list2 = Stream.of(integerSequence.split(",")).map(String::trim).map(Integer::parseInt)
                .collect(Collectors.toList());
        Collections.sort(list2);
        final String s = StringUtils.join(',', list);
        return s != null && !s.equals("") ? " (" + s + ")" : s;
    }

    /**
     * remove all non-ascii characters, controls, non printable unicodes
     *
     * @param text
     * @return
     * @author VMR
     */
    public static String cleanTextContent(String text) {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        return text.trim();
    }

}
