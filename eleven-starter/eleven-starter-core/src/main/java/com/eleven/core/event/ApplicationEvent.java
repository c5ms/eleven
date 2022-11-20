package com.eleven.core.event;

import java.time.LocalDateTime;

public interface ApplicationEvent extends Event {

    LocalDateTime time();

    ApplicationEventMeta meta();

}
