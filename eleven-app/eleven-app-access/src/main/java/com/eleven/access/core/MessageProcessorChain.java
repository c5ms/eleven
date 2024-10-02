package com.eleven.access.core;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

public class MessageProcessorChain {

    private final int index;
    private final List<MessageProcessor> filters;

    public MessageProcessorChain(List<MessageProcessor> filters) {
        this.filters = new ArrayList<>(filters);
        this.index = 0;
    }

    private MessageProcessorChain(MessageProcessorChain parent, int index) {
        this.filters = parent.getFilters();
        this.index = index;
    }

    protected List<MessageProcessor> getFilters() {
        return filters;
    }

    public void doFilter(MessageExchange exchange) {
        if (this.index < filters.size()) {
            MessageProcessor filter = filters.get(this.index);
            MessageProcessorChain chain = new MessageProcessorChain(this, this.index + 1);
            try {
                filter.filter(exchange, chain);
            } catch (Exception e) {
                throw MessageErrors.FILTER_ERROR.exception("过滤组件 " + filter + " 处理错误:" + ExceptionUtils.getRootCauseMessage(e));
            }
        }
    }
}
