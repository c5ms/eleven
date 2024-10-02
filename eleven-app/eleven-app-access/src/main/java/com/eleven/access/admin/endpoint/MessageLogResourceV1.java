package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.support.MessageQuery;
import com.cnetong.access.admin.support.MessageQueryFilter;
import com.cnetong.access.core.MessageLog;
import com.cnetong.access.core.MessageManager;
import com.cnetong.access.core.MessageService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.ProcessRejectException;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestApi
@Tag(name = "消息记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message/logs")
public class MessageLogResourceV1 {

    private final MessageManager messageManager;
    private final MessageService messageService;
    private final Optional<MessageQuery> messageQuery;

    @GetMapping("/{partition}/query")
    @Operation(summary = "查询消息")
    public Page<MessageLog> queryMessages(@PathVariable("partition") String partition, @ParameterObject @Validated MessageQueryFilter filter) {
        return messageQuery.map(query -> query.query(partition, filter)).orElse(new Page<>(null));
    }

    @GetMapping("/{partition}/{messageId}")
    @Operation(summary = "读取消息")
    public Optional<MessageLog> getLog(@PathVariable("partition") String partition, @PathVariable("messageId") String messageId) {
        return messageManager.get(partition, messageId);
    }

    @Operation(summary = "处理消息")
    @PostMapping("/{partition}/{messageId}")
    public MessageLog reProcess(@PathVariable("partition") String partition, @PathVariable("messageId") String messageId) {
        var log = messageManager.get(partition, messageId).orElseThrow(() -> ProcessRejectException.of("无此消息记录"));
        messageService.reHandle(log);
        return log;
    }

}
