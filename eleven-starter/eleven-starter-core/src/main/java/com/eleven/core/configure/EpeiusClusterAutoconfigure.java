package com.eleven.core.configure;

import cn.hutool.core.net.NetUtil;
import com.eleven.core.cluster.ClusterMember;
import com.eleven.core.cluster.MetadataStore;
import com.eleven.core.cluster.support.CacheMetadataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheMetadataStore.CACHE_NAME)
public class EpeiusClusterAutoconfigure {

    @Bean
    public ClusterMember clusterMember(ServerProperties properties) {
        ClusterMember node = new ClusterMember();
        node.setId(UUID.randomUUID().toString());
        node.setAddress(NetUtil.getLocalhost().getHostAddress());
        node.setPort(properties.getPort());
        return node;
    }

    @Bean
    @ConditionalOnMissingBean(MetadataStore.class)
    public MetadataStore metadataStore(CacheManager cacheManager) {
        return new CacheMetadataStore(cacheManager);
    }

}
