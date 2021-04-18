package com.example.assignment2.book;

import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.book.model.Book;
import com.example.assignment2.search.PredicateBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(books);
        List<Book> all = repository.findAll();
        assertEquals(books.size(), all.size());
    }

    @Test
    public void TestFindFiltered() {
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

        repository.saveAll(books);

        BooleanExpression select = new PredicateBuilder<Book>(Book.class).with("genre", ":", "horror").build();
        List<Book> foundBooks = StreamSupport.stream(
                repository.findAll(select).spliterator(),false)
                .collect(Collectors.toList()
                );

        assertEquals(3 ,foundBooks.size());

        select = new PredicateBuilder<Book>(Book.class).with("author", ":", "King").build();
        foundBooks = StreamSupport.stream(
                repository.findAll(select).spliterator(),false)
                .collect(Collectors.toList()
                );

        assertEquals(2 ,foundBooks.size());

        select = new PredicateBuilder<Book>(Book.class).with("title", ":", "foundation").build();
        foundBooks = StreamSupport.stream(
                repository.findAll(select).spliterator(),false)
                .collect(Collectors.toList()
                );

        assertEquals(2 ,foundBooks.size());

        select = new PredicateBuilder<Book>(Book.class).with("title", ":", "it").build();
        foundBooks = StreamSupport.stream(
                repository.findAll(select).spliterator(),false)
                .collect(Collectors.toList()
                );

        assertEquals(2 ,foundBooks.size());

        select = new PredicateBuilder<Book>(Book.class)
                .with("title", ":", "it")
                .with("author",":", "steph")
                .build();
        foundBooks = StreamSupport.stream(
                repository.findAll(select).spliterator(),false)
                .collect(Collectors.toList()
                );

        assertEquals(1 ,foundBooks.size());

        select = new PredicateBuilder<Book>(Book.class)
                .with("quantity", ":", "0")
                .build();

        foundBooks = StreamSupport.stream(
                repository.findAll(select).spliterator(),false)
                .collect(Collectors.toList()
                );

        assertEquals(2 ,foundBooks.size());
    }
}