package com.eleven.gateway.admin.domain.repository;

import com.eleven.gateway.admin.domain.entity.GateService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GateServiceRepository extends JpaRepository<GateService, String>, JpaSpecificationExecutor<GateService> {

}
