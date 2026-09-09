package com.seng21222.salesreporter.service;

import com.seng21222.salesreporter.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class SalesSummary {

    private final List<Product> products;
    private final Map<String, Double> revenuePerCategory;
    private final double grandTotalRevenue;
    private final Optional<Product> bestSellingProduct;
    private final Optional<Product> highestRevenueProduct;

    public SalesSummary(List<Product> products,
                        Map<String, Double> revenuePerCategory,
                        double grandTotalRevenue,
                        Optional<Product> bestSellingProduct,
                        Optional<Product> highestRevenueProduct) {
        this.products = products;
        this.revenuePerCategory = revenuePerCategory;
        this.grandTotalRevenue = grandTotalRevenue;
        this.bestSellingProduct = bestSellingProduct;
        this.highestRevenueProduct = highestRevenueProduct;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<String, Double> getRevenuePerCategory() {
        return revenuePerCategory;
    }

    public double getGrandTotalRevenue() {
        return grandTotalRevenue;
    }

    public Optional<Product> getBestSellingProduct() {
        return bestSellingProduct;
    }

    public Optional<Product> getHighestRevenueProduct() {
        return highestRevenueProduct;
    }
}
