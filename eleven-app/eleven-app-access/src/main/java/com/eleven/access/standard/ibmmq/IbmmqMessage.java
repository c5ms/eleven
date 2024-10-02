package com.eleven.access.standard.ibmmq;

import com.cnetong.access.core.MessageException;
import com.cnetong.access.core.support.TransactionalMessage;
import lombok.extern.slf4j.Slf4j;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class IbmmqMessage extends TransactionalMessage {

    private final Message message;

    public IbmmqMessage(Message message) {
        this.message = message;
    }

    @Override
    public String getCharset() {
        return StandardCharsets.UTF_8.name();
    }

    @Override
    public String asString() throws MessageException {
        try {
            return ((TextMessage) message).getText();
        } catch (JMSException e) {
            throw new MessageException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] asBytes() throws MessageException {
        try {
            return asString().getBytes(getCharset());
        } catch (UnsupportedEncodingException e) {
            throw new MessageException(e.getMessage(), e);
        }
    }

    @Override
    public void acknowledge() throws MessageException {
        try {
            message.acknowledge();
        } catch (Exception e) {
            log.warn("IBM MQ 消息确认失败：" + e.getMessage(), e);
        }
    }

    @Override
    public void reject() throws MessageException {
    }
}
