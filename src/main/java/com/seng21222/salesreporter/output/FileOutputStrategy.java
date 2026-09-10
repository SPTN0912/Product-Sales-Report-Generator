package com.seng21222.salesreporter.output;

import com.seng21222.salesreporter.exception.ReportOutputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOutputStrategy implements ReportOutputStrategy {

    private final String outputFilePath;

    public FileOutputStrategy(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void output(String reportText) throws ReportOutputException {
        try {
            Path path = Path.of(outputFilePath);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, reportText);
            System.out.println("Report successfully written to: " + outputFilePath);
        } catch (IOException e) {
            throw new ReportOutputException(
                    "Could not write report to file: " + outputFilePath, e);
        }
    }
}
