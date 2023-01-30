package com.demcia.eleven.alfred.domain.event;

import lombok.Value;

@Value
public class TaskCreatedEvent {
    String taskId;
}
