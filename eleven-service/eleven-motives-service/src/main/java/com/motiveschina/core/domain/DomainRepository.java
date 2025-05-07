package com.motiveschina.core.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DomainRepository<D, ID> extends JpaRepository<D, ID>, JpaSpecificationExecutor<D> {
}
