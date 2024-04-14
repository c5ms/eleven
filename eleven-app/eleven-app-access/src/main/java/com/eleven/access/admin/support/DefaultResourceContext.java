package com.eleven.access.admin.support;

import com.cnetong.access.admin.constants.AccessConstants;
import com.cnetong.access.admin.domain.repository.ConnectionDefinitionRepository;
import com.cnetong.access.core.*;
import com.cnetong.common.cluster.MetadataManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultResourceContext implements ResourceContext {
    private final Map<String, ResourceProxy<?>> connections = new ConcurrentHashMap<>();

    private final ConnectionDefinitionRepository connectionDefinitionRepository;

    private final ComponentManager componentManager;
    private final MetadataManager metadataManager;

    @Override
    @SuppressWarnings("unchecked")
    public synchronized <T> Resource<T> getResource(String id) {
        return (Resource<T>) connections.computeIfAbsent(id, s -> new ResourceProxy<T>(id));
    }

    @Override
    public Collection<ResourceProxy<?>> listResources() {
        return this.connections.values();
    }

    @Scheduled(fixedRate = 1000)
    public void maintain() {
        metadataManager.syncVersion(AccessConstants.VERSION_KEY_CONNECTIONS, globalVersion -> {
            log.info("开始维护全局连接");
            try {
                var definitions = connectionDefinitionRepository.findAll();
                var garbage = new HashSet<>(connections.keySet());

                // 检查在线连接
                for (var define : definitions) {
                    garbage.remove(define.getId());
                    getResource(define.getId());
                }

                // 清理垃圾
                garbage.forEach(this::removeResource);

                log.info("连接更新完成,连接数量:{}", connections.size());
            } catch (Exception e) {
                log.error("连接维护失败", e);
            }
        });
    }

    @Override
    public void removeResource(String id) {
        var exist = connections.get(id);
        if (null != exist) {
            exist.destroy();
            connections.remove(id);
        }
    }

    @Getter
    @SuppressWarnings("unchecked")
    private class ResourceProxy<T> extends AbstractDisposable implements Resource<T> {
        private final String id;

        private Resource<T> proxy;

        public ResourceProxy(String id) {
            this.id = id;
        }

        void sync() {
            metadataManager.syncVersion(AccessConstants.getResourceKey(id), version -> {
                var define = connectionDefinitionRepository.findById(id).orElseThrow(() -> new ComponentNotFoundException("连接不存在"));
                if (null == this.proxy) {
                    this.proxy = (Resource<T>) componentManager.createComponent(define.getComponent(), define.getConfig(), ResourceFactory.class);
                } else {
                    this.proxy.update(define.getConfig());
                }
                log.info("连接已更新:{},{},{}", define.getId(), define.getLabel(), define.getConfig());
            });
        }

        @Override
        public T getActual() {
            sync();
            return getProxy().getActual();
        }

        @Override
        public void update(Map<String, String> config) {
            sync();
            getProxy().update(config);
        }

        @Override
        public void check() throws Exception {
            sync();
            getProxy().check();
        }

        @Override
        public void destroy() {
            if (null != this.proxy) {
                this.proxy.destroy();
            }
        }


        @Override
        public ResourceProducer createProducer(Map<String, String> config) {
            sync();
            return getProxy().createProducer(config);
        }

        @Override
        public ResourceConsumer createConsumer(Map<String, String> config) {
            sync();
            return getProxy().createConsumer(config);
        }
    }

}
