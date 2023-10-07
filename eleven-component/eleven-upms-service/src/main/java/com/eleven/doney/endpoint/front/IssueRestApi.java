package com.eleven.doney.endpoint.front;

import com.eleven.core.domain.PaginationResult;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.doney.domain.IssueService;
import com.eleven.doney.dto.IssueDto;
import com.eleven.doney.dto.IssueQuery;
import com.eleven.doney.dto.IssueSaveAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "任务")
@RequestMapping("/issues")
@AsRestApi
@RequiredArgsConstructor
public class IssueRestApi {

    private final IssueService issueService;

    @Operation(summary = "任务创建")
    @PostMapping
    public IssueDto createIssue(@RequestBody @Validated IssueSaveAction action) {
        return issueService.createIssue(action);
    }

    @Operation(summary = "任务读取")
    @GetMapping("/{id}")
    public Optional<IssueDto> getIssue(@PathVariable("id") String id) {
        return issueService.getIssue(id);
    }


    @Operation(summary = "任务删除")
    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable("id") String id) {
        issueService.deleteIssue(id);
    }

    @Operation(summary = "任务更新")
    @PutMapping("/{id}")
    public IssueDto updateIssue(@PathVariable("id") String id, @RequestBody @Validated IssueSaveAction action) {
        return issueService.updateIssue(id, action);
    }

    @Operation(summary = "任务查询")
    @GetMapping
    public PaginationResult<IssueDto> queryIssuePage(@ParameterObject IssueQuery queryAction) {
        return issueService.queryIssuePage(queryAction);
    }
}
