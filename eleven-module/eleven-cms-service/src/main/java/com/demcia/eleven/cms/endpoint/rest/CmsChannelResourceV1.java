package com.demcia.eleven.cms.endpoint.rest;

import com.demcia.eleven.cms.core.action.CmsChannelCreateAction;
import com.demcia.eleven.cms.core.action.CmsChannelQueryAction;
import com.demcia.eleven.cms.core.action.CmsChannelUpdateAction;
import com.demcia.eleven.cms.core.dto.CmsChannelDto;
import com.demcia.eleven.cms.domain.convertor.CmsChannelConverter;
import com.demcia.eleven.cms.domain.entity.CmsChannel;
import com.demcia.eleven.cms.domain.service.CmsChannelService;
import com.demcia.eleven.core.rest.annonation.RestResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "栏目")
@RequestMapping("/cms/channels")
@RestResource
@RequiredArgsConstructor
public class CmsChannelResourceV1 {

    private final CmsChannelService cmsChannelService;
    private final CmsChannelConverter cmsChannelConverter;

    @Operation(summary = "栏目创建")
    @PostMapping
    public CmsChannelDto channelCreate(@RequestBody @Validated CmsChannelCreateAction action) {
        var channel = cmsChannelService.createChannel(action);
        return cmsChannelConverter.toDto(channel);
    }


    @Operation(summary = "栏目删除")
    @DeleteMapping("/{id}")
    public void channelDelete(@PathVariable("id") String id) {
        CmsChannel channel = cmsChannelService.requireChannel(id);
        cmsChannelService.deleteChannel(channel);
    }

    @Operation(summary = "栏目更新")
    @PostMapping("/{id}")
    public CmsChannelDto channelUpdate(@PathVariable("id") String id, @RequestBody @Validated CmsChannelUpdateAction action) {
        CmsChannel channel = cmsChannelService.requireChannel(id);
        cmsChannelService.updateChannel(channel, action);
        return cmsChannelConverter.toDto(channel);
    }

    @Operation(summary = "栏目列表")
    @GetMapping
    public List<CmsChannelDto> channelList(@ParameterObject @Validated CmsChannelQueryAction action) {
        return cmsChannelService.listChannels(action).stream().map(cmsChannelConverter::toDto).collect(Collectors.toList());
    }

}
