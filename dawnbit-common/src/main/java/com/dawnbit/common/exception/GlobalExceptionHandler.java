/**
 *
 */
package com.dawnbit.common.exception;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author DB-0007
 * @description global exception handler
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    private static final String CONSTRAINT_VIOLATION_EXCEPTION = "Action can't be completed as this record already exists and getting used in application.";

    @ExceptionHandler(value = FileNotFoundException.class)
    public Map<String, Object> fileNotFoundException(final FileNotFoundException ex,
                                                     final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), ex.getMessage(),
                response);
    }

    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    public Map<String, Object> arrayIndexOutOfBoundsException(final ArrayIndexOutOfBoundsException ex,
                                                              final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    @ExceptionHandler(value = ClassCastException.class)
    public Map<String, Object> classCastException(final ClassCastException ex, final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Map<String, Object> illegalArgumentException(final IllegalArgumentException ex,
                                                        final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage(),
                response);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public Map<String, Object> nullPointerException(final NullPointerException ex, final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public Map<String, Object> numberFormatException(final NumberFormatException ex,
                                                     final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage(),
                response);
    }

    @ExceptionHandler(value = AssertionError.class)
    public Map<String, Object> assertionError(final AssertionError ex, final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    @ExceptionHandler(value = ExceptionInInitializerError.class)
    public Map<String, Object> exceptionInInitializerError(final ExceptionInInitializerError ex,
                                                           final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    @ExceptionHandler(value = StackOverflowError.class)
    public Map<String, Object> stackOverflowError(final StackOverflowError ex, final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    @ExceptionHandler(value = NoClassDefFoundError.class)
    public Map<String, Object> noClassDefFoundError(final NoClassDefFoundError ex, final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    /**
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(HttpStatusCodeException.class)
    public Map<String, Object> httpStatusCodeException(final HttpStatusCodeException ex,
                                                       final HttpServletResponse response) {
        log.error(ex.getLocalizedMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage(),
                response);
    }

    /**
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public Map<String, Object> httpStatusCodeException(final HttpClientErrorException ex,
                                                       final HttpServletResponse response) {
        log.error(ex.getLocalizedMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage(),
                response);
    }

    /**
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public Map<String, Object> httpStatusCodeException(final HttpServerErrorException ex,
                                                       final HttpServletResponse response) {
        log.error(ex.getLocalizedMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage(),
                response);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Map<String, Object> resourceNotFoundException(final RuntimeException ex,
                                                         final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Map<String, Object> resourceNotFoundException(final ConstraintViolationException ex,
                                                         final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public Map<String, Object> dataIntegrityViolationException(final DataIntegrityViolationException ex,
                                                               final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                CONSTRAINT_VIOLATION_EXCEPTION, response);
    }

    @ExceptionHandler(value = CustomException.class)
    public Map<String, Object> customException(final CustomException ex, final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage(),
                response);
    }

    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exception(final Exception ex, final HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        return this.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), response);
    }

    private Map<String, Object> setStatusAndMessage(final int status, final String error, final String message,
                                                    final HttpServletResponse response) {
        final Map<String, Object> reponseMap = new HashMap<>();
        response.setStatus(status);
        reponseMap.put("status", status);
        reponseMap.put("error", error);
        reponseMap.put("isSuccess", false);
        reponseMap.put("message", message);
        return reponseMap;
    }
}
