package com.eleven.gateway.admin.domain.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.gateway.admin.domain.action.GateApiGroupSaveAction;
import com.eleven.gateway.admin.domain.entity.GateApi;
import com.eleven.gateway.admin.domain.entity.GateApiGroup;
import com.eleven.gateway.admin.domain.event.GatewayRouteUpdatedEvent;
import com.eleven.gateway.admin.domain.repository.GateApiGroupRepository;
import com.eleven.gateway.admin.domain.repository.GateApiRepository;
import com.eleven.gateway.admin.domain.service.GateApiGroupService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGateApiGroupService implements GateApiGroupService {
    private final GateApiGroupRepository apiGroupRepository;
    private final GateApiRepository gateApiRepository;

    @Override
    public GateApiGroup require(String id) {
        return apiGroupRepository.findById(id).orElseThrow(() -> ProcessRejectException.of("分组不存在"));
    }

    @Override
    public Collection<GateApiGroup> list() {
        return apiGroupRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGroup(String id) {
        apiGroupRepository.deleteById(id);
        gateApiRepository.deleteByGroup(id);
        SpringUtil.publishEvent(new GatewayRouteUpdatedEvent());
    }

    @Override
    public GateApiGroup saveGroup(GateApiGroupSaveAction saveAction) {
        if (StringUtils.isNotBlank(saveAction.getId())) {
            GateApiGroup group = this.require(saveAction.getId());
            group.modify(saveAction);
            group = apiGroupRepository.save(group);
            return group;
        }
        GateApiGroup group = new GateApiGroup(saveAction);
        group = apiGroupRepository.save(group);
        return group;
    }

    @Override
    public List<GateApi> listApi(String id) {
        return gateApiRepository.findAllByGroup(id);
    }
}
