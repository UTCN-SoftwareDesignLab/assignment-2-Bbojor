package com.example.assignment2.report;

import com.example.assignment2.book.BookRepository;
import com.example.assignment2.book.model.Book;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.stream.StreamSupport;

@Service
public class CsvBookReportService extends BookReportService {

    public CsvBookReportService(BookRepository bookRepository) {
        super(bookRepository);
    }

    @Override
    public String export() throws FileNotFoundException {

        String fileName = "report_" + new Date().toString().replace(" ", "-") + ".csv";

        File csvOutputFile = new File(fileName);
        PrintWriter pw = new PrintWriter(csvOutputFile);
        Iterable<Book> books = getOutOfStockBooks();
        StreamSupport.stream(books.spliterator(),false)
                .map(Book::toCSV)
                .forEach(pw::println);
        pw.close();

        return fileName;
    }

    @Override
    public BookReportType getType() {
        return BookReportType.CSV;
    }


}
