package com.dawnbit.common.exception;

import org.springframework.stereotype.Component;

/**
 * @description It is use for throw custom exceptions
 */
@Component
public class CustomException extends Exception {

    private static final long serialVersionUID = 6798031143363755641L;

    public CustomException() {

    }

    public CustomException(final String message) {
        super(message);
    }

    public CustomException(final String message, final Throwable cause) {
        super(message, cause);
    }

}