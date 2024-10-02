package com.eleven.gateway.admin.domain.repository;

import com.eleven.gateway.admin.domain.entity.GateResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface GateResourceRepository extends JpaRepository<GateResource, String>, JpaSpecificationExecutor<GateResource> {

}
