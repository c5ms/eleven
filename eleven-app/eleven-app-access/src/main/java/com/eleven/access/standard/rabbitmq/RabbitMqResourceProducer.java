package com.eleven.access.standard.rabbitmq;

import com.cnetong.access.core.AbstractResourceProducer;
import com.cnetong.access.core.ComponentConfigException;
import com.cnetong.access.core.Message;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;

@Getter
public class RabbitMqResourceProducer extends AbstractResourceProducer {
    private final String queue;
    private final String exchange;
    private final ConnectionFactory connectionFactory;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    @Builder
    public RabbitMqResourceProducer(ConnectionFactory connectionFactory, String queue, String exchange) {
        this.connectionFactory = connectionFactory;
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);
        this.rabbitTemplate = new RabbitTemplate();
        this.rabbitTemplate.setConnectionFactory(connectionFactory);
        this.rabbitTemplate.setChannelTransacted(false);
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.start();
        this.queue = queue;
        this.exchange = exchange;
    }

    @Override
    public void produce(Message message) {
        if (StringUtils.isBlank(getQueue())) {
            throw new ComponentConfigException("消息队列不可为空，无法写出消息");
        }
        var msg = new org.springframework.amqp.core.Message(message.asBytes());
        msg.getMessageProperties().setContentType("text/plain");
        msg.getMessageProperties().setContentEncoding("UTF-8");
        rabbitTemplate.send(getExchange(), getQueue(), msg);
    }

    @Override
    public void check() {
        if (null == this.rabbitAdmin.getQueueProperties(getQueue())) {
            this.rabbitAdmin.declareQueue(new Queue(getQueue(), true, false, false, new HashMap<>()));
        }
    }


    @Override
    public void doClose() {
        if (null != this.rabbitTemplate) {
            this.rabbitTemplate.stop();
        }
    }

}
