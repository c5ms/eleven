package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.domain.action.MessageRuleQueryAction;
import com.cnetong.access.admin.domain.entity.MessageRuleDefinition;
import com.cnetong.access.admin.service.MessageRuleService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestApi
@Tag(name = "消息规则")
@PreAuthorize("isAuthenticated()")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message/rules")
public class MessageRuleResourceV1 {
    private final MessageRuleService messageRuleService;

    @GetMapping("/queryRules")
    @Operation(summary = "列出规则")
    public Page<MessageRuleDefinition> queryRules(@ParameterObject MessageRuleQueryAction queryAction) {
        return messageRuleService.query(queryAction);
    }

    @Operation(summary = "删除规则")
    @DeleteMapping("/deleteRule")
    public void deleteRule(@RequestParam("id") String id) {
        messageRuleService.delete(messageRuleService.require(id));
    }

    @Operation(summary = "保存规则")
    @PostMapping("/saveRule")
    public MessageRuleDefinition saveRule(@RequestBody @Validated MessageRuleDefinition importation) {
        return messageRuleService.save(importation);
    }

    @Operation(summary = "读取规则")
    @GetMapping("/getRule")
    public Optional<MessageRuleDefinition> getRule(@RequestParam("id") String id) {
        return messageRuleService.get(id);
    }

    @Operation(summary = "发布路由")
    @PostMapping("/{id}/publish")
    public void publish(@PathVariable("id") MessageRuleDefinition messageRuleDefinition) {
        messageRuleService.publishRoute(messageRuleDefinition);
    }

    @Operation(summary = "撤销路由")
    @PostMapping("/{id}/cancel")
    public void unPublish(@PathVariable("id") MessageRuleDefinition messageRuleDefinition) {
        messageRuleService.cancelRoute(messageRuleDefinition);
    }


}
