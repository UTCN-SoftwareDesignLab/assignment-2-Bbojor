package com.example.assignment2.report;

import com.example.assignment2.book.BookRepository;

import java.io.IOException;

public interface BookReportService {

    public final static String SAVE_PATH = "./reports";
    public String export();

    public BookReportType getType();
}
