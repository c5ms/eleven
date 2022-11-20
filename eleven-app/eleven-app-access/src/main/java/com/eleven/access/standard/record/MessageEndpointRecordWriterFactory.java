package com.eleven.access.standard.record;

import com.cnetong.access.core.*;
import com.cnetong.access.core.Record;
import com.cnetong.access.core.support.StringMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.cnetong.access.core.ComponentSpecification.PropertyType.message_producer;
import static com.cnetong.access.core.ComponentSpecification.property;

@Component
@RequiredArgsConstructor
public class MessageEndpointRecordWriterFactory implements RecordWriterFactory {

    private final MessageService messageService;


    @Override
    public RecordWriter create(Map<String, String> config) throws ComponentConfigException {
        var endpoint = config.get("endpoint");
        if (StringUtils.isBlank(endpoint)) {
            throw new ComponentConfigException("消息发送需要一个目标终端");
        }
        return new MessageRecordWriter(messageService, endpoint);
    }

    @Override
    public ComponentSpecification getSpecification() {
        return ComponentSpecification.of(RecordWriter.class, "message_endpoint")
                .label("写入至消息终端")
                .describe("向指定消息发送通道写入消息")
                .property(
                        property("endpoint", message_producer).withLabel("终端").required(true)
                )
                .runtime();
    }


    @RequiredArgsConstructor
    public static class MessageRecordWriter implements RecordWriter {
        private final MessageService messageService;
        private final String endpoint;

        @Override
        public void write(Record record) throws Exception {
            var json = record.asJson();
            var message = new StringMessage(json);
            messageService.send(endpoint, message);
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() {

        }
    }

}
