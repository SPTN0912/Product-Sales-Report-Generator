package com.seng21222.salesreporter.exception;

/**
 * Thrown when the generated report cannot be delivered by the chosen
 * output strategy (e.g. the output file path is not writable).
 */
public class ReportOutputException extends SalesReporterException {
    public ReportOutputException(String message, Throwable cause) {
        super(message, cause);
    }
}
