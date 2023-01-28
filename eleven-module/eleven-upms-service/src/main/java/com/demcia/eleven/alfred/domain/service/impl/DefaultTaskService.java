package com.demcia.eleven.alfred.domain.service.impl;

import com.demcia.eleven.alfred.core.action.TaskCreateAction;
import com.demcia.eleven.alfred.core.action.TaskQueryAction;
import com.demcia.eleven.alfred.core.action.TaskUpdateAction;
import com.demcia.eleven.alfred.core.event.TaskCreatedEvent;
import com.demcia.eleven.alfred.domain.entity.Task;
import com.demcia.eleven.alfred.domain.repository.TaskRepository;
import com.demcia.eleven.alfred.domain.service.TaskService;
import com.demcia.eleven.core.domain.helper.PageableQueryHelper;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.upms.domain.entity.User;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {
    private final TaskRepository taskRepository;

    private final MapperFacade mapperFacade;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public PaginationResult<Task> queryTask(TaskQueryAction queryAction) {
        var spec = Specifications.<Task>and()
                .build();
        var sort = Sort.by(User.Fields.id).descending();
        var pageable = PageableQueryHelper.toSpringDataPageable(queryAction, sort);
        var page = taskRepository.findAll(spec, pageable);
        return PageableQueryHelper.toPageResult(page);
    }

    @Override
    public Optional<Task> getTask(String id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task createTask(TaskCreateAction action) {
        var task = mapperFacade.map(action, Task.class);
        taskRepository.save(task);
        applicationEventPublisher.publishEvent(new TaskCreatedEvent(task.getId()));
        return task;
    }

    @Override
    public void updateTask(Task task, TaskUpdateAction updateAction) {
        if(StringUtils.isNotBlank(updateAction.getSubject())){
            task.setSubject(updateAction.getSubject());
        }
        if(StringUtils.isNotBlank(updateAction.getDescription())){
            task.setDescription(updateAction.getDescription());
        }
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}
