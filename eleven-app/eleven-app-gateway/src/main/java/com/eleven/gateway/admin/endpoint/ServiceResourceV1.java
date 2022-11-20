package com.eleven.gateway.admin.endpoint;

import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.RestApi;
import com.eleven.gateway.admin.domain.action.GateServiceQueryAction;
import com.eleven.gateway.admin.domain.entity.GateService;
import com.eleven.gateway.admin.domain.service.GateServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "服务")
@RestApi
@RequestMapping("/gate/services")
@RequiredArgsConstructor
public class ServiceResourceV1 {

    private final GateServiceService serviceService;

    @GetMapping
    @Operation(summary = "查询服务")
    public Page<GateService> queryService(@ParameterObject GateServiceQueryAction action) {
        return serviceService.queryService(action);
    }

    @Operation(summary = "删除服务")
    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable("id") String id) {
        serviceService.deleteService(id);
    }

    @Operation(summary = "保存服务")
    @PostMapping
    public GateService createService(@RequestBody @Validated GateService gateService) {
        return serviceService.saveService(gateService);
    }

    @Operation(summary = "读取服务")
    @GetMapping("/{id}")
    public Optional<GateService> getService(@PathVariable("id") String id) {
        return serviceService.findService(id);
    }


}
