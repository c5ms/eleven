package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.action.SyncTaskQueryAction;
import com.cnetong.access.admin.domain.dto.SyncTaskDto;
import com.cnetong.access.admin.domain.entity.SyncTask;
import com.cnetong.common.query.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface SyncTaskService {

    Page<SyncTask> queryTasks(SyncTaskQueryAction action);

    SyncTask createTask(SyncTaskDto dto) throws Exception;

    void adjustMapping(SyncTask syncTask);

    SyncTask updateTask(SyncTask exist, SyncTaskDto dto) throws Exception;

    void deleteTask(SyncTask syncTask);

    Optional<SyncTask> getTask(String id);

    SyncTask requireTask(String id);

    void startTask(SyncTask exist);

    void cancelTask(SyncTask exist);

    void stopTask(SyncTask exist);

    void runTask(SyncTask exist, Map<String, String> runtimes);
}
