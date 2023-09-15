package com.eleven.upms.domain;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RoleRepository extends ListCrudRepository<Role, String> {
    Optional<Role> findByCode(String code);

}
