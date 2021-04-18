package com.example.assignment2.search;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AllArgsConstructor;

/**
 * Defines a single constraint on a query for items of type T.
 * @param <T> Class of the objects that are the subjects of the query
 */
@AllArgsConstructor
public class Predicate<T> {

    private final SearchCriteria searchCriteria;
    private final Class<T> typeParameterClass;

    public BooleanExpression createPredicate() {
        PathBuilder<T> entityPath = new PathBuilder<T>(typeParameterClass, typeParameterClass.getSimpleName().toLowerCase());

        if (isInteger(searchCriteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(searchCriteria.getKey(), Integer.class);
            int value = Integer.parseInt(searchCriteria.getValue().toString());
            switch (searchCriteria.getOperation()) {
                case ":":
                    return path.eq(value);
                case ">":
                    return path.goe(value);
                case "<":
                    return path.loe(value);

            }
        }
        else {
            StringPath path = entityPath.getString(searchCriteria.getKey());

            return path.containsIgnoreCase(searchCriteria.getValue().toString());

        }

        return null;
    }

    private static boolean isInteger(final String str) {
        try {
            Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

}
