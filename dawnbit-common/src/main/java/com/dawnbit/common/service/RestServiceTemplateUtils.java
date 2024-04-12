package com.dawnbit.common.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import lombok.extern.slf4j.Slf4j;

/**
 * @author DB-CPU009
 * @description common format for return data in rest web service
 */
@Component
@Slf4j
public final class RestServiceTemplateUtils {

    private static final List<String> FIELD_ERROR_CODE_LIST = Arrays.asList("notnull", "size");

    /**
     * @author DB-0003
     */
    private RestServiceTemplateUtils() {
        // private constructor to prevent instantiation
    }

    /**
     * @param object
     * @param response
     * @return response format map
     */
    public static Map<String, Object> createdSuccessResponse(final Object object, final HttpServletResponse response) {
        response.setStatus(HttpStatus.CREATED.value());
        return response(HttpStatus.CREATED.value(), true, "Record successfully created", object);
    }

    /**
     * @param object
     * @param response
     * @return response format map
     */
    public static Map<String, Object> updatedSuccessResponse(final Object object, final HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return response(HttpStatus.OK.value(), true, "Record successfully updated", object);
    }

    /**
     * @param object
     * @param response
     * @return response format map
     */
    public static Map<String, Object> getRecordSuccessResponse(final Object object,
                                                               final HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return response(HttpStatus.OK.value(), true, "Record successfully fetched", object);
    }

    /**
     * @param response
     * @return response format map
     */
    public static Map<String, Object> recordNotFoundErrorResponse(final HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return response(HttpStatus.OK.value(), true, "Record not found", null);
    }

    /**
     * @param object
     * @param response
     * @return response format map
     */
    public static Map<String, Object> errorResponse(final Object object, final HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return response(HttpStatus.BAD_REQUEST.value(), false, HttpStatus.BAD_REQUEST.getReasonPhrase(), object);
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
            errorList.add(error.getField() + " " + error.getDefaultMessage());
        }
        return response(HttpStatus.BAD_REQUEST.value(), false, HttpStatus.BAD_REQUEST.getReasonPhrase(), errorList);
    }

    private static Map<String, Object> response(final int status, final boolean isSuccess, final String message,
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
        if (log.isInfoEnabled()) {
            log.debug("Enter in error after null function");
            log.info(String.format("Object of errors :[%s] and size of FieldErrors : [%s]", errors,
                    errors.getFieldErrors()));
        }
        for (final FieldError error : errors.getFieldErrors()) {
            if (log.isInfoEnabled()) {
                log.info(String.format("Error Code :[%s], Object Name : [%s] and Error Codes : [%s]", error.getCode(),
                        error.getObjectName(), error.getCodes()));
            }
            if (FIELD_ERROR_CODE_LIST.indexOf(error.getCode().toLowerCase(Locale.US)) > -1) {
                errorList.add(error.getField() + " " + error.getDefaultMessage());
            }
        }
        return errorList;
    }

//    private RestServiceTemplate() {
//		throw new IllegalStateException("RestServiceTemplate class");
//	}

}
