package com.eleven.access.core;

import com.cnetong.access.admin.constants.AccessConstants;
import com.cnetong.common.cluster.MetadataManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthManager {
    private final MetadataManager metadataManager;
    private final ResourceContext resourceContext;
    private final ResourceProducerContext resourceProducerContext;
    private final ResourceListenerContext resourceListenerContext;

    public void markUnhealthy(String id, String key, Exception e) {
        var unhealthy = HealthInformation.unhealthy(id, e);
        metadataManager.putRuntimeProperty(key, "health", unhealthy);
    }

    public void markHealth(String id, String key) {
        var healthy = HealthInformation.healthy(id);
        metadataManager.putRuntimeProperty(key, "health", healthy);
    }

    public HealthInformation getConnectionHealthy(String id) {
        var key = AccessConstants.getResourceKey(id);
        return getHealthInformation(id, key);
    }

    public HealthInformation getEndpointHealthy(String id) {
        var key = AccessConstants.getProducerKey(id);
        return getHealthInformation(id, key);
    }

    public HealthInformation getListenerHealthy(String id) {
        var key = AccessConstants.getListenerKey(id);
        return getHealthInformation(id, key);
    }


    @Scheduled(fixedRate = 5 * 1000)
    public void check() {
        resourceContext.listResources()
                .stream()
                .filter(connection -> connection instanceof Disposable)
                .map(connection -> (Disposable) connection)
                .peek(disposable -> check(disposable, AccessConstants::getResourceKey))
                .filter(Disposable::isGarbage)
                .map(Disposable::getId)
                .forEach(resourceProducerContext::removeProducer);

        resourceListenerContext.listListeners()
                .stream()
                .filter(connection -> connection instanceof Disposable)
                .map(connection -> (Disposable) connection)
                .peek(disposable -> check(disposable, AccessConstants::getListenerKey))
                .filter(Disposable::isGarbage)
                .map(Disposable::getId)
                .forEach(resourceProducerContext::removeProducer);

        resourceProducerContext.listProducers()
                .stream()
                .filter(connection -> connection instanceof Disposable)
                .map(connection -> (Disposable) connection)
                .peek(disposable -> check(disposable, AccessConstants::getProducerKey))
                .filter(Disposable::isGarbage)
                .map(Disposable::getId)
                .forEach(resourceProducerContext::removeProducer);

    }

    private void check(Disposable disposable, Function<String, String> toKey) {
        var id = disposable.getId();
        var key = toKey.apply(id);
        try {
            disposable.check();
            markHealth(id, key);
        }
        // 组件可能已经被取消定义，则标记为垃圾
        catch (ComponentNotFoundException e) {
            disposable.markGarbage();
        }

        // 其他情况属于组件检查出现的错误，记录不健康状态
        catch (Exception e) {
            markUnhealthy(id, key, e);
        }
    }


    @NotNull
    public HealthInformation getHealthInformation(String id, String key) {
        var info = metadataManager.getRuntimeProperty(key, "health", HealthInformation.class);
        if (null == info) {
            return HealthInformation.unknown(id);
        }
        return info;
    }


}
