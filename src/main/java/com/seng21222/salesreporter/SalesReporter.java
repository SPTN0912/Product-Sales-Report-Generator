package com.seng21222.salesreporter;

import com.seng21222.salesreporter.cli.CliArguments;
import com.seng21222.salesreporter.cli.ConsoleInputPrompter;
import com.seng21222.salesreporter.exception.SalesReporterException;
import com.seng21222.salesreporter.model.Product;
import com.seng21222.salesreporter.output.OutputStrategyFactory;
import com.seng21222.salesreporter.output.ReportOutputStrategy;
import com.seng21222.salesreporter.reader.CsvSalesDataReader;
import com.seng21222.salesreporter.reader.SalesDataReader;
import com.seng21222.salesreporter.report.ReportGenerator;
import com.seng21222.salesreporter.report.TextReportGenerator;
import com.seng21222.salesreporter.service.DefaultRevenueCalculator;
import com.seng21222.salesreporter.service.RevenueCalculator;
import com.seng21222.salesreporter.service.SalesSummary;
import com.seng21222.salesreporter.service.SalesSummaryService;

import java.util.List;
import java.util.Scanner;

public class SalesReporter {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            run(args, scanner);
        } catch (SalesReporterException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            // Safety net for anything truly unexpected, so the user never
            // sees a raw Java stack trace.
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void run(String[] args, Scanner scanner) throws SalesReporterException {
        CliArguments cliArguments = resolveArguments(args, scanner);

        // --- Composition root: wire up the collaborators ---
        SalesDataReader reader = new CsvSalesDataReader();
        RevenueCalculator calculator = new DefaultRevenueCalculator();
        SalesSummaryService summaryService = new SalesSummaryService(calculator);
        ReportGenerator reportGenerator = new TextReportGenerator();
        ReportOutputStrategy outputStrategy = OutputStrategyFactory.create(
                cliArguments.getOutputMethod(), cliArguments.getOutputFilePath());

        // --- Pipeline: read -> compute -> format -> deliver ---
        List<Product> products = reader.readData(cliArguments.getCsvFilePath());
        SalesSummary summary = summaryService.buildSummary(products);
        String reportText = reportGenerator.generateReport(summary);
        outputStrategy.output(reportText);
    }

    /**
     * Decides where the three inputs come from:
     *   - no arguments at all  -> ask for them interactively
     *   - 2 or 3 arguments     -> use them exactly as the assignment specifies
     *   - anything else (1 argument, or 4+)-> usage error
     */
    private static CliArguments resolveArguments(String[] args, Scanner scanner) throws SalesReporterException {
        if (args.length == 0) {
            ConsoleInputPrompter prompter = new ConsoleInputPrompter(scanner);
            return prompter.promptForArguments();
        }

        if (args.length < 2 || args.length > 3) {
            throw new SalesReporterException(
                    "Usage: java SalesReporter <csv-file-path> <output-method> [output-file-path]"
                            + System.lineSeparator()
                            + "  <csv-file-path>    : Path to the input CSV file"
                            + System.lineSeparator()
                            + "  <output-method>    : 'console' or 'file'"
                            + System.lineSeparator()
                            + "  [output-file-path] : Required only when output-method is 'file'"
                            + System.lineSeparator()
                            + "(Or run with no arguments at all to be prompted interactively.)");
        }

        String csvFilePath = args[0];
        String outputMethod = args[1];
        String outputFilePath = args.length == 3 ? args[2] : null;
        return new CliArguments(csvFilePath, outputMethod, outputFilePath);
    }
}
