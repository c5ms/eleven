package com.eleven.access.admin.service.impl;

import com.cnetong.access.admin.domain.action.SyncLogQueryAction;
import com.cnetong.access.admin.domain.entity.SyncLog;
import com.cnetong.access.admin.domain.repository.SyncLogRepository;
import com.cnetong.access.admin.service.SyncLogService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSyncLogService implements SyncLogService {
    private final SyncLogRepository syncLogRepository;

    @Override
    public Optional<SyncLog> find(String id) {
        return syncLogRepository.findById(id);
    }

    @Override
    public SyncLog require(String id) {
        return find(id).orElseThrow(() -> ProcessRejectException.of("日志不存在"));
    }

    @Override
    public Page<SyncLog> query(SyncLogQueryAction action) {
        var page = syncLogRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(SyncLog log) {
        syncLogRepository.save(log);
    }

    @Transactional
    @Override
    public void deleteDaysAgo(LocalDateTime limit) {
        syncLogRepository.deleteDaysAge(limit);
    }
}
