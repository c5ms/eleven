package com.eleven.framework.event.support;

import com.eleven.framework.event.ApplicationEvent;
import com.eleven.framework.event.ApplicationEventHeader;
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
