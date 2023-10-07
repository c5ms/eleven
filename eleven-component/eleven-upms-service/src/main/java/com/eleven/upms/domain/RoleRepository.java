package com.eleven.upms.domain;

import com.eleven.core.domain.DomainRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RoleRepository extends DomainRepository<Role, String> {
    Optional<Role> findByCode(String code);

}
