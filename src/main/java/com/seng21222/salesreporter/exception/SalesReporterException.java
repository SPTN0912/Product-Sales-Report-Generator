package com.seng21222.salesreporter.exception;

/**
 * Base checked exception for every application-specific error in this
 * tool. Having one common parent lets the entry point (SalesReporter.main)
 * catch a single type and print a clean, user-facing error message instead
 * of leaking a stack trace to the console.
 */
public class SalesReporterException extends Exception {
    public SalesReporterException(String message) {
        super(message);
    }

    public SalesReporterException(String message, Throwable cause) {
        super(message, cause);
    }
}
