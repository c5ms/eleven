package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.convertor.ConnectionConvertor;
import com.cnetong.access.admin.domain.action.ConnectionDefinitionQueryAction;
import com.cnetong.access.admin.domain.dto.ConnectionDto;
import com.cnetong.access.admin.domain.entity.ResourceDefinition;
import com.cnetong.access.admin.service.ConnectionService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestApi
@Tag(name = "连接服务")
@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceResourceV1 {
    private final ConnectionService connectionService;
    private final ConnectionConvertor connectionConvertor;

    @GetMapping("/query")
    @Operation(summary = "查询连接")
    public Page<ConnectionDto> queryEndpoints(@ParameterObject ConnectionDefinitionQueryAction queryAction) {
        return connectionService.query(queryAction)
                .map(connectionConvertor::toDto);
    }

    @Operation(summary = "删除连接")
    @DeleteMapping("/delete")
    public void deleteEndpoint(@RequestParam("id") String id) {
        connectionService.delete(connectionService.require(id));
    }

    @Operation(summary = "保存连接")
    @PostMapping("/save")
    public ResourceDefinition saveEndpoint(@RequestBody @Validated ResourceDefinition importation) {
        return connectionService.save(importation);
    }

    @Operation(summary = "获取连接")
    @GetMapping("/get")
    public Optional<ResourceDefinition> getEndpoint(@RequestParam("id") String id) {
        return connectionService.get(id);
    }

    @Operation(summary = "连接列表")
    @GetMapping("/list")
    public Collection<ResourceDefinition> getEndpointList(@ParameterObject ConnectionDefinitionQueryAction queryAction) {
        return connectionService.list(queryAction);
    }


}
