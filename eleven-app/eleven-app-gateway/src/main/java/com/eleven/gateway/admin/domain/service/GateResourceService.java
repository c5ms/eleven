package com.eleven.gateway.admin.domain.service;

import com.cnetong.common.query.domain.Page;
import com.eleven.gateway.admin.domain.action.GateResourceQueryAction;
import com.eleven.gateway.admin.domain.entity.GateResource;
import com.eleven.gateway.admin.domain.entity.GateResourcePack;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface GateResourceService {

    Page<GateResource> queryService(GateResourceQueryAction action);

    Optional<GateResource> findService(String id);

    GateResource saveService(GateResource serviceDefinition);

    void deleteResource(String id);

    Collection<GateResourcePack> listPacks(String id);

    GateResourcePack uploadPack(GateResource gateResource, Resource resource) throws IOException;

    void deletePack(String id);

    void releasePack(String id);
}
