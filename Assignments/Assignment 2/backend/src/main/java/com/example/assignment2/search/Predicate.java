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

    //constant used for search matching
    private static final String EQUALS = ":";
    private static final String GREATER_OR_EQUAL = ">";
    private static final String LESS_OR_EQUAL = "<";

    //regex string which matches an operation
    private final static String OPERATION_GROUP_MATCHER = "(" + EQUALS + "|" + GREATER_OR_EQUAL + "|" + LESS_OR_EQUAL + ")";
    //regex string to match a term (field name or string to be searched)
    private final static String TERM_GROUP_MATCHER = "([^" + EQUALS + GREATER_OR_EQUAL + LESS_OR_EQUAL+  "]+?)";

    //comma at the end used to in case of multiple search parameters
    public static final String SEARCH_REGEX = TERM_GROUP_MATCHER + OPERATION_GROUP_MATCHER + TERM_GROUP_MATCHER + ",";

    private final SearchCriteria searchCriteria;
    private final Class<T> typeParameterClass;

    public BooleanExpression createPredicate() {
        PathBuilder<T> entityPath = new PathBuilder<T>(typeParameterClass, typeParameterClass.getSimpleName().toLowerCase());

        if (isInteger(searchCriteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(searchCriteria.getKey(), Integer.class);
            int value = Integer.parseInt(searchCriteria.getValue().toString());
            switch (searchCriteria.getOperation()) {
                case EQUALS:
                    return path.eq(value);
                case GREATER_OR_EQUAL:
                    return path.goe(value);
                case LESS_OR_EQUAL:
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
