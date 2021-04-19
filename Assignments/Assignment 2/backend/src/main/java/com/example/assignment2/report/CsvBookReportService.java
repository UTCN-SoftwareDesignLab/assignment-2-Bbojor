package com.example.assignment2.report;

import com.example.assignment2.book.BookRepository;
import com.example.assignment2.book.model.Book;
import org.springframework.stereotype.Service;

import java.io.File;
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
    public String export() {

        try{
            File csvOutputFile = new File("report_" + new Date().toString().replace(" ", "-") + ".csv");
            PrintWriter pw = new PrintWriter(csvOutputFile);
            Iterable<Book> books = getOutOfStockBooks();
            StreamSupport.stream(books.spliterator(),false)
                        .map(this::bookToCsv)
                        .forEach(pw::println);
            pw.close();

        } catch (IOException e) {
            return "ERROR";
        }

        return "CSV";
    }

    @Override
    public BookReportType getType() {
        return BookReportType.CSV;
    }

    private String bookToCsv(Book b) {
        return b.getAuthor() + ", " + b.getTitle();
    }
}
