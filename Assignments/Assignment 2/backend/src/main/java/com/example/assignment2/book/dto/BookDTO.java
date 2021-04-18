package com.example.assignment2.book.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    private String title;

    private String author;

    private String genre;

    private Double price;

    private Integer quantity;
}
