package com.example.assignment2.book.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String author;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

}
