package com.seng21222.salesreporter.cli;

/**
 * Immutable bundle of the three inputs the tool needs, regardless of
 * whether they came from command-line arguments or from
 * {@link ConsoleInputPrompter}. Having one shared type means the rest of
 * SalesReporter.run() does not need to know or care which source supplied
 * the values.
 *
 * Module owner: Dulanjana (Member 3 - Console Interface, Exception Handling & Documentation)
 */
public final class CliArguments {

    private final String csvFilePath;
    private final String outputMethod;
    private final String outputFilePath;

    public CliArguments(String csvFilePath, String outputMethod, String outputFilePath) {
        this.csvFilePath = csvFilePath;
        this.outputMethod = outputMethod;
        this.outputFilePath = outputFilePath;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public String getOutputMethod() {
        return outputMethod;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }
}
