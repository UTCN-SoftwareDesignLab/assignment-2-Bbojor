package com.example.assignment2.book;

import com.example.assignment2.book.model.Book;
import com.example.assignment2.book.model.QBook;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book>, QuerydslBinderCustomizer<QBook> {
    @Override
    default void customize(QuerydslBindings bindings, QBook root){
        bindings.bind(String.class)
                .first((SingleValueBinding< StringPath, String>) StringExpression::containsIgnoreCase);
    };
}
