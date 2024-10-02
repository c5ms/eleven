package com.eleven.gateway.management.endpoint;

import com.eleven.core.domain.PaginationResult;
import com.eleven.core.web.annonation.AsAdminApi;
import com.eleven.gateway.management.domain.RouteService;
import com.eleven.gateway.management.model.RouteCreateAction;
import com.eleven.gateway.management.model.RouteDto;
import com.eleven.gateway.management.model.RouteQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "routes")
@RequestMapping("/routes")
@AsAdminApi
@RequiredArgsConstructor
public class RoutesApiV1 {
    private final RouteService routeService;

    @GetMapping
    @Operation(summary = "routes list")
    public PaginationResult<RouteDto> listRoutes(@ParameterObject RouteQuery routeQuery) {
        return routeService.queryRoutePage(routeQuery);
    }

    @Operation(summary = "create route")
    @PostMapping
    public RouteDto createRoute(@RequestBody @Validated RouteCreateAction action) {
        return routeService.createRoute(action);
    }


}
