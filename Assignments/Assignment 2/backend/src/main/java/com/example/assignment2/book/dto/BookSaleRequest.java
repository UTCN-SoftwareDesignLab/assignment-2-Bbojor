package com.example.assignment2.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookSaleRequest {
    @NotNull(message = "Must specify amount of books to sell")
    @Positive(message = "Cannot sell 0 or negative amount of books")
    private Integer quantity;
}
