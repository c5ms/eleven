package com.eleven.doney.endpoint.front;

import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.doney.domain.ProjectService;
import com.eleven.doney.dto.ProjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Tag(name = "个人")
@RequestMapping("/personal")
@AsRestApi
@RequiredArgsConstructor
public class PersonalRestApi {

    private final ProjectService projectService;

    @Operation(summary = "可见项目列表")
    @GetMapping("/my/projects")
    public List<ProjectDto> listMyProjects() {
        return projectService.listProjects();
    }

}
