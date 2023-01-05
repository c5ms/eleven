package com.demcia.eleven.alfred.endpoint.rest;

import com.demcia.eleven.alfred.core.action.TaskCreateAction;
import com.demcia.eleven.alfred.core.action.TaskQueryAction;
import com.demcia.eleven.alfred.core.action.TaskUpdateAction;
import com.demcia.eleven.alfred.core.dto.TaskDto;
import com.demcia.eleven.alfred.domain.convertor.TaskConverter;
import com.demcia.eleven.alfred.domain.entity.Task;
import com.demcia.eleven.alfred.domain.service.TaskService;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.core.rest.annonation.RestResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "任务")
@RequestMapping("/tasks")
@RestResource
@RequiredArgsConstructor
public class TaskResourceV1 {


    private final TaskService taskService;
    private final TaskConverter taskConverter;

    @Operation(summary = "任务查询")
    @GetMapping
    public PaginationResult<TaskDto> queryTask(@ParameterObject TaskQueryAction queryAction) {
        return taskService.queryTask(queryAction).map(taskConverter::toDto);
    }

    @Operation(summary = "任务读取")
    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable("id") String id) {
        var task = taskService.requireTask(id);
        return taskConverter.toDto(task);
    }

    @Operation(summary = "任务创建")
    @PostMapping
    public TaskDto createTask(@RequestBody @Validated TaskCreateAction action) {
        var task = taskService.createTask(action);
        return taskConverter.toDto(task);
    }

    @Operation(summary = "任务更新")
    @PostMapping("/{id}")
    public TaskDto updateTask(@PathVariable("id") String id, @RequestBody TaskUpdateAction updateAction) {
        Task task = taskService.requireTask(id);
        taskService.updateTask(task, updateAction);
        return taskConverter.toDto(task);
    }

    @Operation(summary = "任务删除")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") String id) {
        taskService.getTask(id).ifPresent(taskService::deleteTask);
    }

}
