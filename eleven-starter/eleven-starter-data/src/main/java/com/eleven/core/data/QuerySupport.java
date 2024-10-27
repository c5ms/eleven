package com.eleven.core.data;

import com.eleven.core.application.model.PageQuery;
import com.eleven.core.application.model.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuerySupport {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    public <T> List<T> query(Class<T> domain, Criteria criteria) {
        List<T> list = new ArrayList<>();
        var query = Query.query(criteria);
        jdbcAggregateTemplate.findAll(query, domain).forEach(list::add);
        return list;
    }

    public <T> List<T> query(Class<T> domain, Criteria criteria,Sort sort) {
        List<T> list = new ArrayList<>();
        var query = Query.query(criteria).sort(sort);
        jdbcAggregateTemplate.findAll(query, domain).forEach(list::add);
        return list;
    }

    public <T> PageResult<T> query(Class<T> tClass, Criteria criteria,PageQuery pageQuery, Sort sort) {
        var page = toPageRequest(pageQuery).withSort(sort);
        var query = Query.query(criteria);
        var elements = jdbcAggregateTemplate.findAll(query, tClass, page);
        return PageResult.of(elements.getContent(), elements.getTotalElements());
    }


    public <T> PageResult<T> query(Class<T> tClass, Criteria criteria, PageQuery pageQuery) {
        var page = toPageRequest(pageQuery);
        var query = Query.query(criteria);
        var elements = jdbcAggregateTemplate.findAll(query, tClass, page);
        return PageResult.of(elements.getContent(), elements.getTotalElements());
    }


    private PageRequest toPageRequest(PageQuery pageQuery) {
        int page = pageQuery.getPage();
        int size = pageQuery.getSize();
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 50;
        }
        return PageRequest.ofSize(size).withPage(page - 1);
    }

}
