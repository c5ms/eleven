package com.eleven.access.core;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Topic {

    /**
     * 主题命名
     */
    private final String topic;


    /**
     * 消息规则
     */
    private final List<MessageRule> rules;

    @Builder
    public Topic(String topic,
                 List<MessageRule> rules) {
        this.topic = topic;
        this.rules = rules;
    }

    /**
     * 解码消息
     * TODO 消息解码机制
     *
     * @param message 消息
     * @return 解码后的消息
     */
    public Message decode(Message message) {
        return message;
    }


}
