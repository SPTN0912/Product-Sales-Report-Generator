package com.seng21222.salesreporter.cli;

import com.seng21222.salesreporter.exception.SalesReporterException;

import java.util.Scanner;

/**
 * Interactively asks the user for the three pieces of input the tool needs,
 * mirroring the same contract as the command-line arguments:
 *
 *   Input — Command-Line Arguments:
 *   java SalesReporter <csv-file-path> <output-method> [output-file-path]
 *     <csv-file-path>    : Path to the input CSV file
 *     <output-method>    : 'console' or 'file'
 *     [output-file-path] : Required only when output-method is 'file'
 *
 * This is used only when the program is launched with zero command-line
 * arguments, so the tool still fully satisfies the assignment's required
 * `java SalesReporter <csv-file-path> <output-method> [output-file-path]`
 * usage, while also being convenient to run directly from an IDE without
 * configuring Program Arguments every time.
 *
 * SRP: this class' only job is collecting raw input from the terminal. It
 * does no validation beyond "don't accept an empty answer" - the real
 * validation (e.g. is this a real file, is this method recognised) still
 * happens where it already lived: in CsvSalesDataReader and
 * OutputStrategyFactory. That keeps validation logic in exactly one place
 * for both the CLI-argument path and the interactive path.
 *
 * Module owner: Dulanjana (Member 3 - Console Interface, Exception Handling & Documentation)
 */
public class ConsoleInputPrompter {

    private final Scanner scanner;

    public ConsoleInputPrompter(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prompts the user for the CSV file path, the output method, and (only
     * if needed) the output file path, then returns them as a
     * {@link CliArguments} bundle.
     */
    public CliArguments promptForArguments() throws SalesReporterException {
        String csvFilePath = promptRequired("Enter the path to the input CSV file: ");
        String outputMethod = promptRequired("Enter output method ('console' or 'file'): ");

        String outputFilePath = null;
        if (outputMethod.trim().equalsIgnoreCase("file")) {
            outputFilePath = promptRequired("Enter the output file path: ");
        }

        return new CliArguments(csvFilePath, outputMethod, outputFilePath);
    }

    private String promptRequired(String prompt) throws SalesReporterException {
        System.out.print(prompt);
        String value = scanner.hasNextLine() ? scanner.nextLine() : null;

        if (value == null || value.isBlank()) {
            throw new SalesReporterException("No input received. " + prompt.replace(": ", "") + " cannot be empty.");
        }
        return value.trim();
    }
}
