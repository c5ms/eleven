package com.eleven.framework.utils;

import jakarta.annotation.Nonnull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImmutableValues<T> implements Iterable<T> {

    private final List<T> values;

    public ImmutableValues(Collection<T> values) {
        this.values = Optional.ofNullable(values)
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
    }

    public static <T> ImmutableValues<T> of(Collection<T> collection) {
        return new ImmutableValues<>(collection);
    }

    public static <T> ImmutableValues<T> of(Set<T> set) {
        return new ImmutableValues<>(set);
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

    public boolean isNotEmpty() {
        return !values.isEmpty();
    }

    public T get(int index) {
        return values.get(index);
    }

    public void add(T t) {
        values.add(t);
    }

    public Set<T> toSet() {
        return new HashSet<>(values);
    }

    public List<T> toList() {
        return new ArrayList<>(values);
    }

    public <D> List<D> toList(Function<T,D> convertor) {
        return this.values.stream().map(convertor).toList();
    }

    public <D> Set<D> toSet(Function<T,D> convertor) {
        return this.values.stream().map(convertor).collect(Collectors.toSet());
    }
}
