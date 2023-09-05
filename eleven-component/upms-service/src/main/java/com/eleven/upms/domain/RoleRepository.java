package com.eleven.upms.domain;

import com.eleven.core.domain.DomainRepository;

import java.util.Optional;

 interface RoleRepository extends DomainRepository<Role, String> {
    Optional<Role> findByCode(String code);

}
