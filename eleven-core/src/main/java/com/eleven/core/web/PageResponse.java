package com.eleven.core.web;

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
@Schema(description = "page data model", name = "PageResult")
public class PageResponse<T> implements Iterable<T> {

    private long total;
    private List<T> items;

    public static <T> PageResponse<T> empty() {
        return new PageResponse<T>();
    }

    public static <T> PageResponse<T> of(List<T> items, long total) {
        return new PageResponse<T>()
                .setItems(items)
                .setTotal(total)
                ;
    }

    public static <T> PageResponse<T> of(List<T> items) {
        return new PageResponse<T>()
                .setItems(items)
                .setTotal(items.size())
                ;
    }

    public <R> PageResponse<R> map(Function<T, R> mapper) {
        return new PageResponse<R>()
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

    public PageResponse<T> withItems(List<T> items) {
        return PageResponse.of(items, this.total);
    }
}
