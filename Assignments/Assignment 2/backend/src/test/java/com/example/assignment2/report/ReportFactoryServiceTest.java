package com.example.assignment2.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ReportFactoryServiceTest {

    @Autowired
    private ReportFactoryService reportFactoryService;

    @Test
    void getReportService() throws IOException {
        BookReportService csvService =  reportFactoryService.getReportService(BookReportType.CSV);
        String actualCsvResult = csvService.export();
        Assertions.assertEquals("CSV", actualCsvResult);

        BookReportService pdfService =  reportFactoryService.getReportService(BookReportType.PDF);
        String actualPdfResult = pdfService.export();
        Assertions.assertEquals("PDF", actualPdfResult);

    }
}