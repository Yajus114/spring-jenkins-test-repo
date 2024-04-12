/**
 *
 */
package com.dawnbit.storage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import lombok.extern.slf4j.Slf4j;

/**
 * @author DB-0007
 * @description common format for return data in rest web service
 */
@Slf4j
@Component
public final class RestStorageServiceTemplateUtils {

    /**
     * @param object
     * @param response
     * @return response format map
     */

    /**
     * @description private constructor in utility class
     */
    private RestStorageServiceTemplateUtils() {
    }

    /**
     * @param object
     * @param response
     * @return
     */
    public static Map<String, Object> createdSuccessResponse(final Object object, final HttpServletResponse response) {
        response.setStatus(HttpStatus.CREATED.value());
        return customResponse(HttpStatus.CREATED.value(), true, "Record successfully created", object);
    }

    /**
     * @param object
     * @param response
     * @return response format map
     */
    public static Map<String, Object> updatedSuccessResponse(final Object object, final HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return customResponse(HttpStatus.OK.value(), true, "Record successfully updated", object);
    }

    /**
     * @param object
     * @param response
     * @return response format map
     */
    public static Map<String, Object> getRecordSuccessResponse(final Object object,
                                                               final HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return customResponse(HttpStatus.OK.value(), true, "Record successfully fetched", object);
    }

    /**
     * @param response
     * @return response format map
     */
    public static Map<String, Object> recordNotFoundErrorResponse(final HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return customResponse(HttpStatus.OK.value(), true, "Record not found", null);
    }

    /**
     * @param object
     * @param response
     * @return response format map
     */
    public static Map<String, Object> errorResponse(final Object object, final HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return customResponse(HttpStatus.BAD_REQUEST.value(), false, HttpStatus.BAD_REQUEST.getReasonPhrase(), object);
    }

    /**
     * @param errors
     * @param response
     * @return response format map
     */
    public static Map<String, Object> errorResponse(final Errors errors, final HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        final List<String> errorList = new ArrayList<>();
        for (final FieldError error : errors.getFieldErrors()) {
            if (log.isInfoEnabled()) {
                log.info(error.getDefaultMessage() + " code : " + error.getCode());
            }
            errorList.add(error.getField() + " " + error.getDefaultMessage());
        }
        return customResponse(HttpStatus.BAD_REQUEST.value(), false, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorList);
    }

    private static Map<String, Object> customResponse(final int status, final boolean isSuccess, final String message,
                                                      final Object object) {
        final Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("isSuccess", isSuccess);
        responseMap.put("message", message);
        if (isSuccess) {
            responseMap.put("data", object);
        } else {
            responseMap.put("error", object);
        }
        return responseMap;
    }

    /**
     * @param errors
     * @return true or false
     */
    public static List<String> errorAfterSkipNull(final Errors errors) {
        final List<String> errorList = new ArrayList<>();
        for (final FieldError error : errors.getFieldErrors()) {
            if (!error.getCode().equals("NotNull")) {
                errorList.add(error.getField() + " " + error.getDefaultMessage());
            }
        }
        return errorList;
    }

}
