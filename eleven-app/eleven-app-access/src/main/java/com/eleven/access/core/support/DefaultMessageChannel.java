package com.eleven.access.core.support;


import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.RejectPolicy;
import cn.hutool.core.thread.ThreadUtil;
import com.cnetong.access.core.*;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Slf4j
@Getter
public class DefaultMessageChannel implements MessageChannel {

    private final String id;
    private final String topic;
    private final ExecutorService executor;
    private final MessageService processor;
    private final MessageErrorHandler messageErrorHandler;
    private final Map<String, String> headers;

    private volatile boolean isRunning = false;

    @Builder
    public DefaultMessageChannel(String id,
                                 String topic,
                                 int threads,
                                 MessageService processor,
                                 MessageErrorHandler errorHandler,
                                 Map<String, String> headers) {

        this.id = id;
        this.topic = topic;
        this.processor = processor;
        this.messageErrorHandler = Optional.ofNullable(errorHandler).orElseGet(DefaultMessageErrorHandler::new);
        this.headers = headers;

        this.executor = ExecutorBuilder.create()
                .setThreadFactory(ThreadUtil.createThreadFactory("consumer-"))
                .setCorePoolSize(threads)
                .setMaxPoolSize(threads)
                .useSynchronousQueue(false)
                .setHandler(RejectPolicy.BLOCK.getValue())
                .buildFinalizable();

    }

    private static void tryAck(Message message) {
        if (message instanceof TransactionalMessage) {
            ((TransactionalMessage) message).acknowledge();
        }
    }

    private static void tryReject(Message message) {
        if (message instanceof TransactionalMessage) {
            ((TransactionalMessage) message).reject();
        }
    }


    public void start() {
        isRunning = true;
    }

    public void stop() {
        this.isRunning = false;
        this.executor.shutdownNow();
    }

    private boolean isRunning() {
        return this.isRunning;
    }

    /*  =========================== 接收消息 =========================== */

    @Override
    public void receive(Message message) {
        message.getHeader().putAll(headers);
        MessageHelper.markEndpoint(message, id);
        MessageHelper.markDirection(message, Message.Direction.IN);
        this.executor.submit(() -> doReceiveMessage(message));
    }


    private void doReceiveMessage(Message message) {
        try {
            // 设置主题
            if (message instanceof AbstractMessage) {
                ((AbstractMessage) message).setTopic(topic);
            }
            // 尝试阻塞执行
            if (tryProcess(message, processor)) {
                tryAck(message);
            } else {
                tryReject(message);
            }
        } catch (Throwable e) {
            tryReject(message);
        }
    }

    private boolean tryProcess(Message message, MessageService processor) {
        try {
            processor.handle(message);
            return true;
        } catch (Exception e) {
            return messageErrorHandler.onError(e, message, processor);
        }
    }


}
