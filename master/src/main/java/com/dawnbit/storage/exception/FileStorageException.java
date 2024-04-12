package com.dawnbit.storage.exception;

/**
 * A custom FileStorageException
 */
public class FileStorageException extends RuntimeException {

    private static final long serialVersionUID = 1240141559060545750L;

    /**
     * @param message
     */
    public FileStorageException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public FileStorageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
