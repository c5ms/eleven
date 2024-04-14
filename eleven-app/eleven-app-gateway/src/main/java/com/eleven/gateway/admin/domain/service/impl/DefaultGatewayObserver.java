package com.eleven.gateway.admin.domain.service.impl;

import com.cnetong.common.time.TimeContext;
import com.cnetong.common.time.TimeService;
import com.cnetong.common.util.QPSCalculator;
import com.eleven.gateway.admin.domain.entity.GateApiAccessLog;
import com.eleven.gateway.admin.domain.entity.GateRequestSummary;
import com.eleven.gateway.admin.domain.entity.GateRequestSummaryView;
import com.eleven.gateway.admin.domain.repository.*;
import com.eleven.gateway.admin.domain.service.GateStaticsService;
import com.eleven.gateway.admin.domain.statics.QpsSummary;
import com.eleven.gateway.admin.domain.statics.RequestSummary;
import com.eleven.gateway.admin.domain.statics.RouteSummary;
import com.eleven.gateway.core.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGatewayObserver implements GateStaticsService, InitializingBean, GatewayObserver {
    private final String QPS_REQUEST_NAME = "qps_request";
    private final String ATTR_OBV_START_ACCESS_TIME = "OBV_START_ACCESS_TIME";

    private final QPSCalculator qpsCalculator = new QPSCalculator(60, 1000);

    private final List<QpsSummary> summaryRequestHolder = new LinkedList<>();

    private final GatewayProvider<GatewayRoute> routeProvider;
    private final GatewayProvider<GatewayStack> stackProvider;

    private final Map<String, GatewayRouteStatics> routeStaticsMap = new ConcurrentHashMap<>();
    private final TimeService timeService;

    private final GateApiAccessLogRepository gateApiAccessLogRepository;
    private final GateAppRepository appRepository;
    private final GateApiRepository apiRepository;
    private final GateApiGroupRepository groupRepository;
    private final GateRequestSummaryRepository gateRequestSummaryRepository;

    private final RequestStaticsHolder requestStaticsHolder = new RequestStaticsHolder();

    /* stat api request count */
    private final List<GateApiAccessLog> apiLogsHolder = new ArrayList<>();


    @Override
    public void afterPropertiesSet() {
        requestStaticsHolder.init(timeService.getLocalDateTime());
    }

    @Override
    public void onRequest(ServerWebExchange exchange) {
        qpsCalculator.pass(QPS_REQUEST_NAME);
        requestStaticsHolder.getRequestCount().increment();
        exchange.getAttributes().put(ATTR_OBV_START_ACCESS_TIME, TimeContext.localDateTime());
    }

    @Override
    public void onRoute(ServerWebExchange exchange, GatewayRoute gatewayRoute) {
        requestStaticsHolder.getRouteCount().increment();
        var stat = routeStaticsMap.computeIfAbsent(gatewayRoute.getId(), GatewayRouteStatics::new);
        stat.hit();
    }

    @Override
    public void onError(ServerWebExchange exchange, GatewayRoute gatewayRoute, Throwable throwable) {
        requestStaticsHolder.getErrorCount().increment();
        log.error("网关路由异常", throwable);
    }

    @Override
    public void onRouteSuccess(ServerWebExchange exchange, GatewayRoute gatewayRoute) {
        var stat = routeStaticsMap.computeIfAbsent(gatewayRoute.getId(), GatewayRouteStatics::new);
        stat.success();
        logApiAccess(exchange, gatewayRoute);
    }

    @Override
    public void onRouteFailure(ServerWebExchange exchange, GatewayRoute gatewayRoute) {
        var stat = routeStaticsMap.computeIfAbsent(gatewayRoute.getId(), GatewayRouteStatics::new);
        stat.failure();
        logApiAccess(exchange, gatewayRoute);
    }

    @Scheduled(fixedDelay = 10 * 1000)
    public void snap() {
        QpsSummary qpsSummary = new QpsSummary()
            .setQps(qpsCalculator.current(QPS_REQUEST_NAME))
            .setAvgQps(qpsCalculator.avg(QPS_REQUEST_NAME))
            .setStatTime(timeService.getLocalDateTime());
        summaryRequestHolder.add(qpsSummary);
        if (summaryRequestHolder.size() > 30) {
            summaryRequestHolder.remove(0);
        }
    }


    @Transactional
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void flush() {
        log.debug("持久化统计数据");
        GateRequestSummary gateRequestSummary = new GateRequestSummary();
        gateRequestSummary.setRequestCount(requestStaticsHolder.getRequestCount().sum());
        gateRequestSummary.setRouteCount(requestStaticsHolder.getRouteCount().sum());
        gateRequestSummary.setErrorCount(requestStaticsHolder.getErrorCount().sum());
        gateRequestSummary.setStaticDate(TimeContext.localDate());
        gateRequestSummary.setStaticBeginDateTime(requestStaticsHolder.getBeginDateTime());
        gateRequestSummary.setStaticEndDateTime(TimeContext.localDateTime());
        gateRequestSummaryRepository.save(gateRequestSummary);
        requestStaticsHolder.init(timeService.getLocalDateTime());

        Map<String, AtomicLong> apiStats = new HashMap<>();
        Map<String, AtomicLong> appStats = new HashMap<>();
        Map<String, AtomicLong> groupStats = new HashMap<>();
        for (GateApiAccessLog accessLog : apiLogsHolder) {
            apiStats.computeIfAbsent(accessLog.getApiId(), s -> new AtomicLong()).incrementAndGet();
            appStats.computeIfAbsent(accessLog.getAppId(), s -> new AtomicLong()).incrementAndGet();
            groupStats.computeIfAbsent(accessLog.getGroupId(), s -> new AtomicLong()).incrementAndGet();

        }

        for (Map.Entry<String, AtomicLong> stringAtomicLongEntry : apiStats.entrySet()) {
            apiRepository.increaseRequestCount(stringAtomicLongEntry.getKey(), stringAtomicLongEntry.getValue().intValue());
        }

        for (Map.Entry<String, AtomicLong> stringAtomicLongEntry : appStats.entrySet()) {
            appRepository.increaseRequestCount(stringAtomicLongEntry.getKey(), stringAtomicLongEntry.getValue().intValue());
        }

        for (Map.Entry<String, AtomicLong> stringAtomicLongEntry : groupStats.entrySet()) {
            groupRepository.increaseRequestCount(stringAtomicLongEntry.getKey(), stringAtomicLongEntry.getValue().intValue());
        }

        gateApiAccessLogRepository.saveAll(apiLogsHolder);
        apiLogsHolder.clear();

    }

    @Override
    public List<QpsSummary> qps() {
        return summaryRequestHolder;
    }

    @Override
    public RequestSummary requests(LocalDate staticDate) {
        GateRequestSummaryView requestSummary = gateRequestSummaryRepository.summaryStatics(timeService.getLocalDate());
        return new RequestSummary()
            .setRequestCount(requestSummary.getRequestCount() + requestStaticsHolder.getRequestCount().sum())
            .setRouteCount(requestSummary.getRouteCount() + requestStaticsHolder.getRouteCount().sum())
            .setErrorCount(requestSummary.getErrorCount() + requestStaticsHolder.getErrorCount().sum())
            .setStaticDate(requestSummary.getStaticDate());
    }


    private void logApiAccess(ServerWebExchange exchange, GatewayRoute gatewayRoute) {
        if (gatewayRoute.getRouteMode() == GatewayRouteMode.api) {
            var accessTime = (LocalDateTime) exchange.getAttributes().get(ATTR_OBV_START_ACCESS_TIME);
            var app = (GatewayApp) exchange.getAttributes().get(GatewayConstants.GATEWAY_APP_ATTR);
            var error = (String) exchange.getResponse().getHeaders().getFirst(GatewayConstants.GATEWAY_HEADER_GATEWAY_ERROR);

            var groupId = gatewayRoute.getGroup();
            var apiId = StringUtils.substringBefore(gatewayRoute.getId(), "@");

            GateApiAccessLog accessLog = new GateApiAccessLog();
            accessLog.setAccessTime(timeService.getLocalDateTime());
            accessLog.setRemoteIp(exchange.getRequest().getHeaders().getFirst(GatewayConstants.GATEWAY_HEADER_FORWARDED_FOR));
            accessLog.setApiId(apiId);
            accessLog.setGroupId(groupId);
            accessLog.setAppId(Optional.ofNullable(app).map(GatewayApp::getId).orElse(null));
            accessLog.setAccessTime(accessTime);
            accessLog.setDuration(Duration.between(accessTime, timeService.getLocalDateTime()));
            accessLog.setSuccess(StringUtils.isBlank(error));
            accessLog.setError(StringUtils.substring(error, 0, 100));
            accessLog.setResponseStatus(exchange.getResponse().getStatusCode().value());
            accessLog.setTraceId(exchange.getResponse().getHeaders().getFirst(GatewayConstants.GATEWAY_HEADER_TRACE_ID));

            apiLogsHolder.add(accessLog);

        }

    }


    @Override
    public List<RouteSummary> routes() {
        List<RouteSummary> summaries = new ArrayList<>();
        routeProvider.getInstances()
            .stream()
            .map(gatewayRoute -> {
                var gatewayRouteStatics = routeStaticsMap.computeIfAbsent(gatewayRoute.getId(), GatewayRouteStatics::new);
                RouteSummary summary = new RouteSummary();
                BeanUtils.copyProperties(gatewayRouteStatics, summary);
                summary.setRouteId(gatewayRoute.getId());
                summary.setRouteName(gatewayRoute.getName());
                return summary;
            })
            .forEach(summaries::add);

        stackProvider.getInstances()
            .stream()
            .flatMap(gatewayStack -> {
                return gatewayStack.getRoutes().stream()
                    .map(gatewayRoute -> {
                        var gatewayRouteStatics = routeStaticsMap.computeIfAbsent(gatewayRoute.getId(), GatewayRouteStatics::new);
                        RouteSummary summary = new RouteSummary();
                        BeanUtils.copyProperties(gatewayRouteStatics, summary);
                        summary.setRouteId(gatewayRoute.getId());
                        summary.setRouteName(gatewayRoute.getName());
                        summary.setStackId(gatewayStack.getId());
                        summary.setStackName(gatewayStack.getName());
                        return summary;
                    });
            })
            .forEach(summaries::add);
        return summaries;

    }

    @Getter
    @RequiredArgsConstructor
    public static class GatewayRouteStatics {

        private final String id;

        private final LongAdder hits = new LongAdder();
        private final LongAdder successes = new LongAdder();
        private final LongAdder failures = new LongAdder();

        public void hit() {
            this.hits.increment();
        }

        public void success() {
            this.successes.increment();
        }

        public void failure() {
            this.failures.increment();
        }

    }

    @Getter
    public static class RequestStaticsHolder {
        private final LongAdder requestCount = new LongAdder();
        private final LongAdder routeCount = new LongAdder();
        private final LongAdder errorCount = new LongAdder();
        private volatile LocalDateTime beginDateTime;

        public void init(LocalDateTime beginDateTime) {
            this.beginDateTime = beginDateTime;
            this.requestCount.reset();
            this.errorCount.reset();
            this.routeCount.reset();
        }

    }

    @Data
    @Builder
    public static class TodayRequestSummary {
        private final Long requestCount;
        private final Long routeCount;
        private final Long errorCount;
        private final LocalDate staticDate;
    }
}
