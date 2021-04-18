package com.example.assignment2.report;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportFactoryService {

    private final Map<BookReportType, BookReportService> reportServices;

    public ReportFactoryService(List<BookReportService> bookReportServices) {
        this.reportServices = bookReportServices.stream()
                .collect(Collectors.toMap(BookReportService::getType,
                                         Function.identity()));
    }

    public BookReportService getReportService(BookReportType reportType) {
        return reportServices.get(reportType);
    }

}
