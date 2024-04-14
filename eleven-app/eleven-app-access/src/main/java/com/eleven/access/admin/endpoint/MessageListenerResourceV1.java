package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.convertor.ListenerConvertor;
import com.cnetong.access.admin.domain.action.MessageListenerQueryAction;
import com.cnetong.access.admin.domain.dto.ListenerDto;
import com.cnetong.access.admin.domain.entity.MessageListenerDefinition;
import com.cnetong.access.admin.service.ListenerService;
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
@Tag(name = "接收通道")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message/listeners")
public class MessageListenerResourceV1 {
    private final ListenerService producerService;
    private final ListenerConvertor endpointConvertor;

    @GetMapping("/queryListener")
    @Operation(summary = "列出监听器")
    public Page<ListenerDto> queryListener(@ParameterObject MessageListenerQueryAction queryAction) {
        return producerService.query(queryAction)
                .map(endpointConvertor::toDto);
    }

    @Operation(summary = "删除监听器")
    @DeleteMapping("/deleteListener")
    public void deleteListener(@RequestParam("id") String id) {
        producerService.delete(producerService.require(id));
    }

    @Operation(summary = "保存监听器")
    @PostMapping("/saveListener")
    public MessageListenerDefinition saveListener(@RequestBody @Validated MessageListenerDefinition importation) {
        return producerService.save(importation);
    }

    @Operation(summary = "获取监听器")
    @GetMapping("/getListener")
    public Optional<MessageListenerDefinition> getListener(@RequestParam("id") String id) {
        return producerService.get(id);
    }


    @Operation(summary = "监听器列表")
    @GetMapping("/list")
    public Collection<MessageListenerDefinition> getListenerList(@ParameterObject MessageListenerQueryAction queryAction) {
        return producerService.list(queryAction);
    }

}
