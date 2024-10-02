package com.eleven.access.core;

import com.cnetong.access.core.support.StringMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageManager {
    private final MessageRepository messageRepository;
    private final MessagePartitionDecider messagePartitionDecider;

    public MessageLog save(Message message) {
        var partition = messagePartitionDecider.partition(message);
        if (StringUtils.isBlank(partition)) {
            throw MessageErrors.NO_LOG_PARTITION.exception();
        }
        var messageId = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT);
        var messageDirection = MessageHelper.getDirection(message);
        var messageEndpoint = MessageHelper.getEndpoint(message);
        var messageLog = new MessageLog();
        messageLog.setMessageId(messageId);
        messageLog.setPartition(partition);
        messageLog.setTopic(message.getTopic());
        messageLog.setDirection(messageDirection);
        messageLog.setEndpointId(messageEndpoint);
        messageLog.error(message.getError());
        messageLog.setHeader(message.getHeader());
        messageLog.setBody(message.asString());
        messageRepository.save(messageLog);
        return messageLog;
    }

    public void update(MessageLog log) {
        messageRepository.update(log);
    }

    public Optional<MessageLog> get(String partition, String id) {
        return messageRepository.get(partition, id);
    }

    public void delete(String partition, String messageId) {
        messageRepository.delete(partition, messageId);
    }

    public Message restore(MessageLog log) {
        StringMessage stringMessage = new StringMessage(log.getBody());
        stringMessage.setTopic(log.getTopic());
        stringMessage.getHeader().putAll(log.getHeader());
        return stringMessage;
    }

}
