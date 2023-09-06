package com.eleven.upms.domain;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, String> {

    Optional<User> findByUsername(String username);

}
