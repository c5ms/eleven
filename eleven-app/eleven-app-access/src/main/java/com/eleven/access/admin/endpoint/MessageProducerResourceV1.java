package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.convertor.ProducerConvertor;
import com.cnetong.access.admin.domain.action.MessageProducerQueryAction;
import com.cnetong.access.admin.domain.dto.ProducerDto;
import com.cnetong.access.admin.domain.entity.MessageProducerDefinition;
import com.cnetong.access.admin.service.ProducerService;
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
@Tag(name = "发送通道")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message/producers")
public class MessageProducerResourceV1 {
    private final ProducerService producerService;
    private final ProducerConvertor endpointConvertor;

    @GetMapping("/queryProducer")
    @Operation(summary = "列出生产者")
    public Page<ProducerDto> queryProducer(@ParameterObject MessageProducerQueryAction queryAction) {
        return producerService.query(queryAction)
                .map(endpointConvertor::toDto);
    }

    @Operation(summary = "删除生产者")
    @DeleteMapping("/deleteProducer")
    public void deleteProducer(@RequestParam("id") String id) {
        producerService.delete(producerService.require(id));
    }

    @Operation(summary = "保存生产者")
    @PostMapping("/saveProducer")
    public MessageProducerDefinition saveProducer(@RequestBody @Validated MessageProducerDefinition importation) {
        return producerService.save(importation);
    }

    @Operation(summary = "获取生产者")
    @GetMapping("/getProducer")
    public Optional<MessageProducerDefinition> getProducer(@RequestParam("id") String id) {
        return producerService.get(id);
    }


    @Operation(summary = "生产者列表")
    @GetMapping("/list")
    public Collection<MessageProducerDefinition> getProducerList(@ParameterObject MessageProducerQueryAction queryAction) {
        return producerService.list(queryAction);
    }

    @PostMapping("/start")
    @Operation(summary = "开启生产者监听")
    public MessageProducerDefinition startListen(@RequestParam("id") String id) {
        MessageProducerDefinition messageProducerDefinition = producerService.require(id);
        producerService.start(messageProducerDefinition);
        return messageProducerDefinition;
    }

    @PostMapping("/stop")
    @Operation(summary = "关闭生产者监听")
    public MessageProducerDefinition stopListen(@RequestParam("id") String id) {
        MessageProducerDefinition messageProducerDefinition = producerService.require(id);
        producerService.stop(messageProducerDefinition);
        return messageProducerDefinition;
    }


}
