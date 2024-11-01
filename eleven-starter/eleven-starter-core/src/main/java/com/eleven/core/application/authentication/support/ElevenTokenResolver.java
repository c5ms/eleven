package com.eleven.core.application.authentication.support;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

public class ElevenTokenResolver implements BearerTokenResolver {
    public static final String HEADER_TOKEN_NAME = "X-access_token";
    public static final String QUERY_TOKEN_NAME = "access_token";
    public static final String COOKIE_TOKEN_NAME = "access_token";
    public static final String TOKEN_BEARER = "Bearer";

    @Override
    public String resolve(HttpServletRequest request) {

        // 通过 header 获取
        var token = request.getHeader(HEADER_TOKEN_NAME);

        // 通过 header.Authorization 获取
        if (StringUtils.isBlank(token)) {
            token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isNotBlank(token)) {
                token = token.trim();
                if (StringUtils.startsWithIgnoreCase(token, TOKEN_BEARER)) {
                    token = StringUtils.trim(StringUtils.substring(token, TOKEN_BEARER.length()));
                }
            }
        }

        // 通过 query/form 获取
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(QUERY_TOKEN_NAME);
        }

        // 通过参数
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(HEADER_TOKEN_NAME);
        }

        // 通过 Cookie 获取
        if (StringUtils.isBlank(token)) {
            if (null != request.getCookies()) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(COOKIE_TOKEN_NAME)) {
                        token = cookie.getValue();
                    }
                }
            }
        }

        return token;

    }
}
