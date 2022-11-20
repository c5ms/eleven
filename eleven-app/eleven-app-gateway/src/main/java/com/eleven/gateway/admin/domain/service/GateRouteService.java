package com.eleven.gateway.admin.domain.service;

import com.eleven.core.common.PaginationResult;
import com.eleven.gateway.admin.domain.action.GateRouteQueryAction;
import com.eleven.gateway.admin.domain.entity.GateRoute;
import com.eleven.gateway.admin.domain.entity.GateStack;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public interface GateRouteService {

    PaginationResult<GateRoute> queryRoute(GateRouteQueryAction action);

    Optional<GateRoute> findRoute(String id);

    GateRoute saveRoute(GateRoute gateRoute);

    void deleteRoute(String id);

    GateRoute require(String id);

    void publishRoute(GateRoute api);

    void cancelRoute(GateRoute gateRoute);

    Collection<GateRoute> listRouteByStackId(GateStack gateStack);

    Collection<GateRoute> listRoutes(GateRouteQueryAction action);

}
