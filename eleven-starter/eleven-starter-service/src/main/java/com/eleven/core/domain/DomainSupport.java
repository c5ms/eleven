package com.eleven.core.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DomainSupport {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;
    private final IdentityGenerator identityGenerator;

    /**
     * 分页查询数据
     *
     * @param query  查询限定对象
     * @param domain 领域对象
     * @param page   页码
     * @param size   页长
     * @param <T>    领域对象类型
     * @return 查询结果
     */
    public <T> PaginationResult<T> queryPage(Query query, Class<T> domain, int page, int size) {
        var pageable = Pageable.ofSize(size).withPage(page - 1);
        var elements = jdbcAggregateTemplate.findAll(query, domain, pageable);
        return PaginationResult.of(elements.getContent(), elements.getTotalElements());
    }

    /**
     * 查询数据
     *
     * @param query  查询限定对象
     * @param domain 领域对象
     * @param <T>    领域对象类型
     * @return 查询结果
     */
    public <T> List<T> query(Query query, Class<T> domain) {
        List<T> list= new ArrayList<>();
        jdbcAggregateTemplate.findAll(query, domain).forEach(list::add);
        return  list;
    }


    /**
     * 获取下一个 ID
     *
     * @return ID
     */
    public String nextId() {
        return identityGenerator.next();
    }

}
