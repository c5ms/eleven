package com.eleven.core.domain;

import com.eleven.core.exception.RequireDataNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface DomainRepository<T, ID> extends ListCrudRepository<T, ID> {

    default T requireById(ID id) {
        return findById(id).orElseThrow(RequireDataNotFoundException::new);
    }

    List<T> findAll(Sort sort);

}
