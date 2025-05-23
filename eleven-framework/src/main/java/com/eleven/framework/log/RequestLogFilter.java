package com.eleven.framework.log;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import cn.hutool.core.net.NetUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eleven.framework.security.SecurityContext;
import com.eleven.framework.security.Subject;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.DispatcherServlet;

@Slf4j
@Order
@Component
@WebFilter("/*")
@RequiredArgsConstructor
public class RequestLogFilter extends OncePerRequestFilter {

    public static final String TRACE_TAG_PRINCIPAL = "principal";
    public static final String TRACE_TAG_PRINCIPAL_TYPE = "principal.type";

    public static final String HTTP_HEADER_SERVICE_PROVIDER = "X-Service-Provider";
    public static final String HTTP_HEADER_TRACE_ID = "X-Trace-Id";

    private final String serverIp = NetUtil.getLocalhostStr();
    private final List<RequestLogAppender> logAppender;
    private final Optional<Tracer> tracer;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain chain) throws ServletException, IOException {
        RequestLog requestLog = prepareLog(request);
        RequestLogContext.markUnPersist();
        RequestLogContext.setLog(requestLog);
        try {
            response.setHeader(HTTP_HEADER_SERVICE_PROVIDER, SpringUtil.getApplicationName());
            response.setHeader(HTTP_HEADER_TRACE_ID, requestLog.getTraceId());
            chain.doFilter(request, response);
        } catch (Throwable e) {
            RequestLogContext.setCurrentError(e);
            throw e;
        } finally {
            try {
                requestLog.setResponseTime(LocalDateTime.now());
                requestLog.setDuration(Duration.between(requestLog.getRequestTime(), requestLog.getResponseTime()).toMillis());
                requestLog.getResponse().setStatus(response.getStatus());

                Subject subject = SecurityContext.getCurrentSubject();
                requestLog.setUserId(subject.getUserId());

                var se = (Throwable) request.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE);
                if (null != se) {
                    RequestLogContext.setCurrentError(se);
                }

                boolean ignore = request.getRequestURL().toString().endsWith("/actuator/health");

                if (/*(RequestLogContext.isDurable() || RequestLogContext.isErrored()) &&*/ !ignore ) {
                    for (RequestLogAppender logAppender : logAppender) {
                        logAppender.append(requestLog);
                    }
                }
            } catch (Exception e) {
                log.error("系统日志记录失败", e);
            }

            RequestLogContext.clean();
        }
    }

    @NotNull
    private RequestLog prepareLog(HttpServletRequest request) {
        RequestLog requestLog = new RequestLog();
        requestLog.setServerIp(serverIp);
        requestLog.setServiceName(SpringUtil.getApplicationName());
        requestLog.setRequestTime(LocalDateTime.now());
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
        tracer.map(Tracer::currentSpan).ifPresent(span -> {
            requestLog.setTraceId(span.context().traceId());

            var spanName = request.getMethod() + " " + request.getServletPath();
            span.name(spanName);

            Optional.ofNullable(subject.getPrincipal()).ifPresentOrElse(principal -> {
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
