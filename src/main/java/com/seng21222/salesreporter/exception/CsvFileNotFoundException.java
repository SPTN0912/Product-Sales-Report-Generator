package com.seng21222.salesreporter.exception;

/**
 * Thrown when the CSV file path supplied on the command line does not exist
 * or cannot be opened.
 */
public class CsvFileNotFoundException extends SalesReporterException {
    public CsvFileNotFoundException(String filePath) {
        super("CSV file not found: " + filePath);
    }
}
