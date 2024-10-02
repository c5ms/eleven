package com.eleven.core.cluster.support;

import cn.hutool.json.JSONUtil;
import com.eleven.core.cluster.ClusterMember;
import com.eleven.core.cluster.Metadata;
import com.eleven.core.cluster.MetadataManager;
import com.eleven.core.cluster.MetadataStore;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMetadataManager implements MetadataManager {
    private final MetadataStore metadataStore;
    private final ClusterMember currentInstance;


    @Override
    public Metadata put(String name, String data) {
        return metadataStore.put(name, data);
    }

    @NotNull
    @Override
    public Metadata get(String name) {
        return metadataStore.get(name);
    }

    @Override
    public void putRuntimeProperty(String owner, String property, Serializable information) {
        if (null == information) {
            metadataStore.put("property." + owner + ":" + property, null);
            return;
        }
        metadataStore.put("property." + owner + ":" + property, JSONUtil.toJsonStr(information));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRuntimeProperty(String owner, String property, Class<T> clazz) {
        var metaData = metadataStore.get("property." + owner + ":" + property);
        if (null == metaData.getValue()) {
            return null;
        }
        if (clazz.isAssignableFrom(String.class)) {
            return (T) metaData.getValue();
        }
        return JSONUtil.toBean(metaData.getValue(), clazz);
    }

    public String newVersion() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public String updateGlobalVersion(String name) {
        return metadataStore.put("global." + name, newVersion()).getValue();
    }

    @Override
    public String getGlobalVersion(String name) {
        return metadataStore.get("global." + name).getValue();
    }

    @Override
    public void updateLocalVersion(String name, String version) {
        metadataStore.put("local." + name + "@" + currentInstance.getId(), version);
    }

    @Override
    public String getLocalVersion(String name) {
        return metadataStore.get("local." + name + "@" + currentInstance.getId()).getValue();
    }

    /**
     * Compare versions between local and global .
     * if they don't equally ,the onChange consumer will be called.
     * It's thread safe.
     *
     * @param key      to compare version key
     * @param onChange call back function when the versions are not equal
     */
    @Override
    public void syncVersion(String key, Consumer<String> onChange) {
        log.debug("sync version for key {}", key);
        var currentVersion = getGlobalVersion(key);
        if (null == currentVersion) {
            currentVersion = this.updateGlobalVersion(key);
        }
        var localVersion = getLocalVersion(key);
        if (!isVersionEquals(currentVersion, localVersion)) {
            synchronized (this) {
                localVersion = getLocalVersion(key);
                if (!isVersionEquals(currentVersion, localVersion)) {
                    onChange.accept(currentVersion);
                    updateLocalVersion(key, currentVersion);
                }
            }
        }
    }

    @Override
    public void syncVersion(String key) {
        var currentVersion = getGlobalVersion(key);
        if (null == currentVersion) {
            currentVersion = this.updateGlobalVersion(key);
        }
        var localVersion = getLocalVersion(key);
        if (!isVersionEquals(currentVersion, localVersion)) {
            synchronized (this) {
                updateLocalVersion(key, currentVersion);
            }
        }
    }

    private boolean isVersionEquals(String currentVersion, String localVersion) {
        if (null == localVersion) {
            return false;
        }
        return StringUtils.equals(currentVersion, localVersion);
    }

}
