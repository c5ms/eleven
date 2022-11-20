package com.eleven.gateway.admin.domain.service.impl;

import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.query.domain.QuerySort;
import com.eleven.gateway.admin.domain.action.GateApiLogQueryAction;
import com.eleven.gateway.admin.domain.entity.GateApiAccessLog;
import com.eleven.gateway.admin.domain.repository.GateApiAccessLogRepository;
import com.eleven.gateway.admin.domain.service.GateApiAccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultGateApiAccessLogService implements GateApiAccessLogService {
    private final GateApiAccessLogRepository apiAccessLogRepository;

    @Override
    public Page<GateApiAccessLog> queryLogs(GateApiLogQueryAction action) {
        action.sortBy(GateApiAccessLog.Fields.accessTime, QuerySort.Direction.DESC);
        var page = apiAccessLogRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }
}
