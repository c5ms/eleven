package com.eleven.doney.endpoint.front;

import com.eleven.core.security.SecurityContext;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.doney.domain.ProjectService;
import com.eleven.doney.model.ProjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "personal")
@RequestMapping("/personal")
@AsRestApi
@RequiredArgsConstructor
public class PersonalRestApi {

    private final ProjectService projectService;

    @Operation(summary = "可见项目列表")
    @GetMapping("/my/projects")
    public List<ProjectDto> listMyProjects() {
        var subject = SecurityContext.getCurrentSubject();
        if (subject.isAnonymous()){
            log.info("load project list for anonymous");
//            return new ArrayList<>();
            projectService.listAllProjects();
        }
        log.info("load project list for {}",subject.getNickName());
        return projectService.listProjectsOfMember(subject.getUserId());
    }

}
