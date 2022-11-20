package com.eleven.access.core;

import javax.validation.constraints.NotNull;

public interface MessagePartitionDecider {

    @NotNull
    String partition(Message message);

}
