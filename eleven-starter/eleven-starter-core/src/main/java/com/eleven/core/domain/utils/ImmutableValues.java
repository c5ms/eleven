package com.eleven.core.domain.utils;

import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class ImmutableValues<T> implements Iterable<T> {

    private final List<T> values;

    public ImmutableValues(Collection<T> values) {
        this.values = new ArrayList<>(values);
    }

    public static <T> ImmutableValues<T> of(Collection<T> vs) {
        return new ImmutableValues<>(vs);
    }

    public static <T> ImmutableValues<T> empty(Class<T> ignored) {
        return new ImmutableValues<>(new ArrayList<>());
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return values.iterator();
    }

    public Stream<T> stream() {
        return values.stream();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public T get(int index) {
        return values.get(index);
    }

    public void add(T t) {
        values.add(t);
    }

}
