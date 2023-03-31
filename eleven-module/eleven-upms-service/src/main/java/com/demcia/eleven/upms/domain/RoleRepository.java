package com.demcia.eleven.upms.domain;

import com.demcia.eleven.upms.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, String> {

    Optional<Role> findByName(String name);

}