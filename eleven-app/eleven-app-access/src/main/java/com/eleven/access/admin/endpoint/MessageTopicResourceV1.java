package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.domain.action.TopicDefinitionQueryAction;
import com.cnetong.access.admin.domain.entity.MessageTopicDefinition;
import com.cnetong.access.admin.service.impl.DefaultTopicService;
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
@Tag(name = "消息主题")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message/topics")
public class MessageTopicResourceV1 {
    private final DefaultTopicService defaultTopicService;

    @GetMapping("/list")
    @Operation(summary = "列出主题")
    public Collection<MessageTopicDefinition> listTopics(@ParameterObject TopicDefinitionQueryAction queryAction) {
        return defaultTopicService.list(queryAction);
    }

    @Operation(summary = "删除主题")
    @DeleteMapping("/delete")
    public void deleteTopic(@RequestParam("id") String id) {
        defaultTopicService.delete(id);
    }

    @Operation(summary = "保存主题")
    @PostMapping("/save")
    public MessageTopicDefinition saveTopic(@RequestBody @Validated MessageTopicDefinition importation) {
        return defaultTopicService.save(importation);
    }

    @Operation(summary = "获取主题")
    @GetMapping("/get")
    public Optional<MessageTopicDefinition> getTopic(@RequestParam("id") String id) {
        return defaultTopicService.find(id);
    }

}
