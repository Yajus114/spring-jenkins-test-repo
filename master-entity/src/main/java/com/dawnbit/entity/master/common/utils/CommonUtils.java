package com.dawnbit.entity.master.common.utils;

//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author DB-0083
 */
@Slf4j
@Component
public final class CommonUtils {

    /**
     * @author DB-0083
     */
    private CommonUtils() {
        // private constructor to prevent instantiation
    }

    /**
     * method to get current logged in user
     *
     * @return current user
     */
    public static String getPrincipal() {
//		if (SecurityContextHolder.getContext() != null
//				&& SecurityContextHolder.getContext().getAuthentication() != null
//				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
//			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			if (principal instanceof UserDetails) {
//				return ((UserDetails) principal).getUsername();
//			}
//			return principal.toString();
//		}
//		return null;
        return "admin";
    }

}
