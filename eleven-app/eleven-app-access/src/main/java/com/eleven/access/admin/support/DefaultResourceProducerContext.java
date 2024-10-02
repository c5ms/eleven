package com.eleven.access.admin.support;

import cn.hutool.core.io.IoUtil;
import com.cnetong.access.admin.constants.AccessConstants;
import com.cnetong.access.admin.domain.repository.ProducerDefinitionRepository;
import com.cnetong.access.core.*;
import com.cnetong.common.cluster.MetadataManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultResourceProducerContext implements ResourceProducerContext {
    private final Map<String, ResourceProducerProxy> producers = new ConcurrentHashMap<>();

    private final ProducerDefinitionRepository producerDefinitionRepository;

    private final ResourceContext resourceContext;
    private final MetadataManager metadataManager;


    @Override
    public ResourceProducer getProducer(String id) {
        return producers.computeIfAbsent(id, s -> new ResourceProducerProxy(id));
    }

    @Override
    public void removeProducer(String id) {
        IoUtil.closeIfPosible(producers.remove(id));
    }

    @Override
    public Collection<ResourceProducerProxy> listProducers() {
        return producers.values();
    }

    @Getter
    private class ResourceProducerProxy extends AbstractDisposable implements ResourceProducer {

        @Getter
        private final String id;
        private final String producerKey;
        private volatile ResourceProducer proxy;

        // 当前看到的资源版本
        private String lastResourceVersion;
        private String resourceKey;

        public ResourceProducerProxy(String id) {
            this.id = id;
            this.producerKey = AccessConstants.getProducerKey(id);
        }

        synchronized void sync() {
            var currentResourceVersion = metadataManager.getLocalVersion(resourceKey);
            if (!StringUtils.equals(currentResourceVersion, lastResourceVersion) || StringUtils.isAnyBlank(currentResourceVersion, lastResourceVersion)) {
                metadataManager.updateLocalVersion(producerKey, "0");
            }
            metadataManager.syncVersion(producerKey, version -> {
                if (null != this.proxy) {
                    this.proxy.close();
                }
                var define = producerDefinitionRepository.findById(id).orElseThrow(() -> new ComponentNotFoundException("终端不存在"));
                if (!BooleanUtils.isTrue(define.getRunning())) {
                    throw new ResourceUnreadyException("终端未启动");
                }
                var connection = resourceContext.getResource(define.getResourceId());
                this.proxy = connection.createProducer(define.getConfig());
                // 记录最后使用的资源和版本
                this.resourceKey = AccessConstants.getResourceKey(define.getResourceId());
                this.lastResourceVersion = metadataManager.getLocalVersion(resourceKey);
                log.info("生产者已更新:{},{},{}", define.getId(), define.getLabel(), define.getConfig());
            });
        }

        @Override
        public void produce(Message message) throws Exception {
            sync();
            try {
                getProxy().produce(message);
            } catch (Exception e) {
                log.warn("终端生产异常:{}", ExceptionUtils.getRootCauseMessage(e), e);
                throw e;
            }
        }

        @Override
        public void check() throws Exception {
            sync();
            proxy.check();
        }

        @Override
        public void close() {
            if (null != this.proxy) {
                this.proxy.close();
                this.proxy = null;
            }
        }

        @Override
        public boolean isClosed() {
            if (null != this.proxy) {
                return this.proxy.isClosed();
            }
            return true;
        }
    }


}
