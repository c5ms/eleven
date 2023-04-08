package com.eleven.core.query;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class QueryResult<T> {

    private QueryResultMate mate;

    private List<T> items;

    public static <T> QueryResult<T> of(List<T> items, long total) {
        return new QueryResult<T>()
                .setItems(items)
                .setMate(new QueryResultMate().setTotal(total))
                ;
    }

    public static <T> QueryResult<T> of(List<T> items) {
        return new QueryResult<T>()
                .setItems(items)
                .setMate(new QueryResultMate().setTotal(items.size()))
                ;
    }

    public QueryResult<T> withPagination(Pagination pagination) {
        this.mate.setPage(pagination.getPage());
        this.mate.setSize(pagination.getSize());
        return this;
    }

    public QueryResult<T> withPage(Integer page) {
        this.mate.setPage(page);
        return this;
    }

    public QueryResult<T> withSize(Integer page) {
        this.mate.setSize(page);
        return this;
    }

    public <R> QueryResult<R> map(Function<T, R> mapper) {
        return new QueryResult<R>()
                .setItems(this.getItems().stream().map(mapper).collect(Collectors.toList()))
                .setMate(this.getMate());
    }

    @Data
    @Accessors(chain = true)
    public static class QueryResultMate {
        private long total;
        private long page;
        private long size;
    }


}
