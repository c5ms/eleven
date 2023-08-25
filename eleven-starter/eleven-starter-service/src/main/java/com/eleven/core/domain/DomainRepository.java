package com.eleven.core.domain;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface DomainRepository<T, ID> extends CrudRepository<T, ID> {

    @Override
    @Nonnull
    Collection<T> findAll();

    @Override
    @Nonnull
    Collection<T> findAllById(@Nonnull Iterable<ID> ids);

}
