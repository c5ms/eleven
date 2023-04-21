package com.eleven.core.app.rest;

import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Service
public class RestRequestArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(RestRequest.class);
    }

    @Override
    public RestRequest resolveArgument(@Nonnull MethodParameter parameter,
                                       ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest,
                                       WebDataBinderFactory binderFactory) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        return RestRequest.builder()
                .ip(fixIp(JakartaServletUtil.getClientIP(httpServletRequest)))
                .build();
    }


    private String fixIp(String clientIp) {
        if (clientIp.equals("::1") || clientIp.equals("0:0:0:0:0:0:0:1") || clientIp.equals("127.0.0.1")) {
            clientIp = "127.0.0.1";
        }

        if (clientIp.startsWith("::ffff:")) {
            clientIp = clientIp.substring(7);
        }

        return clientIp;
    }

}