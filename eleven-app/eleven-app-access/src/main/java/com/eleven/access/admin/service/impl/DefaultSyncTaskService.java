package com.eleven.access.admin.service.impl;

import com.cnetong.access.admin.domain.action.SyncTaskQueryAction;
import com.cnetong.access.admin.domain.dto.SyncTaskDto;
import com.cnetong.access.admin.domain.dto.SyncTaskMappingDto;
import com.cnetong.access.admin.domain.dto.SyncTaskWriterDto;
import com.cnetong.access.admin.domain.entity.SyncTask;
import com.cnetong.access.admin.domain.entity.SyncTaskMapping;
import com.cnetong.access.admin.domain.entity.SyncTaskWriter;
import com.cnetong.access.admin.domain.repository.SyncLogRepository;
import com.cnetong.access.admin.domain.repository.SyncTaskRepository;
import com.cnetong.access.admin.service.SyncStatistician;
import com.cnetong.access.admin.service.SyncTaskService;
import com.cnetong.access.admin.support.SyncFactory;
import com.cnetong.access.core.ComponentConfigException;
import com.cnetong.access.core.RecordReader;
import com.cnetong.access.core.Schema;
import com.cnetong.access.core.SchemaField;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.schedule.EpeiusTaskScheduler;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSyncTaskService implements SyncTaskService, SyncStatistician {
    private final EpeiusTaskScheduler epeiusTaskScheduler;

    private final SyncFactory syncFactory;
    private final MapperFacade mapperFacade;

    private final SyncTaskRepository syncTaskRepository;
    private final SyncLogRepository syncLogRepository;

    @Override
    public Page<SyncTask> queryTasks(SyncTaskQueryAction action) {
        var page = syncTaskRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SyncTask createTask(SyncTaskDto dto) {
        SyncTask task = new SyncTask();
        updateTask(task, dto);
        saveTask(task);
        return task;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SyncTask updateTask(SyncTask task, SyncTaskDto dto) {
        task.setTitle(StringUtils.trim(dto.getTitle()));
        task.setMemo(StringUtils.trim(dto.getMemo()));
        task.setCron(StringUtils.trim(dto.getCron()));
        task.setDailyTime(dto.getDailyTime());
        task.setInterval(dto.getInterval());
        task.setReaderType(dto.getReaderType());
        task.setReaderConfig(dto.getReaderConfig());
        task.setReaderRuntime(dto.getReaderRuntime());

        // 输出目标
        task.getWriters().clear();
        for (SyncTaskWriterDto writerDto : dto.getWriters()) {
            task.getWriters().add(mapperFacade.map(writerDto, SyncTaskWriter.class));
        }


        // 保存字段映射
        task.getMappings().clear();
        for (SyncTaskMappingDto mappingDto : dto.getMappings()) {
            SyncTaskMapping mapping = mapperFacade.map(mappingDto, SyncTaskMapping.class);
            mapping.setTargetName(StringUtils.trim(mapping.getTargetName()));
            mapping.setTransformer(StringUtils.trim(mapping.getTransformer()));
            task.getMappings().add(mapping);
        }

        saveTask(task);
        return task;
    }


    /**
     * 整理字段映射
     *
     * @param syncTask 计划
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adjustMapping(SyncTask syncTask) {
        try (RecordReader recordReader = syncFactory.createReader(syncTask)) {
            var collections = recordReader.readSchemas(syncTask.getReaderConfig())
                    .stream()
                    .map(Schema::getName)
                    .collect(Collectors.toList());
            syncTask.setCollections(collections);

            List<SyncTaskMapping> allMappings = new ArrayList<>();
            for (Schema schema : recordReader.readSchemas(syncTask.getReaderConfig())) {
                for (SchemaField schemaField : schema.getFields()) {

                    // 已经存在的，保留旧的,防止被人修改过
                    SyncTaskMapping mapping = null;
                    for (SyncTaskMapping check : syncTask.getMappings()) {
                        if (StringUtils.equals(check.getCollection(), schema.getName()) && StringUtils.equals(check.getSourceName(), schemaField.getName())) {
                            mapping = check;
                        }
                    }

                    if (null == mapping) {
                        mapping = new SyncTaskMapping();
                        mapping.setCollection(schema.getName());
                        mapping.setSourceName(schemaField.getName());
                        mapping.setTargetName(schemaField.getName());
                    }
                    allMappings.add(mapping);
                }
            }
            syncTask.getMappings().clear();
            syncTask.setMappings(allMappings);
            syncTaskRepository.clearMapping();
            syncTaskRepository.saveAndFlush(syncTask);
        } catch (ComponentConfigException e) {
            log.error("数据字段处理失败", e);
            throw ProcessRejectException.of("数据字段处理失败:" + e.getMessage());
        } catch (Exception e) {
            log.error("数据字段处理失败", e);
            throw ProcessRejectException.of("数据字段处理失败:" + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    @Override
    public Optional<SyncTask> getTask(String id) {
        return syncTaskRepository.findById(id);
    }

    @Override
    public SyncTask requireTask(String id) {
        return this.getTask(id).orElseThrow(() -> ProcessRejectException.of("计划不存在"));
    }

    private void saveTask(SyncTask task) {
        adjustMapping(task);
        syncTaskRepository.save(task);
    }

    @Override
    public void deleteTask(SyncTask exist) {
        syncTaskRepository.delete(exist);
        epeiusTaskScheduler.cancel(exist.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startTask(SyncTask exist) {
        exist.setHealth(true);
        exist.setError(null);
        exist.setStarted(true);

        var task = syncFactory.createTask(exist);


        // 周期
        if (Objects.nonNull(exist.getInterval())) {
            var plan = epeiusTaskScheduler.schedule(task, exist.getInterval(), ChronoUnit.SECONDS);
            exist.setExecutionTime(plan.getNextExecuteTime());
        }
        // 每天
        else if (Objects.nonNull(exist.getDailyTime())) {
            var plan = epeiusTaskScheduler.schedule(task, exist.getDailyTime());
            exist.setExecutionTime(plan.getNextExecuteTime());
        }
        // Cron
        else if (StringUtils.isNotBlank(exist.getCron())) {
            var plan = epeiusTaskScheduler.schedule(task, exist.getCron());
            exist.setExecutionTime(plan.getNextExecuteTime());
        }
        syncTaskRepository.save(exist);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTask(SyncTask exist) {
        exist.setStarted(false);
        exist.setCurrentCompleted(0L);
        exist.setExecutionTime(null);
        syncTaskRepository.save(exist);
        epeiusTaskScheduler.cancel(exist.getId());
    }

    @Override
    public void runTask(SyncTask exist, Map<String, String> runtimes) {
        exist.setHealth(true);
        exist.setError(null);
        exist.setCurrentCompleted(0L);
        exist.getReaderRuntime().putAll(runtimes);
        var task = syncFactory.createTask(exist);
        var plan = epeiusTaskScheduler.execute(task);
        exist.setExecutionTime(plan.getNextExecuteTime());
        syncTaskRepository.save(exist);
    }

    @Override
    public void stopTask(SyncTask exist) {
        exist.setRunning(false);
        syncTaskRepository.save(exist);
        epeiusTaskScheduler.cancel(exist.getId());
    }


    @Override
    public long statCount() {
        return syncTaskRepository.count();
    }

    @Override
    public long statStartedCount() {
        return syncTaskRepository.countByStarted(true);
    }

    @Override
    public long statRecentlyErrorCount(LocalDateTime start) {
        return syncLogRepository.countBySuccess(false, start);
    }

}
