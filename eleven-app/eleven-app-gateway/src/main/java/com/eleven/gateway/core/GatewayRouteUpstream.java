package com.eleven.gateway.core;

import lombok.Builder;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class GatewayRouteUpstream {
    LongAdder hits = new LongAdder();
    List<String> uris;

    @Builder
    public GatewayRouteUpstream(List<String> uris) {
        this.uris = uris;
    }

    public String selectUri() {
        if (getUris().isEmpty()) {
            return null;
        }
        // TODO 缺少负载均衡算法提取（1. 轮训，2.随机，3. 权重随机）
        hits.increment();
        int seed = hits.intValue() % getUris().size();
        return getUris().get(seed);
    }

    public List<String> getUris() {
        return uris;
    }
}
