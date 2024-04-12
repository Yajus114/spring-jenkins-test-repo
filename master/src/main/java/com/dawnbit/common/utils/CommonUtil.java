package com.dawnbit.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


/**
 * @author DB-0066
 */
@Slf4j
@Component
public final class CommonUtil {

    /**
     * @author DB-0079
     */
    private CommonUtil() {
        // private constructor to prevent instantiation
    }

    /**
     * ignore null properties
     *
     * @param source source
     * @return array of null property names
     */
    public static String[] getNullPropertyNames(final Object source) {
        final Set<String> emptyNames = getSetOfNullPropertyNames(source);
        final String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    /**
     * ignore null properties
     *
     * @param source                source
     * @param extraIgnoreProperties extra ignore properties
     * @return array of null and extra property names
     */
//    public static String[] getNullPropertyNames(final Object source, final List<String> extraIgnoreProperties) {
//        final Set<String> nullAndExtraFields = getSetOfNullPropertyNames(source);
//        nullAndExtraFields.addAll(extraIgnoreProperties);
//        final String[] result = new String[nullAndExtraFields.size()];
//        return nullAndExtraFields.toArray(result);
//    }

    /**
     * @param source source
     * @return array of null property names
     */
    private static Set<String> getSetOfNullPropertyNames(final Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        final java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        final Set<String> emptyNames = new HashSet<>();
        for (final java.beans.PropertyDescriptor pd : pds) {
            try {
                final Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue == null) {
                    emptyNames.add(pd.getName());
                }
            } catch (final Exception e) {
                if (log.isInfoEnabled()) {
                    log.info(e.getMessage());
                }
            }
        }
        return emptyNames;
    }

    /**
     * @param amount amount
     * @return double value
     */
//    public static double roundOf(final double amount) {
//        return Math.round(amount * 100.0) / 100.0;
//    }

    /**
     * @return logged in user name
     */
    public static String getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        return "";
    }

}
