package com.eleven.core.event;

import com.eleven.core.time.TimeContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class AbstractApplicationEvent implements ApplicationEvent {

    @JsonIgnore
    private  transient final LocalDateTime time = TimeContext.localDateTime();

    @JsonIgnore
    private transient final ApplicationEventMeta meta = new ApplicationEventMeta();

    @Override
    public ApplicationEventMeta meta() {
        return meta;
    }

    public LocalDateTime time() {
        return time;
    }
}
