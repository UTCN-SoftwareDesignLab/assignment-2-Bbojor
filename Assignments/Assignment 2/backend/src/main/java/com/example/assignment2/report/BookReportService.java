package com.example.assignment2.report;

import com.example.assignment2.book.BookRepository;
import com.example.assignment2.book.model.Book;
import com.example.assignment2.search.PredicateBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;

@RequiredArgsConstructor
public abstract class BookReportService {

    final BookRepository bookRepository;

    public abstract String export() throws IOException;

    public abstract  BookReportType getType();

    Iterable<Book> getOutOfStockBooks() {
        PredicateBuilder<Book> predicateBuilder = new PredicateBuilder<>(Book.class);
        return bookRepository.findAll(predicateBuilder.build("quantity<0"));
    }
}
