package com.eleven.access.admin.support;

import com.cnetong.access.core.Message;
import com.cnetong.access.core.MessagePartitionDecider;
import com.cnetong.common.time.TimeContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ByDateMessagePartitionDecider implements MessagePartitionDecider {
    private final DateTimeFormatter formatter;

    public ByDateMessagePartitionDecider(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public String partition(Message message) {
        LocalDateTime localDateTime = TimeContext.localDateTime();
        return localDateTime.format(formatter);
    }
}
