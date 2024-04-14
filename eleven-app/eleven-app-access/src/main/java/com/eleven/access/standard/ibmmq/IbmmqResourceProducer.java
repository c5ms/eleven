package com.eleven.access.standard.ibmmq;

import cn.hutool.core.io.IoUtil;
import com.cnetong.access.core.AbstractResourceProducer;
import com.cnetong.access.core.Message;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import lombok.Getter;

import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

@Getter
public class IbmmqResourceProducer extends AbstractResourceProducer {

    private final String queue;


    private final MQQueueConnectionFactory mqQueueConnectionFactory;
    private final JMSContext jmsContext;
    private final Queue destination;
    private final JMSProducer jmsProducer;

    public IbmmqResourceProducer(MQQueueConnectionFactory mqQueueConnectionFactory, String queue) {
        this.queue = queue;
        this.mqQueueConnectionFactory = mqQueueConnectionFactory;
        this.jmsContext = mqQueueConnectionFactory.createContext();
        this.destination = jmsContext.createQueue("queue:///" + getQueue());
        this.jmsProducer = jmsContext.createProducer();
        this.jmsContext.start();
    }

    @Override
    public void check() throws Exception {
        // I have nothing to do here ...
        var queue = this.jmsContext.createQueue("queue:///" + getQueue());
        var browser = this.jmsContext.createBrowser(queue);
        browser.getQueue();
        browser.close();
    }

    @Override
    public void produce(Message message) throws Exception {
        final BytesMessage bytesMessage = this.jmsContext.createBytesMessage();
        bytesMessage.writeBytes(message.asBytes());
        this.jmsProducer.send(destination, bytesMessage);
    }

    @Override
    public void doClose() {
        IoUtil.closeIfPosible(jmsProducer);
        IoUtil.closeIfPosible(destination);
        IoUtil.closeIfPosible(jmsContext);
    }

}
