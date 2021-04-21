package com.example.assignment2.book;

import com.example.assignment2.book.dto.BookDTO;
import com.example.assignment2.book.mapper.BookMapper;
import com.example.assignment2.book.model.Book;
import com.example.assignment2.search.PredicateBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll(String search) {

        if(search != null) {

            PredicateBuilder<Book> predicateBuilder = new PredicateBuilder<Book>(Book.class);
            BooleanExpression booleanExpression = predicateBuilder.build(search);

            return  StreamSupport.stream(
                    bookRepository.findAll(booleanExpression).spliterator(),false)
                    .map(bookMapper::toDto)
                    .collect(Collectors.toList()
                    );
        }
        else //just fetch everything
            return bookRepository.findAll().stream()
                    .map(bookMapper::toDto)
                    .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book existingBook = findById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setGenre(book.getGenre());
        existingBook.setPrice(book.getPrice());
        existingBook.setQuantity(book.getQuantity());
        return bookMapper.toDto(bookRepository.save(existingBook));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDTO getBook(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Book not found: " + id)));
    }

    public BookDTO sell(Long id, Integer quantity) {
        Book existingBook = findById(id);
        int newStock  = existingBook.getQuantity() - quantity;
        if(newStock < 0) {
            return null;
        }
        else {
            existingBook.setQuantity(newStock);
            return bookMapper.toDto(bookRepository.save(existingBook));
        }
    }
}
