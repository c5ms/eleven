package com.eleven.core.web.log;

import cn.hutool.core.net.NetUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.security.SecurityContext;
import com.eleven.core.security.Subject;
import com.eleven.core.time.TimeHelper;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.DispatcherServlet;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Order(-1)
@Component
@WebFilter("/*")
@RequiredArgsConstructor
public class RequestLogFilter extends OncePerRequestFilter {

    public static final String TRACE_TAG_PRINCIPAL = "principal";
    public static final String TRACE_TAG_PRINCIPAL_TYPE = "principal.type";
    private final String serverIp;
    private final List<RequestLogAppender> requestLogAppender;

    public final String HTTP_HEADER_SERVICE_PROVIDER = "X-Service-Provider";

    @Autowired
    public RequestLogFilter(List<RequestLogAppender> requestLogAppender) {
        this.serverIp = NetUtil.getLocalhostStr();
        this.requestLogAppender = requestLogAppender;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        RequestLog requestLog = prepareLog(request);

        // http 链路追踪
        response.setHeader(HTTP_HEADER_SERVICE_PROVIDER, SpringUtil.getApplicationName());
        Throwable exception = null;
        try {
            RequestLogContext.markUnPersist();
            RequestLogContext.setLog(requestLog);
            chain.doFilter(request, response);
        } catch (Throwable e) {
            exception = e;
        } finally {
            try {
                requestLog.setResponseTime(TimeHelper.localDateTime());
                requestLog.setDuration(Duration.between(requestLog.getRequestTime(), requestLog.getResponseTime()).toMillis());
                requestLog.getResponse().setStatus(response.getStatus());

                Subject subject = SecurityContext.getCurrentSubject();
                requestLog.setUserId(subject.getUserId());

                if (HttpStatus.valueOf(response.getStatus()).is5xxServerError()) {
                    var se = (Throwable) request.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE);
                    if (null != se) {
                        exception = se;
                    }
                }

                if (HttpStatus.valueOf(response.getStatus()).is4xxClientError()) {
                    var se = (Throwable) request.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE);
                    if (null != se) {
                        exception = se;
                    }
                }

                if (null != exception) {
                    RequestLogContext.setCurrentError(exception);
                }
                boolean write = true;

                if (request.getRequestURL().toString().endsWith("/actuator/health")) {
                    write = false;
                }

                if (write) {
                    for (RequestLogAppender logAppender : requestLogAppender) {
                        logAppender.append(requestLog);
                    }
                }
            } catch (Exception e) {
                log.error("系统日志记录失败", e);
            } finally {
                RequestLogContext.clean();
            }
        }
    }

    @NotNull
    private RequestLog prepareLog(HttpServletRequest request) {
        RequestLog requestLog = new RequestLog();
        requestLog.setServerIp(serverIp);
        requestLog.setServiceName(SpringUtil.getApplicationName());
        requestLog.setRequestTime(TimeHelper.localDateTime());
        requestLog.setClientIp(JakartaServletUtil.getClientIP(request));
        requestLog.getRequest().setPath(request.getServletPath());
        requestLog.getRequest().setUrl(request.getRequestURL().toString());
        requestLog.getRequest().setMethod(request.getMethod().toUpperCase(Locale.ROOT));
        requestLog.getRequest().setContentType(request.getContentType());
        requestLog.getRequest().setParameter(request.getQueryString());
        requestLog.getRequest().setUpload(StringUtils.contains(request.getContentType(), MediaType.MULTIPART_FORM_DATA_VALUE));

        // 用户
        Subject subject = SecurityContext.getCurrentSubject();
        requestLog.setUserId(subject.getUserId());

        // 追踪 + 身份
        SpringUtil.getBeanFactory().getBeanProvider(Tracer.class).stream().findFirst().map(Tracer::currentSpan).ifPresent(span -> {
            requestLog.setTraceId(span.context().traceId());

            var spanName = request.getMethod() + " " + request.getServletPath();
            span.name(spanName);

            Optional.ofNullable(subject.getPrincipal())
                .ifPresentOrElse(principal -> {
                    span.tag(TRACE_TAG_PRINCIPAL, principal.getName());
                    span.tag(TRACE_TAG_PRINCIPAL_TYPE, principal.getType());
                }, () -> {
                    span.tag(TRACE_TAG_PRINCIPAL, "");
                    span.tag(TRACE_TAG_PRINCIPAL_TYPE, "");
                });
            log.trace("追踪用户身份[{}]：{},traceId:{}", spanName, subject.getNickName(), span.context().traceId());
        });

        return requestLog;
    }


}
