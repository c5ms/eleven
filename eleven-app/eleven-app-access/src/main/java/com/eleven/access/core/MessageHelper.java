package com.eleven.access.core;

import com.cnetong.access.core.support.BytesMessage;
import org.apache.commons.lang3.StringUtils;

public abstract class MessageHelper {

    public static final String HEADER_DIRECTION = "direction";
    public static final String HEADER_ENDPOINT = "endpoint";

    public static void markDirection(Message message, Message.Direction direction) {
        setHeader(message, HEADER_DIRECTION, direction.toString());
    }

    public static Message.Direction getDirection(Message message) {
        String direction = (String) getHeader(message, HEADER_DIRECTION);
        return Message.Direction.valueOf(direction);
    }

    public static void markEndpoint(Message message, String endpoint) {
        setHeader(message, HEADER_ENDPOINT, endpoint);
    }

    public static String getEndpoint(Message message) {
        String endpoint = (String) getHeader(message, HEADER_ENDPOINT);
        return StringUtils.defaultString(endpoint, "unknown");
    }

    public static AbstractMessage copy(Message message) {
        var newMessage = new BytesMessage(message.asBytes(), message.getCharset());
        newMessage.setTopic(message.getTopic());
        newMessage.getHeader().putAll(message.getHeader());
        return newMessage;
    }

    public static void copyHead(Message source, Message dist) {
        dist.getHeader().putAll(source.getHeader());
        if (dist instanceof AbstractMessage) {
            ((AbstractMessage) dist).setTopic(source.getTopic());
        }
    }


    public static void setHeader(Message message, String name, String value) {
        message.getHeader().put(name, value);
    }

    public static Object getHeader(Message message, String name) {
        return message.getHeader().get(name);
    }
}
