package com.eleven.core.data;

import com.eleven.core.model.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuerySupport {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    public <T> PageResult<T> query(Query query, Class<T> domain, int page, int size) {
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 50;
        }
        var pageable = Pageable.ofSize(size).withPage(page - 1);
        return query(query, domain, pageable);
    }

    public <T> PageResult<T> query(Query query, Class<T> domain, Pageable pageable) {
        var elements = jdbcAggregateTemplate.findAll(query, domain, pageable);
        return PageResult.of(elements.getContent(), elements.getTotalElements());
    }

    public <T> List<T> query(Query query, Class<T> domain) {
        List<T> list = new ArrayList<>();
        jdbcAggregateTemplate.findAll(query, domain).forEach(list::add);
        return list;
    }

}
