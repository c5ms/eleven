package com.eleven.gateway.admin.domain.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HtmlUtil;
import com.cnetong.basic.service.SerialGenerator;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.query.domain.QuerySort;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.admin.domain.action.GateApiQueryAction;
import com.eleven.gateway.admin.domain.entity.GateApi;
import com.eleven.gateway.admin.domain.event.GatewayRouteUpdatedEvent;
import com.eleven.gateway.admin.domain.repository.GateApiRepository;
import com.eleven.gateway.admin.domain.service.GateApiService;
import com.eleven.gateway.admin.support.GatewayFactory;
import com.eleven.gateway.core.GatewayRoute;
import com.eleven.gateway.core.GatewayRouteLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGateApiService implements GateApiService, GatewayRouteLoader {
    private final GateApiRepository gateApiRepository;
    private final GatewayFactory gatewayFactory;
    private final SerialGenerator serialGenerator;

    @Override
    public Page<GateApi> queryApi(GateApiQueryAction action) {
        action.sortBy(GateApi.Fields.order, QuerySort.Direction.ASC);
        var page = gateApiRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public Optional<GateApi> findApi(String id) {
        return gateApiRepository.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GateApi saveApi(GateApi gateApi) {
        if(StringUtils.isBlank(HtmlUtil.cleanHtmlTag(gateApi.getDescription()))){
            gateApi.setDescription(null);
        }

        //  修改
        if (StringUtils.isNotBlank(gateApi.getId())) {
            GateApi exist = this.require(gateApi.getId());
            exist.update(gateApi);
            doSave(exist);
            return exist;
        }

        // 新增
        gateApi.setId(serialGenerator.nextSerialWithPrefix(GateAdminConstants.API_ID_PREFIX));
        doSave(gateApi);
        return gateApi;
    }

    private void doSave(GateApi gateApi) {
        gateApiRepository.save(gateApi);
        if (gateApi.isPublished()) {
            this.publishApi(gateApi);
        } else {
            this.cancelApi(gateApi);
        }
    }

    @Override
    public void deleteApi(String id) {
        this.findApi(id).ifPresent(route -> {
            gateApiRepository.delete(route);
            SpringUtil.publishEvent(new GatewayRouteUpdatedEvent());
        });
    }

    @Override
    public GateApi require(String id) {
        return findApi(id).orElseThrow(() -> ProcessRejectException.of("API不存在"));
    }

    @Override
    public void publishApi(GateApi gateApi) {
        try {
            gatewayFactory.createRoute(gateApi);
            gateApi.publish();
            gateApi.clearError();
        } catch (Exception e) {
            log.debug("API解析失败", e);
            gateApi.error(e);
            gateApi.cancel();
            throw ProcessRejectException.of("API解析失败");
        } finally {
            gateApiRepository.save(gateApi);
            SpringUtil.publishEvent(new GatewayRouteUpdatedEvent());
        }
    }

    @Override
    public void cancelApi(GateApi gateApi) {
        gateApi.cancel();
        gateApiRepository.save(gateApi);
        SpringUtil.publishEvent(new GatewayRouteUpdatedEvent());
    }

    @Override
    public Collection<GatewayRoute> load() {
        return gateApiRepository.findPublishedApis(true)
                .stream()
                .map(gatewayFactory::createRoute)
                .collect(Collectors.toList());
    }
}

