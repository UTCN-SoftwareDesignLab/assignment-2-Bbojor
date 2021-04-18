package com.example.assignment2.search;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Used to store a constraint
 */
@Data
@AllArgsConstructor
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}
