package com.example.assignment2.search;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Builds a complex query predicate out of several basic ones.
 * @param <T> Class of the objects that are the subjects of the query
 */
@RequiredArgsConstructor
public class PredicateBuilder<T> {

    private final List<SearchCriteria> criteria = new ArrayList<>();
    private final Class<T> typeParameterClass;

    public PredicateBuilder<T> with(String key, String operation ,String value) {
        criteria.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        List<BooleanExpression> predicates = criteria.stream()
                .map(crit ->
                    new Predicate<T>(crit,typeParameterClass).createPredicate()
                ).collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for(BooleanExpression expr:predicates) {
            result = result.and(expr);
        }

        return result;
    }
}
