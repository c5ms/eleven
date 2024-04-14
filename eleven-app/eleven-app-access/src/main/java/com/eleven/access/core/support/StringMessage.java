package com.eleven.access.core.support;

import com.cnetong.access.core.AbstractMessage;
import com.cnetong.access.core.MessageException;
import lombok.Getter;
import lombok.Setter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * 字符串消息
 *
 * @author wangzc
 */
@Getter
@Setter
public class StringMessage extends AbstractMessage {
    private static final long serialVersionUID = 1962743855047957251L;

    private final String body;
    private final String charset;

    public StringMessage(String body, String charset) {
        this.body = body;
        this.charset = charset;
    }

    public StringMessage(String body) {
        this.body = body;
        this.charset = StandardCharsets.UTF_8.name();
    }

    /**
     * 获取消息字节内容
     *
     * @return 自己内容
     */
    @Override
    public byte[] asBytes() {
        if (null == body) {
            return new byte[0];
        }
        try {
            return this.asString().getBytes(getCharset());
        } catch (UnsupportedEncodingException e) {
            throw new MessageException("无法将消息转换为字节数组,因为字符编码不能支持", e);
        }
    }


    @Override
    public String asString() {
        return this.body;
    }

}
