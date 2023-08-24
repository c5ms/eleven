package com.eleven.core.message;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageTemplate {

    public void send(String channel, String topic, Object message) {
        if (!(message instanceof String)) {
            message = JSONUtil.toJsonStr(message);
        }
        log.info("send message to [{}] channel with [{}] topic, message is:{}", channel, topic, message);
    }

}
