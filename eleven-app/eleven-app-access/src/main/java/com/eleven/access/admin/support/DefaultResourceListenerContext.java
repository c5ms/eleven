package com.eleven.access.admin.support;

import com.cnetong.access.admin.constants.AccessConstants;
import com.cnetong.access.admin.domain.repository.BlockingRepository;
import com.cnetong.access.admin.domain.repository.ListenerDefinitionRepository;
import com.cnetong.access.core.*;
import com.cnetong.access.core.support.DefaultMessageChannel;
import com.cnetong.common.cluster.MetadataManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultResourceListenerContext implements ResourceListenerContext {
    private final Map<String, ResourceListenerProxy> listeners = new ConcurrentHashMap<>();


    private final MessageService messageService;
    private final MetadataManager metadataManager;
    private final ResourceContext resourceContext;

    private final BlockingRepository blockingRepository;
    private final ListenerDefinitionRepository listenerDefinitionRepository;

    public ResourceListener getListener(String id) {
        return listeners.computeIfAbsent(id, s -> new ResourceListenerProxy(id));
    }

    @Override
    public Collection<ResourceListenerProxy> listListeners() {
        return listeners.values();
    }

    @Scheduled(fixedRate = 1000)
    public void maintain() {
        metadataManager.syncVersion(AccessConstants.VERSION_KEY_LISTENERS, globalVersion -> {
            log.info("开始维护监听器");

            try {
                var definitions = listenerDefinitionRepository.findAllByRunning(true);
                // 假设在线的都是垃圾
                var garbage = new HashSet<>(listeners.keySet());

                for (var define : definitions) {
                    // 数据库有记录的不是垃圾
                    garbage.remove(define.getId());
                    // 获取的这个操作会检测独立任务的版本，如果已经停止服务，则自己主动关闭自己，如果正在服务，但是版本变了，则更新
                    getListener(define.getId());
                }

                // 清理垃圾，这些垃圾是在运行的实例，但是数据库没有记录
                garbage.forEach(this::removeListener);

                log.info("监听器更新完成,连接数量:{}", listeners.size());
            } catch (Exception e) {
                log.error("监听器维护失败", e);
            }
        });
    }

    @Override
    public void removeListener(String id) {
        var exist = listeners.get(id);
        if (null != exist) {
            exist.destroy();
            listeners.remove(id);
        }
    }


    @Getter
    public class ResourceListenerProxy extends AbstractDisposable implements ResourceListener {

        @Getter
        private final String id;
        private final String listenerKey;

        private ResourceConsumer proxy;
        private DefaultMessageChannel channel;
        private BlockMessageErrorHandler errorHandler;

        // 当前看到的资源版本
        private String lastResourceVersion;
        private String resourceKey;


        public ResourceListenerProxy(String id) {
            this.id = id;
            this.listenerKey = AccessConstants.getListenerKey(id);
        }

        void sync() {
            var currentResourceVersion = metadataManager.getLocalVersion(resourceKey);
            if (!StringUtils.equals(currentResourceVersion, lastResourceVersion) || StringUtils.isAnyBlank(currentResourceVersion, lastResourceVersion)) {
                metadataManager.updateLocalVersion(listenerKey, "0");
            }

            metadataManager.syncVersion(listenerKey, version -> {
                if (null != this.proxy) {
                    this.proxy.destroy();
                }
                if (null != this.channel) {
                    this.channel.stop();
                }
                var define = listenerDefinitionRepository.findById(id).orElseThrow(() -> new ComponentNotFoundException("终端不存在"));
                if (!BooleanUtils.isTrue(define.getRunning())) {
                    throw new ResourceUnreadyException("终端未启动");
                }
                var connection = resourceContext.getResource(define.getResourceId());
                this.resourceKey = AccessConstants.getResourceKey(define.getResourceId());
                this.lastResourceVersion = metadataManager.getLocalVersion(resourceKey);
                this.proxy = connection.createConsumer(define.getConfig());
                this.errorHandler = BlockMessageErrorHandler.builder()
                        .blockingRepository(blockingRepository)
                        .listenerId(id)
                        .topic(define.getTopic())
                        .build();
                this.channel = DefaultMessageChannel.builder()
                        .id(id)
                        .errorHandler(errorHandler)
                        .headers(define.rawHeaders())
                        .processor(messageService)
                        .threads(define.getThreads())
                        .topic(define.getTopic())
                        .build();
                this.errorHandler.start();
                this.channel.start();
                this.proxy.listen(this.channel);
                blockingRepository.deleteByListenerId(id);
                log.info("监听器已更新:{},{},{}", define.getId(), define.getLabel(), define.getConfig());
            });
        }

        @Override
        public void start() {
            sync();
        }

        @Override
        public void check() throws Exception {
            try {
                sync();
                if (!getErrorHandler().getBlocks().isEmpty()) {
                    throw new ResourceBlockingException("终端阻塞:" + getErrorHandler().getBlocks().size());
                }
                getProxy().checkHealth();
            } catch (ResourceUnreadyException ignored) {
                // ignored
            }
        }

        @Override
        public void destroy() {
            if (null != this.proxy) {
                this.proxy.destroy();
                this.proxy = null;
            }

            if (null != this.channel) {
                this.channel.stop();
                this.channel = null;
            }
        }
    }
}
