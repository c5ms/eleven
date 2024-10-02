package com.eleven.access.standard.rabbitmq;

import com.cnetong.access.core.MessageException;
import com.cnetong.access.core.support.TransactionalMessage;
import com.rabbitmq.client.Channel;

import java.io.UnsupportedEncodingException;

/**
 * @author wangzc
 */
public class RabbitMqMessage extends TransactionalMessage {

    private final byte[] bytes;
    private final String charset;
    private final Channel channel;
    private final long deliveryTag;

    public RabbitMqMessage(byte[] bytes, String charset, Channel channel, long deliveryTag) {
        this.channel = channel;
        this.charset = charset;
        this.deliveryTag = deliveryTag;
        this.bytes = bytes;
    }

    @Override
    public void acknowledge() throws MessageException {
        try {
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            throw new MessageException("rabbitMq message acknowledge failure", e);
        }
    }

    @Override
    public void reject() throws MessageException {
        try {
            channel.basicNack(deliveryTag, false, true);
        } catch (Exception e) {
            throw new MessageException("rabbitMq message reject failure", e);
        }
    }


    @Override
    public String asString() throws MessageException {
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new MessageException("rabbitMq message reject failure", e);
        }
    }

    @Override
    public byte[] asBytes() throws MessageException {
        return bytes;
    }

    @Override
    public String getCharset() {
        return charset;
    }
}
