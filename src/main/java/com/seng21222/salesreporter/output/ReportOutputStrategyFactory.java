package com.seng21222.salesreporter.output;

import com.seng21222.salesreporter.exception.ReportOutputException;

public interface ReportOutputStrategy {
    void output(String reportText) throws ReportOutputException;
}
