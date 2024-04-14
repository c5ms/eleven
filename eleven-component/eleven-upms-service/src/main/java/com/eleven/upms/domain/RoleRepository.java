package com.eleven.upms.domain;

import com.eleven.core.data.DomainRepository;

import java.util.Optional;

public interface RoleRepository extends DomainRepository<Role, String> {
    Optional<Role> findByCode(String code);

}
