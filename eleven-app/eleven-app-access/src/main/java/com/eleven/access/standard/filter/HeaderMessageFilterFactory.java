package com.eleven.access.standard.filter;

import cn.hutool.json.JSONUtil;
import com.cnetong.access.core.MessageFilter;
import com.cnetong.access.core.MessageFilterFactory;
import com.cnetong.access.core.utils.ConfigMap;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HeaderMessageFilterFactory implements MessageFilterFactory {
    private static final String NAME = "header";

    public String getName() {
        return NAME;
    }

    @Override
    public MessageFilter apply(String configString) {
        ConfigMap config = JSONUtil.toBean(configString, ConfigMap.class);
        var pattens = config.entrySet().stream()
                .map(stringStringEntry -> new Config(stringStringEntry.getKey(), stringStringEntry.getValue()))
                .collect(Collectors.toList());
        return exchange -> {
            for (Config patten : pattens) {
                String value = exchange.getIn().getHeader().getOrDefault(patten.getName(), "");
                if (value.matches(patten.getRegexp())) {
                    return true;
                }
            }
            return false;
        };
    }


    @Value
    public static class Config {
        String name;
        String regexp;
    }
}
