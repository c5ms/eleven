package com.eleven.upms.endpoint.rest;

import com.eleven.core.security.SecurityContext;
import com.eleven.core.rest.annonation.AsRestApi;
import com.eleven.core.security.Subject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "会话")
@RequestMapping("/subject")
@AsRestApi
@RequiredArgsConstructor
public class SubjectResourceV1 {

    @Operation(summary = "当前会话信息")
    @GetMapping
    public Subject createToken() {

        return SecurityContext.getCurrentSubject();
    }

}
