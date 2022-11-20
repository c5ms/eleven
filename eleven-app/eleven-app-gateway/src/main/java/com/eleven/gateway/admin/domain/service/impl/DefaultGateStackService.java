package com.eleven.gateway.admin.domain.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.basic.service.SerialGenerator;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.admin.domain.action.GateStackQueryAction;
import com.eleven.gateway.admin.domain.entity.GateStack;
import com.eleven.gateway.admin.domain.event.GatewayStacksUpdatedEvent;
import com.eleven.gateway.admin.domain.repository.GateStackRepository;
import com.eleven.gateway.admin.domain.service.GateStackService;
import com.eleven.gateway.admin.support.GatewayFactory;
import com.eleven.gateway.core.GatewayStack;
import com.eleven.gateway.core.GatewayStackLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGateStackService implements GateStackService, GatewayStackLoader {
    private final GateStackRepository gateStackRepository;
    private final GatewayFactory gatewayFactory;
    private final SerialGenerator serialGenerator;

    @Override
    public Collection<GateStack> queryStack(GateStackQueryAction action) {
        var sort = Sort.by(GateStack.Fields.order).ascending();
        return gateStackRepository.findAll(sort);
    }

    @Override
    public Optional<GateStack> findStack(String id) {
        return gateStackRepository.findById(id);
    }

    @Override
    public GateStack saveStack(GateStack gateStack) {
        //  修改
        if (gateStack.hasId()) {
            var exist = this.require(gateStack.getId());
            exist.update(gateStack);
            doSave(exist);
            return exist;
        }
        // 新增
        gateStack.setId(serialGenerator.nextSerialWithPrefix(GateAdminConstants.STACK_ID_PREFIX));
        doSave(gateStack);
        return gateStack;
    }

    private void doSave(GateStack gateStack) {
        gateStackRepository.save(gateStack);
        if (gateStack.isPublished()) {
            this.publish(gateStack);
        } else {
            this.cancel(gateStack);
        }
    }

    @Override
    @Transactional
    public void deleteStack(String id) {
        gateStackRepository.findById(id)
                .ifPresent(gateStackRepository::delete);
    }

    @Override
    public GateStack require(String id) {
        return findStack(id).orElseThrow(() -> ProcessRejectException.of("站点不存在"));
    }

    @Override
    public void publish(GateStack gateStack) {
        try {
            gatewayFactory.createStack(gateStack);
            gateStack.publish();
            gateStack.clearError();
        } finally {
            gateStackRepository.save(gateStack);
            SpringUtil.publishEvent(new GatewayStacksUpdatedEvent());
        }
    }

    @Override
    public void cancel(GateStack gateStack) {
        gateStack.cancel();
        gateStackRepository.save(gateStack);
        SpringUtil.publishEvent(new GatewayStacksUpdatedEvent());
    }

    @Override
    @Transactional
    public Collection<GatewayStack> loadStacks() {
        return gateStackRepository.findPublishedServers(true)
                .stream()
                .map(gatewayFactory::createStack)
                .filter(gatewayStack -> !gatewayStack.getRoutes().isEmpty())
                .collect(Collectors.toList());
    }
}
