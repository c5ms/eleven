package com.eleven.access.standard.filter;

import com.cnetong.access.core.MessageFilter;
import com.cnetong.access.core.MessageFilterFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TopicMessageFilterFactory implements MessageFilterFactory {
    private static final String NAME = "topic";

    public String getName() {
        return NAME;
    }

    @Override
    public MessageFilter apply(String configString) {
        return exchange -> StringUtils.equals(exchange.getIn().getTopic(), configString);
    }

}
