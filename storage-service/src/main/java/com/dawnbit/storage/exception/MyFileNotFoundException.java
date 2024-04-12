package com.dawnbit.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A custom MyFileNotFoundException
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1559573751368589132L;

    /**
     * @param message
     */
    public MyFileNotFoundException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public MyFileNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
