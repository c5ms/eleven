package com.demcia.eleven.upms.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, String> {

    Optional<Role> findByName(String name);

}