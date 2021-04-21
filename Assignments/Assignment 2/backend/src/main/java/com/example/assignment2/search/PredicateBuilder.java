package com.example.assignment2.search;

import com.example.assignment2.book.model.Book;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public BooleanExpression build(String search) {

        //parse search string and create appropriate query conditions
        Pattern pattern = Pattern.compile(Predicate.SEARCH_REGEX);
        Matcher matcher = pattern.matcher(search + ",");
        while(matcher.find()) {
            with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        List<BooleanExpression> predicates = criteria.stream()
                .map(crit -> new Predicate<T>(crit,typeParameterClass).createPredicate())
                .collect(Collectors.toList());

        //build the final expression out of all the criteria, initialize with a true expression since we are "and"-ing them
        // create a true expression, then convert it into a boolean one
        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for(BooleanExpression expr:predicates) {
            result = result.and(expr);
        }

        return result;
    }
}
