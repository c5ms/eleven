package com.eleven.access.api.message;

import com.cnetong.access.core.*;
import com.cnetong.access.core.openapi.MessageRequest;
import com.cnetong.access.core.openapi.MessageResponse;
import com.cnetong.access.core.support.BytesMessage;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;

@Tag(name = "message", description = "消息")
@RestApi
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageApiV1 {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "投递消息")
    public MessageResponse message(@RequestBody MessageRequest request) {
        try {
            var message = new BytesMessage(request.getBody().getBytes(StandardCharsets.UTF_8));
            message.setTopic(StringUtils.trim(request.getTopic()));
            message.getHeader().putAll(request.getHeaders());
            MessageHelper.markDirection(message, Message.Direction.IN);
            MessageHelper.markEndpoint(message, "rest-api");
            MessageLog log = messageService.handle(message);
            if (log.isErrored()) {
                return new MessageResponse().setError(log.getException()).setSuccess(false);
            }
            return new MessageResponse();
        } catch (MessageException e) {
            return new MessageResponse()
                    .setError(e.getError())
                    .setMessage(e.getMessage())
                    .setSuccess(false);
        } catch (Exception e) {
            return new MessageResponse()
                    .setError(MessageErrors.INTERNAL_ERROR.getError())
                    .setMessage(ExceptionUtils.getRootCauseMessage(e))
                    .setSuccess(false);
        }
    }


}
