package com.eleven.access.core.support;

import com.github.kagkarlsson.scheduler.task.helper.ScheduleAndData;
import com.github.kagkarlsson.scheduler.task.schedule.Schedule;
import lombok.Getter;

@Getter
public class SyncScheduleAndTaskId implements ScheduleAndData {
    private static final long serialVersionUID = 1L;

    private final Schedule schedule;
    private final String taskId;

    public SyncScheduleAndTaskId(Schedule schedule, String taskId) {
        this.schedule = schedule;
        this.taskId = taskId;
    }

    @Override
    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public Object getData() {
        return taskId;
    }
}
