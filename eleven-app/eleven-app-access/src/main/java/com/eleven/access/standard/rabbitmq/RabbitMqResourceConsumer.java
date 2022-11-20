package com.eleven.access.standard.rabbitmq;

import com.cnetong.access.core.Message;
import com.cnetong.access.core.MessageChannel;
import com.cnetong.access.core.ResourceConsumer;
import com.rabbitmq.client.GetResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.HashMap;

@Slf4j
@Getter
public class RabbitMqResourceConsumer implements ResourceConsumer {

    private final String queue;
    private final int concurrentConsumers;
    private final ConnectionFactory connectionFactory;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;
    private AbstractMessageListenerContainer listenerContainer;

    @Builder
    public RabbitMqResourceConsumer(ConnectionFactory connectionFactory, String queue, int concurrentConsumers) {
        this.connectionFactory = connectionFactory;
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);
        this.rabbitTemplate = new RabbitTemplate();
        this.rabbitTemplate.setConnectionFactory(connectionFactory);
        this.rabbitTemplate.setChannelTransacted(false);
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.start();
        this.queue = queue;
        this.concurrentConsumers = concurrentConsumers;
    }

    @Override
    public void listen(MessageChannel processor) {
        this.rabbitAdmin.declareQueue(new Queue(getQueue(), true, false, false, new HashMap<>()));
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(getConnectionFactory());
        container.setQueueNames(getQueue());
        container.setConcurrentConsumers(getConcurrentConsumers());
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setExposeListenerChannel(true);
        container.setAutoDeclare(true);
        container.setMessageListener((ChannelAwareMessageListener) (delivery, channel) -> {
            org.springframework.util.Assert.notNull(channel, "通道是null，这是一个很严重的 BUG");
            Message message = new RabbitMqMessage(delivery.getBody(),
                    StringUtils.defaultString(delivery.getMessageProperties().getContentEncoding(), "UTF-8"),
                    channel,
                    delivery.getMessageProperties().getDeliveryTag());
            delivery.getMessageProperties().getHeaders().forEach((s, o) -> message.getHeader().put(s, o.toString()));
            processor.receive(message);
        });
        this.listenerContainer = container;
        this.listenerContainer.start();
    }

    @Override
    public boolean checkHealth() throws Exception {
        try (var conn = connectionFactory.createConnection()) {
            try (var channel = conn.createChannel(false)) {
                if (StringUtils.isNotBlank(getQueue())) {
                    GetResponse getResponse = channel.basicGet(getQueue(), false);
                    if (null != getResponse) {
                        channel.basicNack(getResponse.getEnvelope().getDeliveryTag(), false, true);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void destroy() {
        if (null != listenerContainer) {
            listenerContainer.stop();
            listenerContainer.destroy();
        }
    }


}
