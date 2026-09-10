package com.seng21222.salesreporter.output;

/**
 * Prints the report to standard output. Used when the user runs the tool
 * with output-method = "console".
 */
public class ConsoleOutputStrategy implements ReportOutputStrategy {

    @Override
    public void output(String reportText) {
        System.out.println(reportText);
    }
}
