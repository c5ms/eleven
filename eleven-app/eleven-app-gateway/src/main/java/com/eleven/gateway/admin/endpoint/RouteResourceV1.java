package com.eleven.gateway.admin.endpoint;

import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.RestApi;
import com.eleven.core.common.PaginationResult;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.gateway.admin.domain.action.GateRouteQueryAction;
import com.eleven.gateway.admin.domain.entity.GateRoute;
import com.eleven.gateway.admin.domain.service.GateRouteService;
import com.cnetong.logs.annonation.UseRequestLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Tag(name = "路由")
@AsRestApi
@RequestMapping("/gate/routes")
@RequiredArgsConstructor
public class RouteResourceV1 {

    private final GateRouteService gateRouteService;

    @GetMapping
    @Operation(summary = "查询路由")
    public PaginationResult<GateRoute> queryService(@ParameterObject GateRouteQueryAction action) {
        return gateRouteService.queryRoute(action);
    }

    @GetMapping("/list")
    @Operation(summary = "查询路由列表")
    public Collection<GateRoute> listRoutes(@ParameterObject GateRouteQueryAction action) {
        return gateRouteService.listRoutes(action);
    }

    @Operation(summary = "删除路由")
    @DeleteMapping("/{routeId}")
    public void deleteRoute(@PathVariable("routeId") GateRoute gateRoute) {
        gateRouteService.deleteRoute(gateRoute.getId());
    }

    @Operation(summary = "保存路由")
    @PostMapping
    public GateRoute saveRoute(@RequestBody @Validated GateRoute gateRoute) {
        return gateRouteService.saveRoute(gateRoute);
    }

    @Operation(summary = "读取路由")
    @GetMapping("/{routeId}")
    public Optional<GateRoute> getService(@PathVariable("routeId") String id) {
        return gateRouteService.findRoute(id);
    }

    @Operation(summary = "发布路由")
    @PostMapping("/{routeId}/publish")
    public GateRoute publish(@PathVariable("routeId") GateRoute gateRoute) {
        gateRouteService.publishRoute(gateRoute);
        return gateRoute;
    }

    @Operation(summary = "撤销路由")
    @PostMapping("/{routeId}/cancel")
    public GateRoute unPublish(@PathVariable("routeId") GateRoute gateRoute) {
        gateRouteService.cancelRoute(gateRoute);
        return gateRoute;
    }


}
