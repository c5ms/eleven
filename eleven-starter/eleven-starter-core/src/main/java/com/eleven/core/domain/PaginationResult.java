package com.eleven.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Accessors(chain = true)
@Schema(description = "page data model", name = "Page")
public class PaginationResult<T> implements Iterable<T> {

    private long total;
    private List<T> items;

    public static <T> PaginationResult<T> of(List<T> items, long total) {
        return new PaginationResult<T>()
                .setItems(items)
                .setTotal(total)
                ;
    }

    public static <T> PaginationResult<T> of(List<T> items) {
        return new PaginationResult<T>()
                .setItems(items)
                .setTotal(items.size())
                ;
    }

    public <R> PaginationResult<R> map(Function<T, R> mapper) {
        return new PaginationResult<R>()
                .setItems(this.getItems().stream().map(mapper).collect(Collectors.toList()))
                .setTotal(this.total)
                ;
    }

    public Stream<T> stream() {
        return this.items.stream();
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return this.items.iterator();
    }
}
