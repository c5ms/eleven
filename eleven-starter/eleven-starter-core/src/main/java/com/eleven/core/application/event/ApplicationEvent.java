package com.eleven.core.application.event;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface ApplicationEvent extends Serializable {

    LocalDateTime time();

    ApplicationEventMeta meta();

}
