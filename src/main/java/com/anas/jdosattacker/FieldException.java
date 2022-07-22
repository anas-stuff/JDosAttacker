package com.anas.jdosattacker;

/**
 * `FieldException` is a custom exception class that is thrown when a field value is wrong.
 */
public class FieldException extends Exception {
    public FieldException(final String message) {
        super(message);
    }
}
