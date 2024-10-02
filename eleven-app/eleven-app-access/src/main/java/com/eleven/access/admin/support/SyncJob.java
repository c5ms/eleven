package com.eleven.access.admin.support;

import com.cnetong.access.admin.domain.entity.SyncLog;
import com.cnetong.access.admin.domain.repository.SyncTaskRepository;
import com.cnetong.access.admin.service.SyncLogService;
import com.cnetong.access.core.ComponentConfigException;
import com.cnetong.access.core.RecordWriter;
import com.cnetong.access.core.ResourceException;
import com.cnetong.access.core.support.DefaultRecordChannel;
import com.cnetong.access.core.support.TaskStoppedException;
import com.cnetong.common.schedule.EpeiusJob;
import com.cnetong.common.schedule.EpeiusTaskExecution;
import com.cnetong.common.schedule.annonation.AsEpeiusJob;
import com.cnetong.common.time.TimeContext;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AsEpeiusJob(label = "离线同步", name = "record_sync")
@RequiredArgsConstructor
public class SyncJob implements EpeiusJob {

    private final SyncFactory syncFactory;
    private final SyncLogService syncLogService;
    private final SyncTaskRepository syncTaskRepository;

    private final static String ATTR_TASK_ID = "sync.task.id";
    private final static String ATTR_CHANNEL = "sync.job.channel";

    @Override
    public void execute(EpeiusTaskExecution execution) throws Exception {

        var taskId = execution.getTask().getId();
        var channel = new DefaultRecordChannel();
        var syncTask = syncTaskRepository.getReferenceById(taskId);

        log.info("同步任务执行开始[{}]", syncTask.getTitle());
        execution.setAttribute(ATTR_TASK_ID, taskId);
        execution.setAttribute(ATTR_CHANNEL, channel);

        syncTaskRepository.updateRunning(taskId, true, channel.getProcessCount());

        var recordReader = syncFactory.createReader(syncTask);
        var writers = syncTask.getWriters()
                .stream()
                .map(syncFactory::createWriter)
                .collect(Collectors.toList());

        writers.forEach(channel::addWriter);
        channel.addConverter(syncFactory.createConvertor(syncTask));

        execution.registerCloseable(recordReader);
        execution.registerCloseable(channel);
        execution.registerCloseable(writers);

        var runtime= syncTask.getReaderRuntime();
        execution.setAttribute("reader.runtime",runtime);

        recordReader.start(channel, syncTask.getReaderConfig(), runtime);
        for (RecordWriter writer : writers) {
            writer.flush();
            if (Thread.interrupted()) {
                throw new TaskStoppedException("任务被强制终止");
            }
        }

        if (writers.isEmpty()) {
            throw ProcessRejectException.of("无写入目标");
        }

    }


    @Override
    public void check(EpeiusTaskExecution execution) {
        var taskId = (String) execution.getAttribute(ATTR_TASK_ID);
        var channel = (DefaultRecordChannel) execution.getAttribute(ATTR_CHANNEL);
        if (null != channel && null != taskId) {
            syncTaskRepository.updateRunning(taskId, true, channel.getProcessCount());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void complete(EpeiusTaskExecution execution) {
        var taskId = (String) execution.getAttribute(ATTR_TASK_ID);
        var channel = (DefaultRecordChannel) execution.getAttribute(ATTR_CHANNEL);
        var exception = execution.getError();

        // 记录日志
        SyncLog syncLog = new SyncLog();
        syncLog.setScheduleId(taskId);
        syncLog.setStartTime(execution.getStartTime());
        syncLog.setEndTime(TimeContext.localDateTime());
        syncLog.setDuration(execution.getDuration().toMillis());
        syncLog.setSuccess(true);
        if (null != channel) {
            syncLog.setTotalCount(channel.getProcessCount());
        }
        if (null != exception) {
            syncLog.setSuccess(false);
            syncLog.setExceptionMessage(StringUtils.substring(ExceptionUtils.getMessage(exception), 0, 1024));
            syncLog.setExceptionStack(ExceptionUtils.getStackTrace(exception));
            if (exception instanceof ComponentConfigException) {
                syncLog.setExceptionMessage(exception.getMessage());
            }
            if (exception instanceof ResourceException) {
                syncLog.setExceptionMessage(exception.getMessage());
            }
            if (exception instanceof ProcessRejectException) {
                syncLog.setExceptionMessage(exception.getMessage());
            }
        }
        if (execution.isInterrupted()) {
            syncLog.setSuccess(false);
            syncLog.setExceptionMessage("任务被中断");
        }
        if (syncLog.getTotalCount() > 0 || !syncLog.getSuccess()) {
            syncLogService.save(syncLog);
        }

        var syncTask = syncTaskRepository.getReferenceById(taskId);
        syncTask.setError(syncLog.getExceptionMessage());
        syncTask.setHealth(syncLog.getSuccess());
        syncTask.setLastExecuteTime(syncLog.getStartTime());
        syncTask.setRunning(false);
        syncTask.setReaderRuntime((Map<String, String>) execution.getAttribute("reader.runtime"));
        syncTask.setCurrentCompleted(syncLog.getTotalCount());
        syncTask.setExecutionTime(TimeContext.localDateTime(execution.nextExecuteTime()));
        syncTaskRepository.save(syncTask);
    }
}
