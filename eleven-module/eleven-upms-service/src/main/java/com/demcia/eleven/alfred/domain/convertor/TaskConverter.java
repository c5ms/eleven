package com.demcia.eleven.alfred.domain.convertor;

import com.demcia.eleven.alfred.core.dto.TaskDto;
import com.demcia.eleven.alfred.domain.entity.Task;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskConverter {
    private final MapperFacade mapperFacade;

    public TaskDto toDto(Task task) {
        var dto = mapperFacade.map(task, TaskDto.class);
        return dto;
    }

}
