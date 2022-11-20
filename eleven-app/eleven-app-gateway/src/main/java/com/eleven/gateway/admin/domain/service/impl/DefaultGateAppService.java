package com.eleven.gateway.admin.domain.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.basic.service.SerialGenerator;
import com.cnetong.common.domain.AbstractDomain;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.admin.domain.action.GateAppGrantApiAction;
import com.eleven.gateway.admin.domain.action.GateAppQueryAction;
import com.eleven.gateway.admin.domain.action.GateAppSaveAction;
import com.eleven.gateway.admin.domain.entity.GateApi;
import com.eleven.gateway.admin.domain.entity.GateApp;
import com.eleven.gateway.admin.domain.event.GatewayAppUpdatedEvent;
import com.eleven.gateway.admin.domain.event.GatewayTokenUpdatedEvent;
import com.eleven.gateway.admin.domain.repository.GateApiRepository;
import com.eleven.gateway.admin.domain.repository.GateAppRepository;
import com.eleven.gateway.admin.domain.service.GateAppService;
import com.eleven.gateway.admin.support.GatewayFactory;
import com.eleven.gateway.core.GatewayApp;
import com.eleven.gateway.core.GatewayAppLoader;
import com.eleven.gateway.core.GatewayToken;
import com.eleven.gateway.core.GatewayTokenLoader;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yz
 */
@Service
@RequiredArgsConstructor
public class DefaultGateAppService implements GateAppService, GatewayAppLoader, GatewayTokenLoader {

    private final GatewayFactory gatewayFactory;

    private final GateAppRepository gateAppRepository;
    private final GateApiRepository gateApiRepository;

    private final SerialGenerator serialGenerator;

    @Override
    public GateApp require(String id) {
        return gateAppRepository.findById(id).orElseThrow(() -> ProcessRejectException.of("应用不存在"));
    }

    @Override
    public Page<GateApp> queryApp(GateAppQueryAction action) {
        var page = gateAppRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public Collection<GateApp> listApp(GateAppQueryAction action) {
        return gateAppRepository.findAll(action.toSpecification(), Sort.by(AbstractDomain.Fields.createDate).descending());
    }

    @Override
    public void deleteApp(String id) {
        gateAppRepository.deleteById(id);
    }

    @Override
    public GateApp saveApp(GateAppSaveAction saveAction) {
        if (StringUtils.isNotBlank(saveAction.getId())) {
            GateApp app = this.require(saveAction.getId());
            app.modify(saveAction);
            check(app);
            app = gateAppRepository.save(app);
            SpringUtil.publishEvent(new GatewayAppUpdatedEvent());
            return app;
        }
        // 创建应用
        saveAction.setId(serialGenerator.nextSerialWithPrefix(GateAdminConstants.APP_ID_PREFIX));
        GateApp app = new GateApp(saveAction);
        check(app);
        app = gateAppRepository.save(app);
        SpringUtil.publishEvent(new GatewayAppUpdatedEvent());
        return app;
    }

    @Override
    public GateApp forbidApp(String id) {
        GateApp app = require(id);
        app.forbid();
        app = gateAppRepository.save(app);
        SpringUtil.publishEvent(new GatewayAppUpdatedEvent());
        return app;
    }

    @Override
    public GateApp createAppToken(String id) {
        GateApp app = require(id);
        app.getToken().setToken(IdUtil.simpleUUID().toUpperCase());
        SpringUtil.publishEvent(new GatewayTokenUpdatedEvent());
        app = gateAppRepository.save(app);
        return app;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantApi(String id, GateAppGrantApiAction action) {
        var app = require(id);
        app.getApis().addAll(action.getApis());
        gateAppRepository.save(app);
        SpringUtil.publishEvent(new GatewayAppUpdatedEvent());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelApi(String appId, String api) {
        var app = require(appId);
        app.getApis().remove(api);
        gateAppRepository.save(app);
        SpringUtil.publishEvent(new GatewayAppUpdatedEvent());
    }

    @Override
    public List<GateApi> listApi(String appId) {
        var app = require(appId);
        return gateApiRepository.findAllById(app.getApis());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Collection<GatewayApp> loadApps() {
        return gateAppRepository.findAll()
                .stream()
                .filter(app -> BooleanUtils.isFalse(app.getForbidden()))
                .map(gatewayFactory::createApp)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Collection<GatewayToken> loadTokens() {
        return gateAppRepository.findAll()
                .stream()
                .filter(app -> BooleanUtils.isFalse(app.getForbidden()))
                .map(gatewayFactory::createToken)
                .collect(Collectors.toList());
    }

    private void check(GateApp app) {
        gateAppRepository.findByAppId(app.getAppId()).ifPresent(check -> {
            if (!StringUtils.equals(check.getId(), app.getId())) {
                throw ProcessRejectException.of(String.format("APP_ID %s 已存在", app.getAppId()));
            }
        });
    }

}
