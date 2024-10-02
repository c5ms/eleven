package com.eleven.access.standard.ibmmq;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.access.core.*;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.Map;

import static com.cnetong.access.core.ComponentSpecification.PropertyType.number;
import static com.cnetong.access.core.ComponentSpecification.PropertyType.string;
import static com.cnetong.access.core.ComponentSpecification.property;

@Service
public class IbmmqResourceFactory implements ResourceFactory {


    @Override
    public ComponentSpecification getSpecification() {
        return ComponentSpecification.of(Resource.class, "Ibmmq")
                .label("Ibmmq")
                .describe("Ibmmq 资源对接")
                .property(
                        property("host", string).withLabel("IP").required(true).withPlaceholder("127.0.0.1"),
                        property("ccsid", string).withLabel("ccsid").required(true).withDefault("819"),
                        property("channel", string).withLabel("channel").required(true).withDefault(""),
                        property("port", number).withLabel("端口").required(true).withDefault("1415"),
                        property("queueManager", number).withLabel("队列管理器").required(false).withDefault(""),
                        property("userid", number).withLabel("用户").required(false).withDefault(""),
                        property("password", string).withLabel("密码").required(false).withDefault(""),

                        // 生产者属性
                        property("producer", "queue", number).withLabel("队列").required(true).withDefault(""),

                        // 消费者属性
                        property("consumer", "queue", number).withLabel("队列").required(true).withDefault("")

                )

                .runtime();
    }

    @Override
    public Resource<ConnectionFactory> create(Map<String, String> config) throws ComponentConfigException {
        return new IbmmqResource(config);
    }

    @RequiredArgsConstructor
    public static class IbmmqResource implements Resource<ConnectionFactory> {

        private MQQueueConnectionFactory mqQueueConnectionFactory;

        public IbmmqResource(Map<String, String> config) {
            this.mqQueueConnectionFactory = new MQQueueConnectionFactory();
            this.config(config);
        }

        private void config(Map<String, String> config) {
            try {
                mqQueueConnectionFactory.setHostName(config.get("host"));
                mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
                mqQueueConnectionFactory.setCCSID(Integer.parseInt(config.get("ccsid")));
                mqQueueConnectionFactory.setChannel(config.get("channel"));
                mqQueueConnectionFactory.setPort(Integer.parseInt(config.get("port")));
                mqQueueConnectionFactory.setQueueManager(config.get("queueManager"));
                mqQueueConnectionFactory.setSyncpointAllGets(false);
                mqQueueConnectionFactory.setClientReconnectOptions(WMQConstants.WMQ_CLIENT_RECONNECT_AS_DEF);
                mqQueueConnectionFactory.setAppName(SpringUtil.getApplicationName());
                mqQueueConnectionFactory.setAsyncExceptions(WMQConstants.ASYNC_EXCEPTIONS_ALL);
                if (StringUtils.isNoneEmpty(config.get("userid"), config.get("password"))) {
                    mqQueueConnectionFactory.setStringProperty(WMQConstants.USERID, config.get("userid"));
                    mqQueueConnectionFactory.setStringProperty(WMQConstants.PASSWORD, config.get("password"));
                }
            } catch (JMSException e) {
                throw new ComponentConfigException(e);
            }
        }

        @Override
        public void check() throws Exception {
            mqQueueConnectionFactory.createConnection().close();
        }

        @Override
        public ConnectionFactory getActual() {
            return mqQueueConnectionFactory;
        }

        @Override
        public void update(Map<String, String> config) {
            this.mqQueueConnectionFactory.clear();
            this.mqQueueConnectionFactory = new MQQueueConnectionFactory();
            this.config(config);
        }

        @Override
        public void destroy() {

        }

        @Override
        public ResourceProducer createProducer(Map<String, String> config) {
            return new IbmmqResourceProducer(this.mqQueueConnectionFactory, config.get("queue"));
        }

        @Override
        public ResourceConsumer createConsumer(Map<String, String> config) {
            return new IbmmqResourceConsumer(this.mqQueueConnectionFactory, config.get("queue"));
        }
    }
}
