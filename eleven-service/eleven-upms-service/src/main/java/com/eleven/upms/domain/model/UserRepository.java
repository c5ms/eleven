package com.eleven.upms.domain.model;

import com.eleven.core.data.DomainRepository;

import java.util.Optional;

public interface UserRepository extends DomainRepository<User, String> {

    Optional<User> findByUsername(String username);

}
