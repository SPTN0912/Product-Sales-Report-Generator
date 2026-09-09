package com.seng21222.salesreporter.service;

import com.seng21222.salesreporter.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SalesSummaryService {

    private final RevenueCalculator revenueCalculator;

    public SalesSummaryService(RevenueCalculator revenueCalculator) {
        this.revenueCalculator = revenueCalculator;
    }

    public SalesSummary buildSummary(List<Product> products) {
        Map<String, Double> revenuePerCategory =
                revenueCalculator.calculateRevenuePerCategory(products);

        double grandTotalRevenue =
                revenueCalculator.calculateGrandTotalRevenue(products);

        Optional<Product> bestSellingProduct =
                revenueCalculator.findBestSellingProduct(products);

        Optional<Product> highestRevenueProduct =
                revenueCalculator.findHighestRevenueProduct(products);

        return new SalesSummary(
                products,
                revenuePerCategory,
                grandTotalRevenue,
                bestSellingProduct,
                highestRevenueProduct
        );
    }
}
