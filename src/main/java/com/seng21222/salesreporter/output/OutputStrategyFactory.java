package com.seng21222.salesreporter.output;

import com.seng21222.salesreporter.exception.InvalidOutputMethodException;


public final class OutputStrategyFactory {

    private OutputStrategyFactory() {
        // utility class - not meant to be instantiated
    }

    public static ReportOutputStrategy create(String outputMethod, String outputFilePath)
            throws InvalidOutputMethodException {

        if (outputMethod == null) {
            throw new InvalidOutputMethodException("Output method must not be empty.");
        }

        switch (outputMethod.trim().toLowerCase()) {
            case "console":
                return new ConsoleOutputStrategy();
            case "file":
                if (outputFilePath == null || outputFilePath.isBlank()) {
                    throw new InvalidOutputMethodException(
                            "Output method 'file' requires an [output-file-path] argument.");
                }
                return new FileOutputStrategy(outputFilePath);
            default:
                throw new InvalidOutputMethodException(
                        "Invalid output method: '" + outputMethod + "'. Expected 'console' or 'file'.");
        }
    }
}
