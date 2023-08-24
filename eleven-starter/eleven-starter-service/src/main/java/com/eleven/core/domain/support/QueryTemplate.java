package com.eleven.core.domain.support;

import com.eleven.core.model.Pagination;
import com.eleven.core.model.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryTemplate {
    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    public <T> PaginationResult<T> findPage(Query query, Class<T> domainType, Pagination pagination) {
        var pageable = Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage() - 1);
        var elements = jdbcAggregateTemplate.findAll(query, domainType, pageable);
        return PaginationResult.of(elements.getContent(), elements.getTotalElements());
    }
}
