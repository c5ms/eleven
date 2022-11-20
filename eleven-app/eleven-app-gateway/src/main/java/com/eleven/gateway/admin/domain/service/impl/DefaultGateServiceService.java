package com.eleven.gateway.admin.domain.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.basic.service.SerialGenerator;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.admin.domain.action.GateServiceQueryAction;
import com.eleven.gateway.admin.domain.entity.GateService;
import com.eleven.gateway.admin.domain.event.GatewayServiceUpdatedEvent;
import com.eleven.gateway.admin.domain.repository.GateServiceRepository;
import com.eleven.gateway.admin.domain.service.GateServiceService;
import com.eleven.gateway.admin.support.GatewayFactory;
import com.eleven.gateway.core.GatewayService;
import com.eleven.gateway.core.GatewayServiceLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGateServiceService implements GateServiceService, GatewayServiceLoader {


    private final GatewayFactory gatewayFactory;
    private final SerialGenerator serialGenerator;
    private final GateServiceRepository gateServiceRepository;

    @Override
    public Page<GateService> queryService(GateServiceQueryAction action) {
        var page = gateServiceRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public Optional<GateService> findService(String id) {
        return gateServiceRepository.findById(id);
    }

    @Override
    public GateService saveService(GateService gateService) {
        if (StringUtils.isNotBlank(gateService.getId())) {
            GateService exist = this.require(gateService.getId());
            exist.update(gateService);
            gatewayFactory.createService(gateService);
            gateServiceRepository.save(exist);
            SpringUtil.publishEvent(new GatewayServiceUpdatedEvent());
            return exist;
        }
        gatewayFactory.createService(gateService);
        gateService.setId(serialGenerator.nextSerialWithPrefix(GateAdminConstants.SERVICE_ID_PREFIX));
        gateServiceRepository.save(gateService);
        SpringUtil.publishEvent(new GatewayServiceUpdatedEvent());
        return gateService;
    }


    @Override
    public void deleteService(String id) {
        this.findService(id).ifPresent(definition -> {
            gateServiceRepository.delete(definition);
            SpringUtil.publishEvent(new GatewayServiceUpdatedEvent());
        });
    }

    @Override
    public Collection<GateService> listService() {
        return gateServiceRepository.findAll();
    }


    private GateService require(String id) {
        return findService(id).orElseThrow(() -> ProcessRejectException.of("服务不存在"));
    }

    @Override
    public Collection<GatewayService> load() {
        return gateServiceRepository.findAll().stream()
                .map(gatewayFactory::createService)
                .collect(Collectors.toList());
    }


}
