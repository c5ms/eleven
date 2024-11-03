package com.eleven.hotel.domain.core;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ObjectMatcher<T> implements Predicate<T> {

    private final List<Predicate<T>> predicates = new ArrayList<>();

    public ObjectMatcher<T> should(Predicate<T> predicate) {
        this.predicates.add(predicate);
        return this;
    }

    @Override
    public boolean test(T t) {
        return predicates.stream().allMatch(p -> p.test(t));
    }
}
