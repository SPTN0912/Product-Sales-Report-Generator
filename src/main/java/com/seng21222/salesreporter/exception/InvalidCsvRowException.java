package com.seng21222.salesreporter.exception;

/**
 * Thrown when a data row in the CSV file is missing columns or otherwise
 * cannot be parsed into a Product.
 */
public class InvalidCsvRowException extends SalesReporterException {
    public InvalidCsvRowException(int lineNumber, String reason) {
        super("Invalid data at line " + lineNumber + ": " + reason);
    }
}
