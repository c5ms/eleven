package com.eleven.access.core;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final TopicContext topicContainer;
    private final MessageManager messageManager;
    private final ResourceProducerContext resourceProducerContext;

    /**
     * 接收消息
     *
     * @param message 消息
     * @return 消息日志
     */
    public MessageLog handle(Message message) {
        var topic = requireTopic(message);
        try {
            this.doHandleInternal(topic, message);
            MessageHelper.markDirection(message, Message.Direction.IN);
        } catch (Exception e) {
            message.setError(e);
        }
        return messageManager.save(message);
    }

    /**
     * 重新处理历史记录的消息
     *
     * @param log 消息历史
     */
    public void reHandle(MessageLog log) {
        var message = messageManager.restore(log);
        var topic = requireTopic(message);
        // 输入，重新处理
        if (Message.Direction.IN == log.getDirection()) {
            try {
                this.doHandleInternal(topic, message);
                log.success();
            } catch (Throwable e) {
                log.error(e);
            }
            messageManager.update(log);
        }

        // 输出，重新发送
        if (Message.Direction.OUT == log.getDirection()) {
            try {
                send(log.getEndpointId(), message);
                log.success();
            } catch (Throwable e) {
                log.error(e);
            }
            messageManager.update(log);
        }

    }

    /**
     * 发送消息
     *
     * @param destination 目标(终端 ID)
     * @param message     消息
     * @throws Exception -
     */
    public void send(String destination, Message message) throws Exception {
        MessageHelper.markDirection(message, Message.Direction.OUT);
        var producer = resourceProducerContext.getProducer(destination);
        producer.produce(message);
    }

    private Topic requireTopic(Message message) {
        var topicOpt = topicContainer.getTopic(message.getTopic());
        if (topicOpt.isEmpty()) {
            throw MessageErrors.NO_SUCH_TOPIC.exception();
        }
        return topicOpt.get();
    }


    private void doHandleInternal(Topic topic, Message message) {
        var decoded = topic.decode(message);

        for (MessageRule rule : topic.getRules()) {

            var replica = MessageHelper.copy(decoded);
            var exchange = new MessageExchange(replica);

            if (rule.match(exchange)) {
                var endpointId = rule.getProducer();

                if (StringUtils.isNotBlank(endpointId)) {

                    //处理路由携带的过滤器
                    var chain = new MessageProcessorChain(rule.getProcessors());
                    chain.doFilter(exchange);
                    var out = exchange.getOut();

                    if (null != out) {
                        try {
                            MessageHelper.markEndpoint(out, endpointId);
                            send(endpointId, out);
                        } catch (Exception e) {
                            out.setError(e);
                        }
                        messageManager.save(out);
                    }
                }
            }
        }
    }

}
