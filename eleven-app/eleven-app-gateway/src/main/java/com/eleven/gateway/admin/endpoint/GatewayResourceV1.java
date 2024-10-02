package com.eleven.gateway.admin.endpoint;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.common.time.TimeContext;
import com.cnetong.common.web.RestApi;
import com.eleven.gateway.admin.domain.dto.GatewayState;
import com.eleven.gateway.admin.domain.dto.GatewayStatics;
import com.eleven.gateway.admin.domain.event.GatewayRefreshEvent;
import com.eleven.gateway.admin.domain.service.GateStaticsService;
import com.eleven.gateway.admin.domain.service.GatewayConfigService;
import com.eleven.gateway.admin.support.GatewayResourceProvider;
import com.eleven.gateway.admin.support.GatewayRouteProvider;
import com.eleven.gateway.admin.support.GatewayServiceProvider;
import com.eleven.gateway.admin.support.GatewayStackProvider;
import com.eleven.gateway.core.*;
import com.cnetong.logs.annonation.UseRequestLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * 提供安全访问环境接口
 */
@Slf4j
@Tag(name = "网关")
@RestApi
@RequestMapping("/gate/server")
@RequiredArgsConstructor
public class GatewayResourceV1 {

    private final GatewayServer gatewayServer;
    private final GatewayRouteProvider gatewayRouteProvider;
    private final GatewayStackProvider gatewayStackProvider;
    private final GatewayServiceProvider gatewayServiceProvider;
    private final GatewayResourceProvider gatewayResourceProvider;
    private final GateStaticsService gateStaticsService;
    private final GatewayConfigService gatewayConfigService;

    @UseRequestLog
    @Operation(summary = "启动服务")
    @PostMapping("/start")
    public void startServer() {
        gatewayServer.start();
    }

    @UseRequestLog
    @Operation(summary = "停止服务")
    @PostMapping("/stop")
    public void stopServer() {
        gatewayServer.stop();
    }

    @UseRequestLog
    @Operation(summary = "保存配置")
    @PostMapping("/config")
    public void saveConfig(@Validated @RequestBody GatewayConfig config) {
        gatewayConfigService.saveConfig(config);
    }

    @Operation(summary = "读取配置")
    @GetMapping("/config")
    public GatewayConfig getConfig() {
        return gatewayConfigService.getConfig();
    }

    @Operation(summary = "刷新缓存")
    @PostMapping("/refresh")
    public void refresh() {
        SpringUtil.publishEvent(new GatewayRefreshEvent());
    }


    @Operation(summary = "运行状态")
    @GetMapping("/state")
    public GatewayState state() {
        return GatewayState.builder()
                .running(gatewayServer.isRunning())
                .build();
    }

    @Operation(summary = "路由列表")
    @GetMapping("/routes")
    public Collection<GatewayRoute> routes() {
        return gatewayRouteProvider.getInstances();
    }

    @Operation(summary = "站点列表")
    @GetMapping("/stacks")
    public Collection<GatewayStack> stacks() {
        return gatewayStackProvider.getInstances();
    }

    @Operation(summary = "服务列表")
    @GetMapping("/services")
    public Collection<GatewayService> services() {
        return gatewayServiceProvider.getInstances();
    }


    @Operation(summary = "资源列表")
    @GetMapping("/resources")
    public Collection<GatewayResource> resources() {
        return gatewayResourceProvider.getInstances();
    }

    @Operation(summary = "统计信息")
    @GetMapping("/statics")
    public GatewayStatics statics() {
        Collection<GatewayRoute> gatewayRoutes = gatewayRouteProvider.getInstances();
        Collection<GatewayStack> gatewayStacks = gatewayStackProvider.getInstances();
        Collection<GatewayService> gatewayServices = gatewayServiceProvider.getInstances();
        return GatewayStatics.builder()
                .qps(gateStaticsService.qps())
                .requests(gateStaticsService.requests(TimeContext.localDate()))
                .routes(gateStaticsService.routes())
                .numberRoutes(gatewayRoutes.size())
                .numberStacks(gatewayStacks.size())
                .numberServices(gatewayServices.size())
                .build();
    }

}
