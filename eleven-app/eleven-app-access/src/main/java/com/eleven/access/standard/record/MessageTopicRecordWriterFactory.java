package com.eleven.access.standard.record;

import com.cnetong.access.core.*;
import com.cnetong.access.core.support.StringMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.cnetong.access.core.ComponentSpecification.PropertyType.message_topic;
import static com.cnetong.access.core.ComponentSpecification.property;

@Component
@RequiredArgsConstructor
public class MessageTopicRecordWriterFactory implements RecordWriterFactory {


    private final MessageService messageService;


    @Override
    public RecordWriter create(Map<String, String> config) throws ComponentConfigException {
        var topic = config.get("topic");
        if (StringUtils.isBlank(topic)) {
            throw new ComponentConfigException("消息发送需要一个主题");
        }
        return new MessageRecordWriter(messageService, topic);
    }

    @Override
    public ComponentSpecification getSpecification() {
        return ComponentSpecification.of(RecordWriter.class, "message_topic")
                .label("写入至消息主题")
                .describe("向指定消息主题写入")
                .property(
                        property("topic", message_topic).withLabel("主题").required(true)
                )
                .runtime(
                );
    }


    @RequiredArgsConstructor
    public static class MessageRecordWriter implements RecordWriter {
        private final MessageService messageService;
        private final String topic;

        @Override
        public void write(Record record) {
            var json = record.asJson();
            var message = new StringMessage(json);
            message.setTopic(topic);
            messageService.handle(message);
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() {

        }
    }

}
