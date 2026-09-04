package com.productsales.output;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutputStrategy implements OutputStrategy {

    private final String filePath;

    public FileOutputStrategy(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void output(String report) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(report);
            System.out.println("Report successfully written to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to write report to file.");
        }
    }
}