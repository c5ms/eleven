package com.eleven.gateway.admin.endpoint;

import com.cnetong.common.web.RestApi;
import com.eleven.gateway.admin.domain.action.GateStackQueryAction;
import com.eleven.gateway.admin.domain.entity.GateRoute;
import com.eleven.gateway.admin.domain.entity.GateStack;
import com.eleven.gateway.admin.domain.service.GateRouteService;
import com.eleven.gateway.admin.domain.service.GateStackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Tag(name = "站点")
@RestApi
@RequestMapping("/gate/stacks")
@RequiredArgsConstructor
public class StackResourceV1 {

    private final GateStackService gateStackService;
    private final GateRouteService gateRouteService;

    @GetMapping
    @Operation(summary = "所有站点")
    public Collection<GateStack> listHosts(@ParameterObject GateStackQueryAction action) {
        return gateStackService.queryStack(action);
    }

    @GetMapping("/{id}/routes")
    @Operation(summary = "站点路由")
    public Collection<GateRoute> listRoutes(@PathVariable("id") GateStack gateStack) {
        return gateRouteService.listRouteByStackId(gateStack);
    }

    @Operation(summary = "删除站点")
    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable("id") String id) {
        gateStackService.deleteStack(id);
    }

    @Operation(summary = "保存站点")
    @PostMapping
    public GateStack saveHost(@RequestBody @Validated GateStack gateStack) {
        return gateStackService.saveStack(gateStack);
    }

    @Operation(summary = "读取站点")
    @GetMapping("/{id}")
    public Optional<GateStack> getService(@PathVariable("id") String id) {
        return gateStackService.findStack(id);
    }

    @Operation(summary = "发布站点")
    @PostMapping("/{id}/publish")
    public GateStack publish(@PathVariable("id") GateStack gateStack) {
        gateStackService.publish(gateStack);
        return gateStack;
    }

    @Operation(summary = "撤销站点")
    @PostMapping("/{id}/cancel")
    public GateStack cancel(@PathVariable("id") GateStack gateStack) {
        gateStackService.cancel(gateStack);
        return gateStack;
    }

}
