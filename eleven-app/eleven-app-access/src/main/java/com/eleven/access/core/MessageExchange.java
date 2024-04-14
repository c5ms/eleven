package com.eleven.access.core;

import com.cnetong.access.core.support.BytesMessage;
import com.cnetong.access.core.support.StringMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class MessageExchange {
    private final Message in;
    private final Map<String, Object> attributes = new HashMap<>();
    private Message out;

    @Builder
    public MessageExchange(Message in) {
        this.in = in;
        this.out = in;
    }


    public void out(String body) {
        this.out = new StringMessage(body);
        MessageHelper.copyHead(this.in, this.out);
    }

    public void out(byte[] body) {
        this.out = new BytesMessage(body);
        MessageHelper.copyHead(this.in, this.out);
    }


    @Nullable
    public Object getAttribute(String name) {
        return this.getAttributes().get(name);
    }

    public Object setAttribute(String name, Object value) {
        return this.getAttributes().put(name, value);
    }

    public Object getRequiredAttribute(String name) {
        Object value = this.getAttribute(name);
        Assert.notNull(value, () -> "Required attribute '" + name + "' is missing");
        return value;
    }

    public Object getAttributeOrDefault(String name, Object defaultValue) {
        return this.getAttributes().getOrDefault(name, defaultValue);
    }
}
