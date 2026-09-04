package com.productsales.output;

public class ConsoleOutputStrategy implements OutputStrategy {

    @Override
    public void output(String report) {
        System.out.println(report);
    }
}