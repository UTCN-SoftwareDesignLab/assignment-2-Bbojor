package com.example.assignment2.report;

import com.example.assignment2.book.BookRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CsvBookReportService implements BookReportService {

    private final BookRepository bookRepository;

    @Override
    public String export() {

        return "CSV";
    }

    @Override
    public BookReportType getType() {
        return BookReportType.CSV;
    }
}
