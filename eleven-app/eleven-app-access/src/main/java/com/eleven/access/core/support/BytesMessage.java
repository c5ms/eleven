package com.eleven.access.core.support;

import com.cnetong.access.core.AbstractMessage;
import com.cnetong.access.core.MessageException;
import lombok.Getter;

import java.io.UnsupportedEncodingException;

/**
 * 字节数组消息
 *
 * @author wangzc
 */
@Getter
public class BytesMessage extends AbstractMessage {

    private final byte[] bytes;
    private final String charset;

    public BytesMessage(byte[] bytes) {
        this(bytes, "UTF-8");
    }

    public BytesMessage(byte[] bytes, String charset) {
        this.bytes = bytes;
        this.charset = charset;
    }

    @Override
    public byte[] asBytes() {
        return bytes;
    }

    @Override
    public String asString() {
        try {
            return new String(this.asBytes(), getCharset());
        } catch (UnsupportedEncodingException e) {
            throw new MessageException("无法将消息字符数组转换为字符串,因为字符编码不支持", e);
        }
    }

}
