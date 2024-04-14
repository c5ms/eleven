package com.eleven.access.standard.filter;

import com.cnetong.access.core.MessageFilter;
import com.cnetong.access.core.MessageFilterFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DxpReceiverMessageFilterFactory implements MessageFilterFactory {
    private static final String NAME = "dxp.receiver";


    public String getName() {
        return NAME;
    }

    @Override
    public MessageFilter apply(String configString) {
        return exchange -> {
            var in = exchange.getIn();
            var instr = in.asString();
            String dxpReceiverIds = StringUtils.substringBetween(instr, "<ReceiverIds>", "</ReceiverIds>");
            return dxpReceiverIds.contains("<ReceiverId>" + configString + "</ReceiverId>");
        };
    }


}
