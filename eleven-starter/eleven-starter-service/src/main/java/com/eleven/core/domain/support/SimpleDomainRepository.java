package com.eleven.core.domain.support;

import com.eleven.core.domain.DomainRepository;
import jakarta.annotation.Nonnull;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.repository.support.SimpleJdbcRepository;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Transactional
public class SimpleDomainRepository<T, ID> extends SimpleJdbcRepository<T, ID> implements DomainRepository<T, ID> {

    public SimpleDomainRepository(JdbcAggregateOperations entityOperations,
                                  PersistentEntity entity,
                                  JdbcConverter converter) {
        super(entityOperations, entity, converter);

    }

    @Override
    @Nonnull
    public Collection<T> findAll() {
        var all = super.findAll();
        var list = new ArrayList<T>();
        for (T t : all) {
            list.add(t);
        }
        return list;
    }

    @Override
    @Nonnull
    public Collection<T> findAllById(@Nonnull Iterable<ID> ids) {
        var all = super.findAllById(ids);
        var list = new ArrayList<T>();
        for (T t : all) {
            list.add(t);
        }
        return list;
    }
}
