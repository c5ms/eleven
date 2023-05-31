package com.eleven.core.query;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class QueryResult<T> {

    private long total;
    private List<T> items;

    public static <T> QueryResult<T> of(List<T> items, long total) {
        return new QueryResult<T>()
                .setItems(items)
                .setTotal(total)
                ;
    }

    public static <T> QueryResult<T> of(List<T> items) {
        return new QueryResult<T>()
                .setItems(items)
                .setTotal(items.size())
                ;
    }

    public <R> QueryResult<R> map(Function<T, R> mapper) {
        return new QueryResult<R>()
                .setItems(this.getItems().stream().map(mapper).collect(Collectors.toList()))
                .setTotal(this.total);
    }


}
