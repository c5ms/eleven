package com.eleven.core.message;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
public class MessageSender {

    public void send(String channel, String topic, Serializable event) {
        String message;
        if (event instanceof String) {
            message = (String) event;
        } else {
            message = JSONUtil.toJsonStr(event);
        }
        log.info("send message to [{}] channel with [{}] topic, message is:{}", channel, topic,message);
    }


}
