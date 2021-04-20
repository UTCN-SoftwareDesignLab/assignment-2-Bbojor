package com.example.assignment2.book.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    private String author;

    @NotBlank(message = "Genre cannot be blank")
    private String genre;

    @NotNull(message = "Must enter a price")
    @Positive(message = "Price should be positive")
    private Double price;

    @NotNull(message = "Must enter a quantity")
    @PositiveOrZero(message = "Quantity must be greater or equal to 0")
    private Integer quantity;
}
