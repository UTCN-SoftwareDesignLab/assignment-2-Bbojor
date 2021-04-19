package com.example.assignment2.report;

import com.example.assignment2.book.BookRepository;
import com.example.assignment2.book.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvBookReportServiceIntegrationTest {

    @Autowired
    private  CsvBookReportService reportService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void export() {
        List<Book> books = new ArrayList<>();
        Book book1 = Book.builder()
                .author("Isaac Asimov")
                .title("The Foundation")
                .genre("Sci-Fi")
                .id(1L)
                .price(20.0)
                .quantity(15)
                .build();

        Book book2 = Book.builder()
                .author("Stephen King")
                .title("The Shining")
                .genre("Horror")
                .id(2L)
                .price(20.0)
                .quantity(15)
                .build();

        Book book3 = Book.builder()
                .author("H.P. Lovecraft")
                .title("1000 pages of just the n-word")
                .genre("Horror")
                .id(3L)
                .price(20.0)
                .quantity(15)
                .build();

        Book book4 = Book.builder()
                .author("Stephen King")
                .title("It")
                .genre("Horror")
                .id(4L)
                .price(20.0)
                .quantity(0)
                .build();

        Book book5 = Book.builder()
                .author("Some Dude")
                .title("Title with foundation in it")
                .genre("Self help")
                .id(5L)
                .price(20.0)
                .quantity(0)
                .build();

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);

        bookRepository.saveAll(books);

        String result = reportService.export();
        assertEquals(result, "CSV");
    }
}