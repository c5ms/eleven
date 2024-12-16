package com.eleven.domain.user;


import com.eleven.framework.data.DataFoundException;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RoleRepository extends ListCrudRepository<Role, String> {
    Optional<Role> findByCode(String code);


    default Role requireById(String id) {
        return findById(id).orElseThrow(DataFoundException::new);
    }
}
