package com.seng21222.salesreporter.service;

import com.seng21222.salesreporter.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RevenueCalculator {

    double calculateProductRevenue(Product product);

    Map<String, Double> calculateRevenuePerCategory(List<Product> products);

    double calculateGrandTotalRevenue(List<Product> products);

    Optional<Product> findBestSellingProduct(List<Product> products);

    Optional<Product> findHighestRevenueProduct(List<Product> products);
}
