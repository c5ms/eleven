package com.eleven.access.standard.rabbitmq;

import com.cnetong.access.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.cnetong.access.core.ComponentSpecification.PropertyType.*;
import static com.cnetong.access.core.ComponentSpecification.property;

@Service
public class RabbitMQResourceFactory implements ResourceFactory {

    @Override
    public Resource<ConnectionFactory> create(Map<String, String> config) {
        return new RabbitMQResource(config);
    }

    @Override
    public ComponentSpecification getSpecification() {
        return ComponentSpecification.of(Resource.class, "rabbitMQ")
                .label("rabbitMQ")
                .describe("RabbitMQ 资源对接，消费端需要配置监听队列，生产端可投递至队列或者交换器")
                .property(
                        property("uri", string).withLabel("URI").required(true).withPlaceholder("amqp://localhost:5672"),
                        property("username", string).withLabel("用户名").required(true),
                        property("password", password).withLabel("密码").required(true),
                        property("connectionLimit", number).withLabel("最大连接数").required(true).withDefault("6"),
                        property("connectionTimeout", number).withLabel("超时时长").required(false).withDefault("60000"),
                        property("vhost", number).withLabel("vhost").required(false).withDefault("/"),

                        // 生产者属性
                        property("producer", "queue", string).withLabel("queue").required(true).withDefault(""),
                        property("producer", "exchange", string).withLabel("exchange").required(true).withDefault(""),

                        // 消费者属性
                        property("consumer", "queue", string).withLabel("队列").required(true).withDefault("")
                )

                .runtime();
    }

    @RequiredArgsConstructor
    public static class RabbitMQResource implements Resource<ConnectionFactory> {
        private final CachingConnectionFactory connectionFactory;

        public RabbitMQResource(Map<String, String> config) {
            this.connectionFactory = new CachingConnectionFactory();
            this.config(config);
        }

        @Override
        public ConnectionFactory getActual() {
            return connectionFactory;
        }

        private void config(Map<String, String> config) {
            this.connectionFactory.setUri(config.get("uri"));
            this.connectionFactory.setUsername(config.get("username"));
            this.connectionFactory.setPassword(config.get("password"));
            this.connectionFactory.setConnectionLimit(Integer.parseInt(config.get("connectionLimit")));
            this.connectionFactory.setConnectionTimeout(Integer.parseInt(config.get("connectionTimeout")));
            this.connectionFactory.setConnectionCacheSize(Integer.parseInt(config.getOrDefault("connectionCacheSize", "2")));
            this.connectionFactory.setVirtualHost(config.getOrDefault("vhost", "/"));
        }

        @Override
        public void update(Map<String, String> config) {
            this.config(config);
            this.connectionFactory.resetConnection();
        }

        public void check() {
            this.connectionFactory.createConnection().close();
        }

        @Override
        public void destroy() {
            connectionFactory.destroy();
        }

        @Override
        public ResourceProducer createProducer(Map<String, String> config) {
            return RabbitMqResourceProducer.builder()
                    .queue(config.get("queue"))
                    .exchange(config.get("exchange"))
                    .connectionFactory(connectionFactory)
                    .build();
        }

        @Override
        public ResourceConsumer createConsumer(Map<String, String> config) {
            return RabbitMqResourceConsumer.builder()
                    .queue(config.get("queue"))
                    .concurrentConsumers(Integer.parseInt(config.getOrDefault("concurrent", "1")))
                    .connectionFactory(connectionFactory)
                    .build();
        }

    }
}
