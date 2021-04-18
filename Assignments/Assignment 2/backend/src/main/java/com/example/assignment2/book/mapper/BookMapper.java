package com.example.assignment2.book.mapper;

import com.example.assignment2.book.dto.BookDTO;
import com.example.assignment2.book.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDto(Book book);

    Book fromDto(BookDTO book);
}
