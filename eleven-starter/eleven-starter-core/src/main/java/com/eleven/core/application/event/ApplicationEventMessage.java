package com.eleven.core.application.event;

import com.eleven.core.authenticate.Principal;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApplicationEventMessage implements Serializable {

    private String cls;

    private String event;

    private LocalDateTime time;

    private Principal trigger;

    private String body;

    public void writeHeaderTo(ApplicationEvent event) {
        event.getHeader().setTrigger(this.getTrigger());
        event.getHeader().setTime(this.getTime());
    }
}
