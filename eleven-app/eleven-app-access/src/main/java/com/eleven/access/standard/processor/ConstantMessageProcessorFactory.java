package com.eleven.access.standard.processor;

import com.cnetong.access.core.MessageExchange;
import com.cnetong.access.core.MessageProcessor;
import com.cnetong.access.core.MessageProcessorChain;
import com.cnetong.access.core.MessageProcessorFactory;
import org.springframework.stereotype.Component;

@Component
public class ConstantMessageProcessorFactory implements MessageProcessorFactory {
    private static final String NAME = "constant";

    public String getName() {
        return NAME;
    }

    @Override
    public MessageProcessor apply(String configString) {
        return new MessageProcessor() {
            @Override
            public void filter(MessageExchange exchange, MessageProcessorChain chain) {
                exchange.out(configString);
                chain.doFilter(exchange);
            }
        };
    }


}
