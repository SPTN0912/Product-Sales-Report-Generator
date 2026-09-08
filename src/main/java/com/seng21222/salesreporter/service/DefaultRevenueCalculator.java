package com.seng21222.salesreporter.service;

import com.seng21222.salesreporter.model.Product;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultRevenueCalculator implements RevenueCalculator{

    @Override
    public double calculateProductRevenue(Product product) {
        return product.getRevenue();
    }

    @Override
    public Map<String, Double> calculateRevenuePerCategory(List<Product> products) {

        Map<String, Double> revenuePerCategory = new LinkedHashMap<>();

        for (Product product : products) {
            revenuePerCategory.merge(
                    product.getCategory(),
                    calculateProductRevenue(product),
                    Double::sum
            );
        }

        return revenuePerCategory;
    }

    @Override
    public double calculateGrandTotalRevenue(List<Product> products) {
        double total = 0.0;
        for (Product product : products) {
            total += calculateProductRevenue(product);
        }
        return total;
    }

    @Override
    public Optional<Product> findBestSellingProduct(List<Product> products) {
        return products.stream()
                .max(Comparator.comparingInt(Product::getQuantitySold));
    }

    @Override
    public Optional<Product> findHighestRevenueProduct(List<Product> products) {
        return products.stream()
                .max(Comparator.comparingDouble(this::calculateProductRevenue));
    }


}
