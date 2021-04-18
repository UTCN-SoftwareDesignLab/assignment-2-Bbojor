package com.example.assignment2.book;

import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.book.dto.BookDTO;
import com.example.assignment2.book.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = new ArrayList<>();

        Book book1 = Book.builder()
                .author("Isaac Asimov")
                .title("The Foundation")
                .genre("Sci-Fi")
                .id(3000L)
                .price(20.0)
                .quantity(15)
                .build();

        Book book2 = Book.builder()
                .author("Stephen King")
                .title("The Shining")
                .genre("Horror")
                .id(2000L)
                .price(20.0)
                .quantity(15)
                .build();

        Book book3 = Book.builder()
                .author("H.P. Lovecraft")
                .title("1000 pages of just the n-word")
                .genre("Horror")
                .id(3002L)
                .price(20.0)
                .quantity(15)
                .build();

        Book book4 = Book.builder()
                .author("Stephen King")
                .title("It")
                .genre("Horror")
                .id(4001L)
                .price(20.0)
                .quantity(15)
                .build();

        Book book5 = Book.builder()
                .author("Some Dude")
                .title("Title with foundation in it")
                .genre("Self help")
                .id(5000L)
                .price(20.0)
                .quantity(15)
                .build();

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);

        bookRepository.saveAll(books);

        List<BookDTO> all = bookService.findAll(null);

        Assertions.assertEquals(books.size(), all.size());

        List<BookDTO> foundBooks = bookService.findAll("genre:horror");
        assertEquals(3 ,foundBooks.size());

        foundBooks = bookService.findAll("author:King");
        assertEquals(2 ,foundBooks.size());

        foundBooks = bookService.findAll("title:foundation");
        assertEquals(2 ,foundBooks.size());

        foundBooks = bookService.findAll("title:it");
        assertEquals(2 ,foundBooks.size());

        foundBooks = bookService.findAll("title:it,author:steph");
        assertEquals(1 ,foundBooks.size());
    }

    @Test
    void create() {
        BookDTO book = TestCreationFactory.newBookDTO();

        BookDTO created = bookService.create(book);
        book.setId(created.getId());
        assertEquals(created, book);
    }

    @Test
    void edit() {
        BookDTO oldBook = TestCreationFactory.newBookDTO();
        BookDTO book = TestCreationFactory.newBookDTO();

        oldBook = bookService.create(oldBook);
        book.setId(oldBook.getId());
        bookService.edit(book.getId(),book);

        List<BookDTO> books = bookService.findAll(null);

        assertTrue(books.contains(book));
        assertFalse(books.contains(oldBook));
    }

    @Test
    void delete() {
        BookDTO book = TestCreationFactory.newBookDTO();

        book = bookService.create(book);

        bookService.delete(book.getId());

        assertEquals(bookService.findAll(null).size(),0);
    }

    @Test
    void getBook() {
        BookDTO book = TestCreationFactory.newBookDTO();

        book = bookService.create(book);

        BookDTO retrievedBook = bookService.getBook(book.getId());

        assertEquals(book, retrievedBook);
    }
}