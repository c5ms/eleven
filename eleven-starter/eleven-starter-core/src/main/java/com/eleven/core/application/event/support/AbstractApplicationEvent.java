package com.eleven.core.application.event.support;

import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.application.event.ApplicationEventHeader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractApplicationEvent implements ApplicationEvent {

    @JsonIgnore
    private transient final ApplicationEventHeader header = new ApplicationEventHeader();

    @Nonnull
    @Override
    @JsonIgnore
    public ApplicationEventHeader getHeader() {
        return header;
    }

}
