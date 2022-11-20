package com.eleven.access.standard.ibmmq;

import com.cnetong.access.core.MessageChannel;
import com.cnetong.access.core.ResourceConsumer;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

@RequiredArgsConstructor
@Slf4j
public class IbmmqResourceConsumer implements ResourceConsumer {
    private MQQueueConnectionFactory mqQueueConnectionFactory;
    private final String queue;
    private Connection connection;
    private MessageConsumer consumer;
    private boolean stop = false;

    public IbmmqResourceConsumer(MQQueueConnectionFactory mqQueueConnectionFactory, String queue) {
        this.queue = queue;
        this.mqQueueConnectionFactory = mqQueueConnectionFactory;
        try {
            checkHealth();
        } catch (Exception e) {
            throw new RuntimeException("IBM MQ启动监听失败：" + e.getMessage(), e);
        }
    }

    class Listener extends Thread {

        private final MessageChannel processor;

        public Listener(MessageChannel processor) {
            this.processor = processor;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    Message message = consumer.receive(500);
                    if (message != null) {
                        processor.receive(new IbmmqMessage(message));
                    }
                } catch (Exception e) {
                    log.error("读取队列数据异常：" + e.getMessage(), e);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ignored) {

                    }
                }
            }
        }
    }

    @Override
    public void listen(MessageChannel processor) {
        new Listener(processor).start();
    }

    @Override
    public boolean checkHealth() throws Exception {
        try (var connection = mqQueueConnectionFactory.createConnection()) {
            connection.start();
            try (var session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE)) {
                try (var browser = session.createBrowser(session.createQueue("queue:///" + queue))) {
                    browser.getQueue();
                }
            }
        }
        return true;
    }

    @Override
    public void destroy() {
        stop = true;
        try {
            if (consumer != null) {
                consumer.close();
            }
            if (connection != null) {
                connection.close();
            }
            consumer = null;
            connection = null;
        } catch (Exception ignored) {

        }
    }

}
