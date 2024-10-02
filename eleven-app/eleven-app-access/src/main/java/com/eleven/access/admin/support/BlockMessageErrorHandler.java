package com.eleven.access.admin.support;

import cn.hutool.core.thread.ThreadUtil;
import com.cnetong.access.admin.domain.entity.MessageBlocking;
import com.cnetong.access.admin.domain.repository.BlockingRepository;
import com.cnetong.access.core.Message;
import com.cnetong.access.core.MessageErrorHandler;
import com.cnetong.access.core.MessageService;
import com.cnetong.common.time.TimeContext;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class BlockMessageErrorHandler implements MessageErrorHandler {
    private final String listenerId;
    private final String topic;
    private final Set<String> blocks = new HashSet<>();
    private final BlockingRepository blockingRepository;
    private volatile boolean isRunning = false;

    @Builder
    public BlockMessageErrorHandler(String listenerId, String topic, BlockingRepository blockingRepository) {
        this.blockingRepository = blockingRepository;
        this.listenerId = listenerId;
        this.topic = topic;
    }

    @Override
    public boolean onError(Exception origin, Message message, MessageService messageService) {
        boolean done = false;

        String blockingId = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
        MessageBlocking messageBlocking = new MessageBlocking();
        messageBlocking.setId(blockingId);
        messageBlocking.setListenerId(listenerId);
        messageBlocking.setTopic(topic);
        messageBlocking.setDescription(String.format("消息接收失败，%s", ExceptionUtils.getRootCauseMessage(origin)));
        messageBlocking.setData(message.asString());
        messageBlocking.error(origin);
        messageBlocking.setCreateDate(TimeContext.localDateTime());
        blockingRepository.save(messageBlocking);
        blocks.add(messageBlocking.getId());

        try {
            while (isRunning() && !Thread.currentThread().isInterrupted()) {
                // 等待三秒背压
                ThreadUtil.sleep(3, TimeUnit.SECONDS);
                try {
                    messageService.handle(message);
                    return true;
                } catch (Exception e) {

                    log.warn("消息阻塞,{}", message.asString());

                    var blockingOpt = blockingRepository.findById(blockingId);
                    if (blockingOpt.isEmpty()) {
                        return true;
                    }
                    messageBlocking = blockingOpt.get();
                    // 阻塞已解决
                    if (messageBlocking.isSolve()) {
                        log.warn("阻塞解除，解除方案：{}", messageBlocking.getSolution());
                        switch (messageBlocking.getSolution()) {
                            case "ignore":
                                return true;
                            case "reject":
                                return false;
                        }
                    }

                    // 记录错误，继续重试
                    messageBlocking.error(e);
                    blockingRepository.save(messageBlocking);
                }
            }
        } finally {
            blockingRepository.deleteById(blockingId);
            blocks.remove(blockingId);
        }
        return done;
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        this.isRunning = false;
        blockingRepository.deleteAllById(blocks);
    }

    private boolean isRunning() {
        return this.isRunning;
    }


}
