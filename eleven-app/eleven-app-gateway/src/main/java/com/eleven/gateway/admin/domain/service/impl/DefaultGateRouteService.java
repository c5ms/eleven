package com.eleven.gateway.admin.domain.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.basic.service.SerialGenerator;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.query.domain.QuerySort;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.core.domain.PaginationResult;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.admin.domain.action.GateRouteQueryAction;
import com.eleven.gateway.admin.domain.entity.GateRoute;
import com.eleven.gateway.admin.domain.entity.GateStack;
import com.eleven.gateway.admin.domain.event.GatewayRouteUpdatedEvent;
import com.eleven.gateway.admin.domain.event.GatewayStacksUpdatedEvent;
import com.eleven.gateway.admin.domain.repository.GateRouteRepository;
import com.eleven.gateway.admin.domain.service.GateRouteService;
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
public class DefaultGateRouteService implements GateRouteService, GatewayRouteLoader {
    private final GateRouteRepository gateRouteRepository;
    private final GatewayFactory gatewayFactory;
    private final SerialGenerator serialGenerator;

    @Override
    public PaginationResult<GateRoute> queryRoute(GateRouteQueryAction action) {
        action.sortBy(GateRoute.Fields.order, QuerySort.Direction.ASC);
        var page = gateRouteRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public Optional<GateRoute> findRoute(String id) {
        return gateRouteRepository.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GateRoute saveRoute(GateRoute gateRoute) {
        //  修改
        if (StringUtils.isNotBlank(gateRoute.getId())) {
            GateRoute exist = this.require(gateRoute.getId());
            exist.update(gateRoute);
            doSave(exist);
            return exist;
        }

        // 新增
        gateRoute.setId(serialGenerator.nextSerialWithPrefix(GateAdminConstants.ROUTE_ID_PREFIX));
        doSave(gateRoute);
        return gateRoute;
    }

    private void doSave(GateRoute gateRoute) {
        gateRouteRepository.save(gateRoute);
        if (gateRoute.isPublished()) {
            this.publishRoute(gateRoute);
        } else {
            this.cancelRoute(gateRoute);
        }
    }

    @Override
    public void deleteRoute(String id) {
        this.findRoute(id).ifPresent(route -> {
            gateRouteRepository.delete(route);
            SpringUtil.publishEvent(new GatewayRouteUpdatedEvent());
        });
    }

    @Override
    public GateRoute require(String id) {
        return findRoute(id).orElseThrow(() -> ProcessRejectException.of("路由不存在"));
    }


    @Override
    public void publishRoute(GateRoute gateRoute) {
        try {
            gatewayFactory.createRoute(gateRoute);
            gateRoute.publish();
            gateRoute.clearError();
        } catch (Exception e) {
            log.debug("路由解析失败", e);
            gateRoute.error(e);
            gateRoute.cancel();
            throw ProcessRejectException.of("路由解析失败");
        } finally {
            gateRouteRepository.save(gateRoute);
            if (StringUtils.isNotBlank(gateRoute.getStackId())) {
                SpringUtil.publishEvent(new GatewayStacksUpdatedEvent());
            } else {
                SpringUtil.publishEvent(new GatewayRouteUpdatedEvent());
            }
        }
    }

    @Override
    public void cancelRoute(GateRoute gateRoute) {
        gateRoute.cancel();
        gateRouteRepository.save(gateRoute);
        if (StringUtils.isNotBlank(gateRoute.getStackId())) {
            SpringUtil.publishEvent(new GatewayStacksUpdatedEvent());
        } else {
            SpringUtil.publishEvent(new GatewayRouteUpdatedEvent());
        }
    }

    @Override
    public Collection<GateRoute> listRouteByStackId(GateStack gateStack) {
        return gateRouteRepository.findByStackIdOrderByOrderAsc(gateStack.getId());
    }

    @Override
    public Collection<GateRoute> listRoutes(GateRouteQueryAction action) {
        action.sortBy(GateRoute.Fields.order, QuerySort.Direction.ASC);
        return gateRouteRepository.findAll(action.toSpecification());
    }

    @Override
    @Transactional
    public Collection<GatewayRoute> load() {
        return gateRouteRepository.findGlobalRoutes(true)
                .stream()
                .map(gatewayFactory::createRoute)
                .collect(Collectors.toList());
    }
}
