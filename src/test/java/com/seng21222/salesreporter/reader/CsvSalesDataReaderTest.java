package com.seng21222.salesreporter.reader;

import com.seng21222.salesreporter.exception.CsvFileNotFoundException;
import com.seng21222.salesreporter.exception.InvalidCsvRowException;
import com.seng21222.salesreporter.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CsvSalesDataReaderTest {

    private final SalesDataReader reader = new CsvSalesDataReader();

    @TempDir
    Path tempDir;

    @Test
    void readData_validFileWithHeader_parsesAllRowsAndSkipsHeader() throws Exception {
        Path csv = writeCsv(
                "product_id, product_name, category, quantity_sold, unit_price",
                "P001, Wireless Mouse, Electronics, 12, 25.50",
                "P002, Notebook, Stationery, 35, 3.75"
        );

        List<Product> products = reader.readData(csv.toString());

        assertEquals(2, products.size());
        assertEquals("P001", products.get(0).getProductId());
        assertEquals(12, products.get(0).getQuantitySold());
        assertEquals(25.50, products.get(0).getUnitPrice(), 0.001);
    }

    @Test
    void readData_missingFile_throwsCsvFileNotFoundException() {
        String missingPath = tempDir.resolve("does-not-exist.csv").toString();

        assertThrows(CsvFileNotFoundException.class, () -> reader.readData(missingPath));
    }

    @Test
    void readData_rowWithMissingColumns_throwsInvalidCsvRowException() throws IOException {
        Path csv = writeCsv(
                "product_id, product_name, category, quantity_sold, unit_price",
                "P001, Wireless Mouse, Electronics, 12" // missing unit_price column
        );

        assertThrows(InvalidCsvRowException.class, () -> reader.readData(csv.toString()));
    }

    @Test
    void readData_rowWithNonNumericQuantity_throwsInvalidCsvRowException() throws IOException {
        Path csv = writeCsv(
                "product_id, product_name, category, quantity_sold, unit_price",
                "P001, Wireless Mouse, Electronics, twelve, 25.50"
        );

        assertThrows(InvalidCsvRowException.class, () -> reader.readData(csv.toString()));
    }

    private Path writeCsv(String... lines) throws IOException {
        Path file = tempDir.resolve("sales.csv");
        Files.write(file, List.of(lines));
        return file;
    }
}

