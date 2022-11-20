package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.action.SyncLogQueryAction;
import com.cnetong.access.admin.domain.entity.SyncLog;
import com.cnetong.common.query.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public interface SyncLogService {
    Optional<SyncLog> find(String id);

    SyncLog require(String requestId);

    Page<SyncLog> query(SyncLogQueryAction build);

    void save(SyncLog log);

    void deleteDaysAgo(LocalDateTime limit);
}
