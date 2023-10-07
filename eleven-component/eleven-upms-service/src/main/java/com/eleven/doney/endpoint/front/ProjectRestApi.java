package com.eleven.doney.endpoint.front;

import com.eleven.core.exception.RequireDataNotFoundException;
import com.eleven.core.domain.PaginationResult;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.doney.domain.MemberService;
import com.eleven.doney.domain.ProjectService;
import com.eleven.doney.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Tag(name = "项目")
@RequestMapping("/projects")
@AsRestApi
@RequiredArgsConstructor
public class ProjectRestApi {

    private final MemberService memberService;
    private final ProjectService projectService;

    @Operation(summary = "项目查询")
    @GetMapping
    public PaginationResult<ProjectDto> queryProjects(@ParameterObject ProjectQuery queryAction) {
        return projectService.queryProjectPage(queryAction);
    }

    @Operation(summary = "项目读取")
    @GetMapping("/{id}")
    public ProjectDto getProject(@PathVariable("id") String id) {
        return projectService.getProject(id).orElseThrow(RequireDataNotFoundException::new);
    }

    @Operation(summary = "项目创建")
    @PostMapping
    public ProjectDto createProject(@RequestBody @Validated ProjectSaveAction action) {
        return projectService.createProject(action);
    }

    @Operation(summary = "项目更新")
    @PutMapping("/{id}")
    public ProjectDto updateProject(@PathVariable("id") String id, @RequestBody ProjectSaveAction action) {
        return projectService.updateProject(id, action);
    }

    @Operation(summary = "项目删除")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") String id) {
        projectService.deleteProject(id);
    }

    @Operation(summary = "项目成员列表")
    @GetMapping("/{id}/members")
    public Optional<List<MemberDto>> listMembers(@PathVariable("id") String id) {
        return memberService.listMembersOfProject(id);
    }
    @Operation(summary = "项目成员添加")
    @PostMapping("/{id}/members")
    public Optional<MemberDto> addMember(@PathVariable("id") String id, @RequestBody MemberSaveAction action) {
        return memberService.addMemberToProject(id,action);
    }

}
