#  Command-Line  Product-Sales-Report-Generator

**Module: SENG 21222 -Software Construction**

**Team:** 

| Github ID    | Student Number -Name                                                                                                                                        |
|--------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| SPTN0912     | SE/2023/028 - S.P.T.Nimasha                                                                                                                                 |
| Lasathmie    | SE/2023/026 - P.G.S.Lasathmie                                                                                                                               |
| Chandula2324 | SE/2023/050 -J.P.D.M.Chandula


---

## 1. What the tool does

```
java -jar Product-Sales-Report-Generator.jar <csv-file-path> <output-method> [output-file-path]
```

- `<csv-file-path>` – path to a CSV file of `product_id, product_name, category, quantity_sold, unit_price`
- `<output-method>` – `console` or `file`
- `[output-file-path]` – required only when `<output-method>` is `file`

It computes: revenue per product, revenue per category, the best-selling
product (highest `quantity_sold`), the highest-revenue product, and the
grand total revenue — then prints or saves a formatted report.

A sample input file is included at `sample-data/sales.csv`. Example run once built:

```
java -jar target/Product-Sales-Report-Generator.jar sample-data/sales.csv console
java -jar target/Product-Sales-Report-Generator.jar sample-data/sales.csv file sample-output/summery-report.txt
```

### Interactive mode

If you run the tool with **no arguments at all**, it asks for the same
three values one at a time instead:

```
$ java -jar target/Product-Sales-Report-Generator.jar
Enter the path to the input CSV file: sample-data/sales.csv
Enter output method ('console' or 'file'): file
Enter the output file path: sample-output/summery-report.txt
Report successfully written to: sample-output/summery-report.txt
```

This is handled by `cli.ConsoleInputPrompter` and is only a convenience for
running the jar directly without setting Program Arguments each time — the
underlying validation (file exists, method recognised, etc.) is still done
in exactly the same place (`CsvSalesDataReader`, `OutputStrategyFactory`) as
the command-line-argument path, so both paths behave identically.

## 2.Project Structure
```text 
Product-Sales-Report-Generator/
│
├── sample-data/
│   └── sales.csv
│
├── sample-output/
│   └── summery-report.txt
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com.seng21222.salesreporter/
│   │           │
│   │           ├── cli/
│   │           │   ├── CliArguments.java
│   │           │   └── ConsoleInputPrompter.java
│   │           │
│   │           ├── exception/
│   │           │   ├── CsvFileNotFoundException.java
│   │           │   ├── InvalidCsvRowException.java
│   │           │   ├── InvalidOutputMethodException.java
│   │           │   ├── ReportOutputException.java
│   │           │   └── SalesReporterException.java
│   │           │
│   │           ├── model/
│   │           │   └── Product.java
│   │           │
│   │           ├── output/
│   │           │   ├── ConsoleOutputStrategy.java
│   │           │   ├── FileOutputStrategy.java
│   │           │   ├── OutputStrategyFactory.java
│   │           │   └── ReportOutputStrategy.java
│   │           │
│   │           ├── reader/
│   │           │   ├── CsvSalesDataReader.java
│   │           │   └── SalesDataReader.java
│   │           │
│   │           ├── report/
│   │           │   ├── ReportGenerator.java
│   │           │   └── TextReportGenerator.java
│   │           │
│   │           ├── service/
│   │           │   ├── DefaultRevenueCalculator.java
│   │           │   ├── RevenueCalculator.java
│   │           │   ├── SalesSummary.java
│   │           │   └── SalesSummeryService.java
│   │           │
│   │           └── SalesReporter.java
│   │
│   └── test/
│       └── java/
│           └── com.seng21222.salesreporter/
│               │
│               ├── cli/
│               │   └── ConsoleInputPrompterTest.java
│               │
│               ├── reader/
│               │   └── CsvSalesDataReaderTest.java
│               │
│               └── service/
│                   └── DefaultRevenueCalculatorTest.java
│
├── .gitignore
├── pom.xml
└── README.md






```

## 3. Architecture

The project follows a **Strategy + layered pipeline** design so each stage
can change independently:

```
CSV file --> SalesDataReader --> List<Product>
                                     |
                                     v
                           RevenueCalculator (via SalesSummaryService)
                                     |
                                     v
                              SalesSummary
                                     |
                                     v
                           ReportGenerator --> report text
                                     |
                                     v
                     ReportOutputStrategy (console / file / ...future)
```

| Package         | Responsibility |
|-----------------|--|
| `model`         | `Product` — a plain, immutable data record |
| `reader`        | `SalesDataReader` (interface) + `CsvSalesDataReader` — turns a CSV file into `List<Product>` |
| `service`       | `RevenueCalculator` (interface) + `DefaultRevenueCalculator` — all the number-crunching; `SalesSummary` (data holder); `SalesSummaryService` (orchestrator) |
| `report`        | `ReportGenerator` (interface) + `TextReportGenerator` — formats the summary as text |
| `output`        | `ReportOutputStrategy` (interface) + `ConsoleOutputStrategy` / `FileOutputStrategy` + `OutputStrategyFactory` — delivers the report |
| `exception`     | Custom checked exceptions with clear, user-facing messages |
| `SalesReporter` | `main()` — parses args, wires everything together, catches all errors |
| `cli`           | `CliArguments` — immutable holder for the resolved csv-path/output-method/output-file-path; `ConsoleInputPrompter` — asks for those same three values interactively when no arguments are given |

### Why this satisfies the assignment's design requirements

- **Single Responsibility Principle** – every class does exactly one job
  (reading, calculating, formatting, or delivering). None of them know
  about each other's internals.
- **Open-Closed Principle / extensibility** – adding a new output method
  (e.g. email) later means writing one new class that implements
  `ReportOutputStrategy` and adding one line to `OutputStrategyFactory`.
  No existing class needs to be modified. The same is true for a new data
  source (implement `SalesDataReader`) or a new report format (implement
  `ReportGenerator`).
- **Dependency Inversion** – `SalesReporter` and `SalesSummaryService`
  depend on interfaces (`SalesDataReader`, `RevenueCalculator`,
  `ReportOutputStrategy`), not concrete classes, and those concrete
  implementations are injected in `SalesReporter.run()` (the composition
  root).
- **Error handling** – every failure path (missing file, malformed row,
  invalid output method, unwritable output path) throws a specific
  checked exception, all rooted in `SalesReporterException`, which
  `main()` catches once and turns into one clean error message instead of
  a stack trace.

## 4. Division of work

Following the suggested split in the assignment brief:

| Member           | Focus                                                                                        | Classes owned                                                                                                                                                                                                                                   |
|------------------|----------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **SPTN0912**     | Core logic — reading the product data into memory, computing the summary, writing the report | `model.Product`, `service.RevenueCalculator`, `service.DefaultRevenueCalculator`, `service.SalesSummary`, `service.SalesSummaryService`, `report.ReportGenerator`, `report.TextReportGenerator`,`src/test/service.DefaultRevenueCalculatorTest` |
| **Lasathmie**    | File I/O, unit testing, SOLID review,documentation                                           | `reader.SalesDataReader`, `reader.CsvSalesDataReader` `src/test/reader.CsvSalesDataReaderTest`, sample CSV data ,sample summery report                                                                                                          |
| **Chandula2324** | Console interface, exception handling                                                        | `SalesReporter` (main), `cli.ConsoleInputPrompter`, `cli.CliArguments`, all classes in `exception`, all classes in `output` ,`src/test/cli.ConsoleInputPrompterTest`                                                                            |





## 5. Building and running in IntelliJ IDEA

1. **File → Open** and select the `Product-Sales-Report-Generator` project folder (the one
   containing `pom.xml`). IntelliJ will detect it as a Maven project and
   download the JUnit 5 dependencies automatically.
2. Set the Project SDK to **Java 17** or later (File → Project Structure →
   Project SDK).
3. To run the tool: right-click `SalesReporter.java` → **Run**, or add
   Program Arguments under **Run → Edit Configurations** (e.g.
   `sample-data/sales.csv console`).
4. To run the tests: right-click the `src/test/java` folder → **Run
   'All Tests'** (uses JUnit 5, already wired in `pom.xml`).
5. To build a runnable jar from the terminal:
   ```
   mvn clean package
   java -jar target/Product-Sales-Report-Generator.jar sample-data/sales.csv console
   ```

## 6. Unit tests included

- `DefaultRevenueCalculatorTest` — revenue-per-product, revenue-per-category,
  grand total, best-seller detection, highest-revenue detection, plus
  empty-list edge cases (meets the assignment's minimum testing
  requirement).
- `CsvSalesDataReaderTest` — valid file parsing, header skipping, missing
  file, and malformed rows (missing column / non-numeric value).
- `ConsoleInputPrompterTest`-interactive-mode prompting: asking for the CSV path and output method, only asking for the output file path when the method is file (and not asking for it when it's console), and rejecting a blank/empty answer with an exception.

