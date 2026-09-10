package com.seng21222.salesreporter.exception;

/**
 * Thrown when the <output-method> argument is neither "console" nor "file",
 * or when "file" is chosen but no output file path was supplied.
 */
public class InvalidOutputMethodException extends SalesReporterException {
    public InvalidOutputMethodException(String message) {
        super(message);
    }
}
