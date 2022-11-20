package com.eleven.gateway.admin.domain.service;

import com.cnetong.common.query.domain.Page;
import com.eleven.gateway.admin.domain.action.GateServiceQueryAction;
import com.eleven.gateway.admin.domain.entity.GateService;

import java.util.Collection;
import java.util.Optional;

public interface GateServiceService {

    Page<GateService> queryService(GateServiceQueryAction action);

    Optional<GateService> findService(String id);

    GateService saveService(GateService gateService);

    void deleteService(String id);

    Collection<GateService> listService();
}
