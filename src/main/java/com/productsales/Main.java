package com.productsales;

import com.productsales.model.SaleRecord;
import com.productsales.parser.CsvSalesReader;
import com.productsales.service.SalesCalculator;
import com.productsales.service.SalesReportGenerator;
import com.productsales.output.ConsoleOutputStrategy;
import com.productsales.output.FileOutputStrategy;
import com.productsales.output.OutputStrategy;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Validate command-line arguments
        if (args.length < 2) {
            printUsage();
            return;
        }

        String csvFilePath = args[0];
        String outputMethod = args[1].toLowerCase();

        // Validate file output arguments
        if (outputMethod.equals("file") && args.length < 3) {
            System.err.println(
                    "Error: Output file path is required when using file output."
            );
            printUsage();
            return;
        }

        // Validate output method
        if (!outputMethod.equals("console") &&
                !outputMethod.equals("file")) {

            System.err.println(
                    "Error: Invalid output method. Use 'console' or 'file'."
            );
            printUsage();
            return;
        }

        try {

            // Read sales data from CSV
            CsvSalesReader reader = new CsvSalesReader();
            List<SaleRecord> sales = reader.read(csvFilePath);

            // Check whether data was loaded
            if (sales == null || sales.isEmpty()) {
                System.err.println(
                        "Error: No sales records were found in the CSV file."
                );
                return;
            }

            // Perform calculations
            SalesCalculator calculator = new SalesCalculator();

            // Generate report
            SalesReportGenerator reportGenerator =
                    new SalesReportGenerator(calculator);

            String report = reportGenerator.generateReport(sales);

            // Select output strategy
            OutputStrategy outputStrategy;

            if (outputMethod.equals("console")) {

                outputStrategy = new ConsoleOutputStrategy();

            } else {

                String outputFilePath = args[2];

                outputStrategy =
                        new FileOutputStrategy(outputFilePath);
            }

            // Output the generated report
            outputStrategy.output(report);

        } catch (Exception e) {

            System.err.println(
                    "Error: Unable to generate sales report."
            );

            System.err.println(
                    "Reason: " + e.getMessage()
            );
        }
    }

    /**
     * Displays the correct command-line usage.
     */
    private static void printUsage() {

        System.out.println();
        System.out.println("Product Sales Report Generator");
        System.out.println("--------------------------------");
        System.out.println("Usage:");
        System.out.println(
                "java Main <csv-file-path> <output-method> [output-file-path]"
        );
        System.out.println();
        System.out.println("Output methods:");
        System.out.println("  console");
        System.out.println("  file");
        System.out.println();
        System.out.println("Examples:");
        System.out.println(
                "java Main data/sales.csv console"
        );
        System.out.println(
                "java Main data/sales.csv file report.txt"
        );
    }
}