package com.demcia.eleven.core.rest.filter;

import cn.hutool.extra.spring.SpringUtil;
import com.demcia.eleven.core.rest.constants.RestfulConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@Slf4j
@Component
@RequiredArgsConstructor
@WebFilter("/*")
public class WebTracingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader(RestfulConstants.HEADER_RESP_SERVICE_NAME, SpringUtil.getApplicationName());
        filterChain.doFilter(request, response);
    }

}
