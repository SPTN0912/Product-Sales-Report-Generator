package com.seng21222.salesreporter.report;

import com.seng21222.salesreporter.model.Product;
import com.seng21222.salesreporter.service.SalesSummary;

public class TextReportGenerator implements ReportGenerator {

    private static final String DIVIDER = "============================================";

    @Override
    public String generateReport(SalesSummary summary) {
        StringBuilder report = new StringBuilder();

        report.append(DIVIDER).append(System.lineSeparator());
        report.append(" PRODUCT SALES SUMMARY REPORT").append(System.lineSeparator());
        report.append(DIVIDER).append(System.lineSeparator());

        appendProductSection(report, summary);
        appendCategorySection(report, summary);
        appendHighlightsSection(report, summary);

        report.append(DIVIDER);

        return report.toString();
    }

    private void appendProductSection(StringBuilder report, SalesSummary summary) {
        report.append("--- Revenue Per Product ---").append(System.lineSeparator());
        for (Product product : summary.getProducts()) {
            report.append(String.format("%-6s %-18s %-14s $%.2f%n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getCategory(),
                    product.getRevenue()));
        }
    }

    private void appendCategorySection(StringBuilder report, SalesSummary summary) {
        report.append(System.lineSeparator()).append("--- Revenue Per Category ---").append(System.lineSeparator());
        summary.getRevenuePerCategory().forEach((category, revenue) ->
                report.append(String.format("%s : $%.2f%n", category, revenue)));
    }

    private void appendHighlightsSection(StringBuilder report, SalesSummary summary) {
        report.append(System.lineSeparator()).append("--- Highlights ---").append(System.lineSeparator());

        summary.getBestSellingProduct().ifPresent(product ->
                report.append(String.format("Best-Selling Product : %s (%d units)%n",
                        product.getProductName(), product.getQuantitySold())));

        summary.getHighestRevenueProduct().ifPresent(product ->
                report.append(String.format("Highest Revenue : %s ($%.2f)%n",
                        product.getProductName(), product.getRevenue())));

        report.append(String.format("Grand Total Revenue : $%.2f%n", summary.getGrandTotalRevenue()));
    }

}
