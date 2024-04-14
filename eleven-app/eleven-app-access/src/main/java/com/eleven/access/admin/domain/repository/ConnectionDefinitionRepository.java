package com.eleven.access.admin.domain.repository;

import com.cnetong.access.admin.domain.entity.ResourceDefinition;
import com.cnetong.common.domain.DomainRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConnectionDefinitionRepository extends DomainRepository<ResourceDefinition, String>, JpaSpecificationExecutor<ResourceDefinition> {

}
