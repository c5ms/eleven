package com.eleven.access.core.support;

import com.cnetong.access.core.Message;
import com.cnetong.access.core.MessageErrorHandler;
import com.cnetong.access.core.MessageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultMessageErrorHandler implements MessageErrorHandler {
    @Override
    public boolean onError(Exception e, Message message, MessageService messageService) {
        log.error("消息处理失败", e);
        return true;
    }
}
