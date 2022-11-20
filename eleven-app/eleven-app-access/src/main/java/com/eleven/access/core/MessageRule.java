package com.eleven.access.core;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class MessageRule {
    private final String producer;
    private final List<MessageFilter> filters;
    private final List<MessageProcessor> processors;

    @Builder
    public MessageRule(List<MessageFilter> filters,
                       List<MessageProcessor> processors,
                       String producer) {
        this.producer = producer;
        this.processors = Optional.ofNullable(processors).orElseGet(ArrayList::new);
        this.filters = Optional.ofNullable(filters).orElseGet(ArrayList::new);
    }

    public boolean match(MessageExchange message) {
        if (this.filters.isEmpty()) {
            return true;
        }
        return getFilters()
                .stream()
                .allMatch(routePredicate -> routePredicate.test(message));
    }


}
