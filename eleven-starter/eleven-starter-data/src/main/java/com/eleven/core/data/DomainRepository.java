package com.eleven.core.data;

import com.eleven.core.domain.NoRequiredEntityException;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface DomainRepository<T, ID> extends ListCrudRepository<T, ID> {

    default T requireById(ID id) {
        return findById(id).orElseThrow(NoRequiredEntityException::new);
    }

    List<T> findAll(Sort sort);

}
