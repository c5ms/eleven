package com.eleven.upms.domain;

import com.eleven.core.domain.DomainRepository;

import java.util.Optional;

public interface UserRepository extends DomainRepository<User, String> {

    Optional<User> findByUsername(String username);

}
