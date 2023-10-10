package com.eleven.core.domain;

import com.eleven.core.exception.DataNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface DomainRepository<T, ID> extends ListCrudRepository<T, ID> {

    default T requireById(ID id) {
        return findById(id).orElseThrow(DataNotFoundException::new);
    }

    List<T> findAll(Sort sort);

}
