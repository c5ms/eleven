package com.eleven.gateway.admin.endpoint;

import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.RestApi;
import com.eleven.gateway.admin.domain.action.GateResourceQueryAction;
import com.eleven.gateway.admin.domain.entity.GateResource;
import com.eleven.gateway.admin.domain.entity.GateResourcePack;
import com.eleven.gateway.admin.domain.service.GateResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Tag(name = "资源")
@RestApi
@RequestMapping("/gate/")
@RequiredArgsConstructor
public class ResourceResourceV1 {

    private final GateResourceService gateResourceService;

    @GetMapping("/resources")
    @Operation(summary = "查询资源")
    public Page<GateResource> queryService(@ParameterObject GateResourceQueryAction action) {
        return gateResourceService.queryService(action);
    }

    @Operation(summary = "删除资源")
    @DeleteMapping("/resources/{id}")
    public void deleteService(@PathVariable("id") String id) {
        gateResourceService.deleteResource(id);
    }

    @Operation(summary = "保存资源")
    @PostMapping("/resources")
    public GateResource createService(@RequestBody @Validated GateResource GateResource) {
        return gateResourceService.saveService(GateResource);
    }

    @Operation(summary = "读取资源")
    @GetMapping("/resources/{id}")
    public Optional<GateResource> getService(@PathVariable("id") String id) {
        return gateResourceService.findService(id);
    }


    @Operation(summary = "读取资源包列表")
    @GetMapping("/resources/{id}/packs")
    public Collection<GateResourcePack> getPacks(@PathVariable("id") String id) {
        return gateResourceService.listPacks(id);
    }

    @Operation(summary = "上传资源包")
    @PostMapping("/resources/{id}/packs")
    public GateResourcePack uploadPack(@PathVariable("id") GateResource gateResource, @RequestParam("pack") MultipartFile multipartFile) throws IOException {
        return gateResourceService.uploadPack(gateResource, multipartFile.getResource());
    }

    @Operation(summary = "删除资源包")
    @DeleteMapping("/resource_packs/{id}")
    public void deletePack(@PathVariable("id") String id) {
        gateResourceService.deletePack(id);
    }

    @Operation(summary = "发布资源包")
    @PostMapping("/resource_packs/{id}/release")
    public void releasePack(@PathVariable("id") String id) {
        gateResourceService.releasePack(id);
    }


}
