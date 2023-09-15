package com.eleven.upms.endpoint.admin;

import com.eleven.core.rest.annonation.AsAdminApi;
import com.eleven.core.security.SecurityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "会话")
@RequestMapping("/session")
@AsAdminApi
@RequiredArgsConstructor
public class SessionResourceV1 {
    SecurityService securityService;



}
