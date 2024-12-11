package com.eleven.core.event;

import jakarta.annotation.Nonnull;

import java.io.Serializable;

public interface ApplicationEvent extends Serializable {

    @Nonnull
    ApplicationEventHeader getHeader();

}
