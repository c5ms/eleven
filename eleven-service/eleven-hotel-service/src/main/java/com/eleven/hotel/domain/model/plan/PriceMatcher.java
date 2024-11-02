package com.eleven.hotel.domain.model.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public  class PriceMatcher {
    private final List<Predicate<Price>> predicates = new ArrayList<>();

    public PriceMatcher should(Predicate<Price> predicate) {
        this.predicates.add(predicate);
        return this;
    }

    public boolean match(Price price) {
        return predicates.stream().allMatch(p -> p.test(price));
    }

}
