package com.eleven.access.standard.filter;

import com.cnetong.access.core.MessageExchange;
import com.cnetong.access.core.MessageFilter;
import com.cnetong.access.core.MessageFilterFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DxpSenderMessageFilterFactory implements MessageFilterFactory {
    private static final String NAME = "dxp.sender";


    public String getName() {
        return NAME;
    }

    @Override
    public MessageFilter apply(String configString) {
        return new MessageFilter() {
            @Override
            public boolean test(MessageExchange exchange) {
                var in = exchange.getIn();
                var instr = in.asString();
                String dxpSenderId = StringUtils.substringBetween(instr, "<SenderId>", "</SenderId>");
                return StringUtils.equals(StringUtils.trim(dxpSenderId), StringUtils.trim(configString));
            }
        };
    }


}
