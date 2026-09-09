package com.seng21222.salesreporter.service;

import com.seng21222.salesreporter.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRevenueCalculatorTest {

    private RevenueCalculator calculator;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        calculator = new DefaultRevenueCalculator();
        products = List.of(
                new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
                new Product("P002", "Notebook", "Stationery", 35, 3.75),
                new Product("P003", "USB Hub", "Electronics", 8, 18.00),
                new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50),
                new Product("P005", "HDMI Cable", "Electronics", 20, 12.00)
        );
    }

    @Test
    void calculateProductRevenue_multipliesQuantityByUnitPrice() {
        Product mouse = products.get(0);
        assertEquals(306.00, calculator.calculateProductRevenue(mouse), 0.001);
    }

    @Test
    void calculateProductRevenue_zeroQuantity_returnsZero() {
        Product zeroSales = new Product("P099", "Unsold Item", "Misc", 0, 99.99);
        assertEquals(0.0, calculator.calculateProductRevenue(zeroSales), 0.001);
    }

    @Test
    void calculateRevenuePerCategory_sumsCorrectlyPerCategory() {
        Map<String, Double> revenuePerCategory = calculator.calculateRevenuePerCategory(products);

        // Electronics: 306.00 + 144.00 + 240.00 = 690.00
        assertEquals(690.00, revenuePerCategory.get("Electronics"), 0.001);
        // Stationery: 131.25 + 50.00 = 181.25
        assertEquals(181.25, revenuePerCategory.get("Stationery"), 0.001);
    }

    @Test
    void calculateGrandTotalRevenue_sumsAllProducts() {
        double total = calculator.calculateGrandTotalRevenue(products);
        assertEquals(871.25, total, 0.001);
    }

    @Test
    void calculateGrandTotalRevenue_emptyList_returnsZero() {
        assertEquals(0.0, calculator.calculateGrandTotalRevenue(List.of()), 0.001);
    }

    @Test
    void findBestSellingProduct_returnsHighestQuantitySold() {
        Optional<Product> bestSeller = calculator.findBestSellingProduct(products);

        assertTrue(bestSeller.isPresent());
        assertEquals("Ballpoint Pen", bestSeller.get().getProductName());
        assertEquals(100, bestSeller.get().getQuantitySold());
    }

    @Test
    void findBestSellingProduct_emptyList_returnsEmptyOptional() {
        assertTrue(calculator.findBestSellingProduct(List.of()).isEmpty());
    }

    @Test
    void findHighestRevenueProduct_returnsHighestRevenueProduct() {
        Optional<Product> topEarner = calculator.findHighestRevenueProduct(products);

        assertTrue(topEarner.isPresent());
        assertEquals("Wireless Mouse", topEarner.get().getProductName());
        assertEquals(306.00, topEarner.get().getRevenue(), 0.001);
    }

    @Test
    void findHighestRevenueProduct_emptyList_returnsEmptyOptional() {
        assertTrue(calculator.findHighestRevenueProduct(List.of()).isEmpty());
    }



}