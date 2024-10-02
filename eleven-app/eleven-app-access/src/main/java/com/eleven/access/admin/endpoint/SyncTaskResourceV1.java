package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.domain.action.SyncTaskQueryAction;
import com.cnetong.access.admin.domain.dto.SyncTaskDto;
import com.cnetong.access.admin.domain.entity.SyncTask;
import com.cnetong.access.admin.domain.entity.SyncTaskMapping;
import com.cnetong.access.admin.service.SyncTaskService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据离线同步
 */
@Slf4j
@Tag(name = "syncTask", description = "离线同步")
@RestApi
@RestController
@RequiredArgsConstructor
@RequestMapping("/sync/tasks")
public class SyncTaskResourceV1 {

    private final SyncTaskService syncTaskService;

    @Operation(summary = "查询任务")
    @GetMapping
    public Page<SyncTask> queryTasks(@ParameterObject SyncTaskQueryAction action) {
        return syncTaskService.queryTasks(action);
    }

    @Operation(summary = "新建任务")
    @PostMapping
    public SyncTask createTask(@RequestBody @Validated SyncTaskDto dto) throws Exception {
        return syncTaskService.createTask(dto);
    }

    @Operation(summary = "更新任务")
    @PostMapping("/{id}")
    public SyncTask updateTask(@PathVariable("id") String id, @RequestBody @Validated SyncTaskDto dto) throws Exception {
        var exist = syncTaskService.requireTask(id);
        return syncTaskService.updateTask(exist, dto);
    }

    @Operation(summary = "读取任务")
    @GetMapping("/{id}")
    public SyncTask requireTask(@PathVariable("id") String id) throws Exception {
        return syncTaskService.requireTask(id);
    }

    @Operation(summary = "删除任务")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") String id) {
        var exist = syncTaskService.requireTask(id);
        syncTaskService.deleteTask(exist);
    }

    @Operation(summary = "发布任务")
    @PostMapping("/{id}/publish")
    public void publishTask(@PathVariable("id") String id) {
        var exist = syncTaskService.requireTask(id);
        syncTaskService.startTask(exist);
    }


    @Operation(summary = "撤销任务")
    @PostMapping("/{id}/cancel")
    public void cancelTask(@PathVariable("id") String id) {
        var exist = syncTaskService.requireTask(id);
        syncTaskService.cancelTask(exist);
    }


    @Operation(summary = "执行任务")
    @PostMapping("/{id}/run")
    public void runTask(@PathVariable("id") String id, @RequestBody Map<String, String> runtimes) {
        var exist = syncTaskService.requireTask(id);
        syncTaskService.runTask(exist, runtimes);
    }

    @Operation(summary = "结束任务")
    @PostMapping("/{id}/stop")
    public void stopTask(@PathVariable("id") String id) {
        var exist = syncTaskService.requireTask(id);
        syncTaskService.stopTask(exist);
    }


    @Operation(summary = "初始化字段映射")
    @PostMapping("/{id}/mappings/init")
    public List<SyncTaskMapping> initScheduleMapping(@PathVariable("id") String id) {
        var exist = syncTaskService.requireTask(id);
        syncTaskService.adjustMapping(exist);
        return exist.getMappings();
    }


    @Operation(summary = "读取字段映射")
    @PostMapping("/{id}/mappings")
    public List<SyncTaskMapping> listScheduleMapping(@PathVariable("id") String id) {
        var exist = syncTaskService.requireTask(id);
        return exist.getMappings();
    }

}
