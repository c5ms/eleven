package com.demcia.eleven.alfred.domain.service;

import com.demcia.eleven.alfred.core.action.TaskCreateAction;
import com.demcia.eleven.alfred.core.action.TaskQueryAction;
import com.demcia.eleven.alfred.core.action.TaskUpdateAction;
import com.demcia.eleven.alfred.domain.entity.Task;
import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.pageable.PaginationResult;

import java.util.Optional;

public interface TaskService {

    // ======================== read ========================

    PaginationResult<Task> queryTask(TaskQueryAction queryAction);

    Optional<Task> getTask(String id);

    default Task requireTask(String id) {
        return this.getTask(id).orElseThrow(() -> DataNotFoundException.of("任务不存在"));
    }

    // ======================== write ========================

    Task createTask(TaskCreateAction action);

    void updateTask(Task task, TaskUpdateAction updateAction);

    void deleteTask(Task task);
}
