package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.action.ConnectionDefinitionQueryAction;
import com.cnetong.access.admin.domain.entity.ResourceDefinition;
import com.cnetong.common.query.domain.Page;

import java.util.Collection;
import java.util.Optional;

public interface ConnectionService {

    ResourceDefinition require(String id);

    Optional<ResourceDefinition> get(String id);

    ResourceDefinition save(ResourceDefinition resourceDefinition);

    void update(ResourceDefinition resourceDefinition);

    Page<ResourceDefinition> query(ConnectionDefinitionQueryAction action);

    void delete(ResourceDefinition resourceDefinition);

    Collection<ResourceDefinition> list();

    Collection<ResourceDefinition> list(ConnectionDefinitionQueryAction action);

}
