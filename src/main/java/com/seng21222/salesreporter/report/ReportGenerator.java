package com.seng21222.salesreporter.report;

import com.seng21222.salesreporter.service.SalesSummary;

public interface ReportGenerator {

    String generateReport(SalesSummary summary);
}
